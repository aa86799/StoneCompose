package com.stone.stonecompose.base.mvi

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.stone.stonecompose.util.MessageLevel
import com.stone.stonecompose.util.ToastUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/12/4 11:24
 */

class BaseMviUi(private val context: Context, private val lifecycleOwner: LifecycleOwner) {


    fun stateFlowHandle(flow: Flow<IUiState>, handleError: Boolean, block: (state: IUiState) -> Unit) {
        lifecycleOwner.lifecycleScope.launchWhenResumed {  // 开启新的协程
            // repeatOnLifecycle 是一个挂起函数；低于目标生命周期状态会取消协程，内部由suspendCancellableCoroutine实现
            // STATE.CREATED 低于 STARTED 状态；若因某种原因，界面重建，重走 Activity#onCreate 生命周期，就会取消该协程，直到 STARTED 状态之后，被调用者重新触发
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect {
                    when (it) {
                        is LoadingState -> {
                            if (it.isShow){
//                                showLoadingDialog(it.msg)
                            } else {
//                                dismissLoadingDialog()
                            }
                        }
                        is LoadErrorState -> {
                            if (handleError) {
//                                dismissLoadingDialog()
                                showToast(it.error, MessageLevel.ERROR, true)
                            } else block(it)
                        }
                        else -> block(it)
                    }
                }
            }
        }
    }

    fun showToast(msg: String, messageLevel: MessageLevel = MessageLevel.NORMAL, isShort: Boolean) {
        // 如果在 调用后，立即调用 activity#finish()
        // 那么 协程启动和 finish() 等于同步在执行，
        // 协程内部检测到 lifecycle 是 destroyed，则如下的 toast 不会再执行
        // 所以，要和 finish() 组合使用时，可直接使用 ToastUtil.showLong("")
        lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                if (isShort) {
                    ToastUtil.showToast(msg)
                } else {
                    ToastUtil.showToast(msg, duration = Toast.LENGTH_LONG)
                }
            }
        }
    }
}