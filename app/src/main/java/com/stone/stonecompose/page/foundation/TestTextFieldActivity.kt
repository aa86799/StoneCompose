package com.stone.stonecompose.page.foundation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stone.stonecompose.R
import com.stone.stonecompose.util.ToastUtil

// 可编输入框
class TestTextFieldActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            test()

        }
    }
/*
@Composable
fun textFieldColors(
    // 输入的文字颜色
    textColor: Color = LocalContentColor.current.copy(LocalContentAlpha.current),

    // 禁用 TextField 时，已有的文字颜色
    disabledTextColor: Color = textColor.copy(ContentAlpha.disabled),

    // 输入框的背景颜色，当设置为 Color.Transparent 时，将透明
    backgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = BackgroundOpacity),

    // 输入框的光标颜色
    cursorColor: Color = MaterialTheme.colors.primary,

    // 当 TextField 的 isError 参数为 true 时，光标的颜色
    errorCursorColor: Color = MaterialTheme.colors.error,

    // 当输入框处于焦点时，底部指示器的颜色
    focusedIndicatorColor: Color = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high),

    // 当输入框不处于焦点时，底部指示器的颜色
    unfocusedIndicatorColor: Color = MaterialTheme.colors.onSurface.copy(alpha = UnfocusedIndicatorLineOpacity),

    // 禁用 TextField 时，底部指示器的颜色
    disabledIndicatorColor: Color = unfocusedIndicatorColor.copy(alpha = ContentAlpha.disabled),

    // 当 TextField 的 isError 参数为 true 时，底部指示器的颜色
    errorIndicatorColor: Color = MaterialTheme.colors.error,

    // TextField 输入框前头的颜色
    leadingIconColor: Color = MaterialTheme.colors.onSurface.copy(alpha = IconOpacity),

    // 禁用 TextField 时 TextField 输入框前头的颜色
    disabledLeadingIconColor: Color = leadingIconColor.copy(alpha = ContentAlpha.disabled),

    // 当 TextField 的 isError 参数为 true 时 TextField 输入框前头的颜色
    errorLeadingIconColor: Color = leadingIconColor,

    // TextField 输入框尾部的颜色
    trailingIconColor: Color = MaterialTheme.colors.onSurface.copy(alpha = IconOpacity),

    // 禁用 TextField 时 TextField 输入框尾部的颜色
    disabledTrailingIconColor: Color = trailingIconColor.copy(alpha = ContentAlpha.disabled),

    // 当 TextField 的 isError 参数为 true 时 TextField 输入框尾部的颜色
    errorTrailingIconColor: Color = MaterialTheme.colors.error,

    // 当输入框处于焦点时，Label 的颜色
    focusedLabelColor: Color = MaterialTheme.colors.primary.copy(alpha = ContentAlpha.high),

    // 当输入框不处于焦点时，Label 的颜色
    unfocusedLabelColor: Color = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),

    // 禁用 TextField 时，Label 的颜色
    disabledLabelColor: Color = unfocusedLabelColor.copy(ContentAlpha.disabled),

    // 当 TextField 的 isError 参数为 true 时，Label 的颜色
    errorLabelColor: Color = MaterialTheme.colors.error,

    // Placeholder 的颜色
    placeholderColor: Color = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),

    // 禁用 TextField 时，placeholder 的颜色
    disabledPlaceholderColor: Color = placeholderColor.copy(ContentAlpha.disabled)
)
 */

    @Preview
    @Composable
    fun test() {
        var text by remember { mutableStateOf("") }
        Column {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xa0a0eded)),
                label = { // label 在textField 之上
                    Text("email")
                },
                singleLine = true
            )

            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                leadingIcon = {
                    Icon(Icons.Filled.Search, null)
                },
                modifier = Modifier.padding(top = 20.dp)
            )
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                leadingIcon = { // 前面的
                    Text("联系人")
                },
                trailingIcon = { // 尾部的
                    Button({
                        ToastUtil.showToast("click it")
                    }) {
                        Text("@163.com")
                    }
                },
                singleLine = true,
                modifier = Modifier.padding(top = 20.dp),
                textStyle = TextStyle(
                    textAlign = TextAlign.Center
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color(0xFF0079D3),
                    backgroundColor = Color.Cyan
                )
            )

            // pwd
            var pwdText by remember{mutableStateOf("")}
            var passwordHidden by remember{ mutableStateOf(true)}
            TextField(
                value = pwdText,
                onValueChange = {
                    pwdText = it
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordHidden = !passwordHidden
                        }
                    ){
                        Icon(imageVector = if (passwordHidden) Icons.Filled.RemoveRedEye else Icons.Outlined.RemoveRedEye, null)
                    }
                },
                label = {
                    Text("密码")
                },
                visualTransformation = if(passwordHidden) PasswordVisualTransformation() else VisualTransformation.None
            )

            testBasicTextField()
        }
    }

    @Composable
    private fun testBasicTextField() {
        // BasicTextField 有更高的自定义效果
        // decorationBox 中 调用 innerTextField() 完成输入框的构建
        var text by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD3D3D3)),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .background(Color.White, CircleShape)
                    .height(35.dp)
                    .fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        IconButton(
                            onClick = { }
                        ) {
                            Icon(painterResource(id = R.drawable.ic_android_black_24dp), null)
                        }
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            innerTextField()
                        }
                        IconButton(
                            onClick = { },
                        ) {
                            Icon(Icons.Filled.Send, null)
                        }
                    }
                }
            )
        }
    }
}
