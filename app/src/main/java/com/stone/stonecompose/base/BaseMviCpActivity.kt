package com.stone.stonecompose.base

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.stone.stonecompose.base.mvi.BaseMviUi
import com.stone.stonecompose.base.mvi.BaseMviViewModel
import com.stone.stonecompose.base.mvi.IUiState
import kotlinx.coroutines.flow.Flow
import java.lang.reflect.ParameterizedType

abstract class BaseMviCpActivity<VM : BaseMviViewModel> : AppCompatActivity() {

    private val mBaseMviUi by lazy { BaseMviUi(this, this) }
    protected lateinit var mViewModel: VM

    @Composable
    abstract fun InitContent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val loadingUiState by mViewModel.loadingUiState.collectAsState()
            val snackbarHostState = remember { SnackbarHostState() }

            Scaffold (
                snackbarHost = { SnackbarHost(snackbarHostState) }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // 主要内容
                    InitContent()
                    // 消息提示
                    loadingUiState.let { state ->
                        if (state.isLoading && !state.message.isNullOrBlank()) {
                            LaunchedEffect(state.message) {
                                snackbarHostState.showSnackbar(
                                    message = state.message,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }
                }
            }
        }

        //获得泛型参数的实际类型
        val vmClass = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this)[vmClass]

        initObserver()
        initBiz(savedInstanceState)

    }

    abstract fun initObserver()

    abstract fun initBiz(savedInstanceState: Bundle?)

    /**
     * 显示用户等待框
     * @param msg 提示信息
     */
    protected fun showLoadingDialog(msg: String = "") {
//        mBaseMviUi.showLoadingDialog(msg)
    }

    /**
     * 隐藏等待框
     */
    protected fun dismissLoadingDialog() {
//        mBaseMviUi.dismissLoadingDialog()
    }

    protected fun stateFlowHandle(flow: Flow<IUiState>, handleError: Boolean = true, block: (state: IUiState) -> Unit) {
        mBaseMviUi.stateFlowHandle(flow, handleError, block)
    }

}