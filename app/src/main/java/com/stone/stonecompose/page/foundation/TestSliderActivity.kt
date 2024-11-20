package com.stone.stonecompose.page.foundation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.stone.stonecompose.util.logi
import okhttp3.internal.toHexString

/**
 * Slider 区间滑块
 * 反映了一个沿条的数值范围，用户可以从中选择一个单一的数值。它们是调整音量、亮度或应用图像过滤器等设置的理想选择。
 * https://jetpackcompose.cn/docs/elements/slider/
 */
class TestSliderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            test()
        }
    }

    @Preview
    @Composable
    fun test() {
        /*
// 滑条未经过部分的默认 alpha 值
const val InactiveTrackAlpha = 0.24f

// 当滑条被禁用的状态下已经过部分的默认 alpha 值
const val DisabledInactiveTrackAlpha = 0.12f

// 当滑条被禁用的状态下未经过部分的默认 alpha 值
const val DisabledActiveTrackAlpha = 0.32f

// 在滑条上方显示的刻度的默认的 alpha 值
const val TickAlpha = 0.54f

// 当刻度线被禁用时，默认的 alpha 值
const val DisabledTickAlpha = 0.12f
         */
        var progress by remember{ mutableFloatStateOf(0f) }
        var alpha = 0
        Slider(
            value = progress,
            // 设置 tick 和 track 不同状态的颜色
            colors = SliderDefaults.colors(
//                thumbColor = when { // 圆圈的颜色
//                    progress > .65f -> Color.Green
//                    progress > .25f -> Color.Blue
//                    progress > 0f -> Color.Black
//                    else -> Color.Gray
//                },
                // 滑块圆圈的颜色 根据 alpha 动态渐变
                thumbColor = generateAlphaColor(progress,"000000"),
                activeTrackColor = Color(0xFF0079D3)
            ),
            onValueChange = {
                progress = it
            },
            // 增加边框
            modifier = Modifier.border(width = 10.dp,
                brush = Brush.linearGradient(// 线性渐变
                    0.1f to Color.Red,
                    0.6f to Color.Blue,
                    1.0f to Color.Green),
                shape = RoundedCornerShape(35, 25 ,55, 35)
                )
        )
    }

    private fun generateAlphaColor(alphaValue: Float, colorStr: String): Color {
        var alpha = (alphaValue * 0xff).toInt().toHexString()
        if (alpha.length == 1) {
            alpha = "0$alpha"
        }
        logi("----color value: ${android.graphics.Color.parseColor("#" + alpha + colorStr)}")
        return Color(android.graphics.Color.parseColor("#" + alpha + colorStr))
    }
}
