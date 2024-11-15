package com.stone.stonecompose.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.stone.stonecompose.ui.theme.Pink80
import com.stone.stonecompose.util.ToastUtil

/*
 * 将根据可用空间来定位其按钮。默认情况下，它将尝试将它们水平地放在彼此的旁边，如果没有足够的空间，则退回到水平放置
 */
@Composable
fun alertDialog1(openDialog: MutableState<Boolean>) {
    if (openDialog.value) {
        AlertDialog(onDismissRequest = { // 当用户点击对话框以外的地方或者按下系统返回键将会执行的代码
            ToastUtil.showToast("dismiss")
            openDialog.value = false
        }, confirmButton = {
            Button(onClick = {
                ToastUtil.showToast("确定")
                openDialog.value = false
            }) {
                Text("confirm")
            }
        }, dismissButton = {
            TextButton({
                ToastUtil.showToast("取消")
                openDialog.value = false
            }) {
                Text("Cancel")
            }
        }, title = {
            Text("标题dialog1")
        }, text = {
            Text("内容内容内容内容内容内容内容内容内容", style = MaterialTheme.typography.button)
        })
    }
}

@Composable
fun alertDialog2(openDialog: MutableState<Boolean>) {
    if (openDialog.value) {
        AlertDialog(onDismissRequest = { // 当用户点击对话框以外的地方或者按下系统返回键将会执行的代码
            ToastUtil.showToast("dismiss")
            openDialog.value = false
        }, buttons = { // 按钮默认纵向排列，可以添加一个 Row {...}；可以只有一个或多个按钮
            Button(onClick = {
                ToastUtil.showToast("确定")
                openDialog.value = false
            }) {
                Text("confirm")
            }
            TextButton({
                ToastUtil.showToast("取消")
                openDialog.value = false
            }) {
                Text("Cancel")
            }
        }, title = {
            Text("标题dialog2")
        }, text = {
            Text("内容内容内容内容内容内容内容内容内容", style = MaterialTheme.typography.button)
        })
    }
}

@Composable
fun dialog() {
    var flag by remember{ mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = { flag = true }
        ) {
            Text("弹窗")
        }
    }
    if(flag) {
        Dialog(
            onDismissRequest = { flag = false }
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    LinearProgressIndicator()
                    Text("加载中 ing...")

                    CircularProgressIndicator(
                        modifier = Modifier.width(100.dp),
                        color = Pink80,
                        strokeWidth = Dp(10f),
                        strokeCap = StrokeCap.Round)
                }
            }
        }
    }
}
