package com.stone.stonecompose.base.mvi

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stone.net.BaseBean
import com.stone.stonecompose.BuildConfig
import com.stone.stonecompose.MyApplication
import com.stone.stonecompose.util.logi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/11/24 13:41
 */
abstract class BaseMviViewModel : ViewModel() {

    val loadingUiState = MutableStateFlow(LoadingUiState())

    data class LoadingUiState(
        val message: String? = null,
        val isLoading: Boolean = false
    )

    fun showLoadingMessage(message: String, isLoading: Boolean) {
        viewModelScope.launch {
            loadingUiState.update { it.copy(message = message, isLoading = isLoading) }
            delay(2000)
            loadingUiState.update { it.copy(message = null, isLoading = isLoading) }
        }
    }

    /**
     * UI 状态
     */
    private val _uiStateFlow by lazy { MutableStateFlow(initUiState()) }
    val uiStateFlow: StateFlow<IUiState> = _uiStateFlow.asStateFlow()

    /**
     * 事件意图, 点击事件、刷新等都是Intent。表示用户的主动操作
     */
    private val _userIntent = MutableSharedFlow<IUiIntent>()
    private val userIntent = _userIntent.asSharedFlow()

    init {
        viewModelScope.launch {
            userIntent.distinctUntilChanged().collect {
                handleUserIntent(it)
            }
        }
    }

    abstract fun handleUserIntent(intent: IUiIntent)

    protected open fun initUiState(): IUiState {
        return LoadingState(false)
    }

    protected fun sendUiState(block: IUiState.() -> IUiState) {
        _uiStateFlow.update { block(it) }
    }

    protected fun sendLoadingState(msg: String = "") {
        sendUiState { LoadingState(true, msg) }
    }

    /**
     * 分发意图
     *
     * 仅此一个 公开函数。供 V 调用
     */
    open fun dispatch(intent: IUiIntent) {
        viewModelScope.launch {
            _userIntent.emit(intent)
        }
    }

    /**
     * @param showLoading 是否展示Loading
     * @param request 请求数据
     * @param successCallback 请求成功
     * @param failCallback 请求失败，处理异常逻辑
     */
    protected fun <T : Any> requestDataWithFlow(
        showLoading: Boolean = true,
        request: suspend () -> Flow<BaseBean<T?>?>,
        successCallback: (BaseBean<T?>?) -> Unit,
        networkErrorCallback: suspend () -> Unit = {},
        failCallback: suspend (String) -> Unit = { errMsg ->  //默认异常处理，子类可以进行覆写
            sendUiState { LoadErrorState(errMsg) }
        },
    ) {
        viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            request()
                .onStart {
                    if (showLoading) {
                        sendUiState { LoadingState(true) }
                    }
                }
                .flowOn(Dispatchers.Default)
                .catch { // 代码运行异常
                    val msg = if (it.message.isNullOrEmpty()) "An unknown error has occurred" else it.message!!
                    failCallback(msg)
                    sendUiState { LoadingState(false) }
                    sendUiState { LoadErrorState(msg) }
                    logi("code run catch. duration=${System.currentTimeMillis() - startTime}, msg=$msg")
                }
                .onCompletion {
                    // 如果发生了 异常，则可能多发送一次 LoadingState(false)
                    sendUiState { LoadingState(false) }
                }
                .flowOn(Dispatchers.Main)
                .collect {
                    if (BuildConfig.DEBUG) {
                        delay(500) // 模拟用的
                    }
                    if (it?.succeeded == true) {
                        successCallback(it)
//                        reauthorize(failCallback) { // 在这里调用，仅用于 模拟验证
//                            println("刷新 user token 成功了")
//                        }
                    } else {
//                        if (it?.code == 401) { // 特定 通用的 code 值 处理
//
//                        }
                        if (it?.code != 1) {
                            logi("code=${it?.code}；url=${it?.requestUrl}；" +
                                    "duration=${System.currentTimeMillis() - startTime}, msg=${it?.msg ?: "unknown error"}")
                        }
                        val msg = if (isNetworkAvailable(MyApplication.getInstance())) {
                            networkErrorCallback()
                            "无网络"
                        } else if (it?.netWorkErr == true) {
                            networkErrorCallback()
                            "网络异常"
                        } else if (it?.msg.isNullOrEmpty())
                            "An unknown error has occurred"
                        else it?.msg!!
                        failCallback(msg)
                    }
                }
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info: NetworkInfo? = connectivity.activeNetworkInfo
        return info != null && info.isConnected
    }

    protected fun getResString(@StringRes res: Int, appendStr: String?): String {
        val str = if (appendStr.isNullOrBlank()) "" else "\n$appendStr"
        return MyApplication.getInstance().topActivity?.getString(res) + str
    }
}