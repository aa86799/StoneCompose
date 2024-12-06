package com.stone.stonecompose.common

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

// 生成随机颜色的函数
fun getRandomColor(): Color {
    val random = Random(System.nanoTime())
    return Color(
        red = random.nextFloat(),
        green = random.nextFloat(),
        blue = random.nextFloat(),
        alpha = 1f // 完全不透明
    )
}