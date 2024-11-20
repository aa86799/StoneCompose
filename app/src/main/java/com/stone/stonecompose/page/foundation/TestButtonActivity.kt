package com.stone.stonecompose.page.foundation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TestButtonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            test()
        }
    }

    @Preview
    @Composable
    fun test() {
//        test1()
        test2()
    }

    @Composable
    private fun test1() {
        Button(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                Icons.Filled.Favorite, null,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Text("确认")
        }
    }

    @Composable
    private fun test2() {
        // 获取按钮的 交互状态
        val interactionState = remember { MutableInteractionSource() }
        // 使用 Kotlin 的解构方法
//        interactionState.collectIsPressedAsState() // 按压
//        interactionState.collectIsDraggedAsState() // 拖动
//        interactionState.collectIsFocusedAsState() // 焦点
//        interactionState.collectIsHoveredAsState() // 徘徊，悬停
        // 解构 不同交互状态时创建的对象
        val (text, textColor, buttonColor) = when {
            interactionState.collectIsPressedAsState().value -> ButtonState(
                "按下",
                Color.Red,
                Color.Black
            )

            else -> ButtonState("默认", Color.White, Color.Blue)
        }

        Button(
            onClick = { /*TODO*/ },
            interactionSource = interactionState,
            shape = RoundedCornerShape(40.dp),
            colors = androidx.compose.material.ButtonDefaults.buttonColors(
                backgroundColor = buttonColor
            ),
//            modifier = Modifier.width(IntrinsicSize.Min).height(100.dp)
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
        ) {
            Icon(
                Icons.Filled.AcUnit, null,
                modifier = Modifier
                    .size(ButtonDefaults.IconSize)
                    .background(Color.White),
                tint = Color.Magenta
            )
            Text(
                text, color = textColor, fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(1.dp)
            )
        }
    }


}

// 记录不同状态下按钮的样式
private data class ButtonState(var text: String, var textColor: Color, var buttonColor: Color)

