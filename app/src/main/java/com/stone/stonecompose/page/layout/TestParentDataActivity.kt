package com.stone.stonecompose.page.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.stone.stonecompose.base.BaseCpActivity
import com.stone.stonecompose.common.getRandomColor
import com.stone.stonecompose.util.logi
import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * 示例1：子组件向父布局传递自定义数据；
 * 示例2：自定义实现 weight 纵向布局
 */
class TestParentDataActivity : BaseCpActivity() {

    @Composable
    override fun InitContent() {
        Column {
            CountChildrenNumber {
                repeat(5) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(getRandomColor())
                            .count(Random.nextInt(30, 100))
                    )
                }
            }

            WeightedVerticalLayout(Modifier.padding(16.dp).height(200.dp)) {
                // 如果要绘制文字，那 background 就要写在前面，否则会被背景绘制覆盖掉
                Box(modifier = Modifier.width(100.dp).background(getRandomColor()).weight(2f))
                Box(modifier = Modifier.width(100.dp).background(getRandomColor()).weight(3f))
                Box(modifier = Modifier.width(100.dp).weight(5f).background(getRandomColor()))
            }
        }
    }

    /*
     * ParentDataModifier 是一种机制，允许子组件向父布局传递自定义数据，
     * 这些数据可以在父组件中被读取和使用，以影响布局行为
     */
    class CountNumParentData(var countNum: Int) : ParentDataModifier {
        override fun Density.modifyParentData(parentData: Any?) = this@CountNumParentData
    }

    private fun Modifier.count(num: Int) = this.drawWithContent {
        drawIntoCanvas { canvas ->
            val paint = android.graphics
                .Paint()
                .apply {
                    textSize = 40F
                }
//            canvas.drawCircle()
            // nativeCanvas 即 android.graphics.Canvas
            canvas.nativeCanvas.drawText(num.toString(), 0F, 40F, paint)
        }
        // 绘制 Box 自身内容
        drawContent()
    }.then(
        // 这部分是 父级数据修饰符
        CountNumParentData(num)
    )

    @Composable
    fun CountChildrenNumber(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        var num = 0
        Layout(
            modifier = modifier,
            content = content
        ) { measurables: List<Measurable>, constraints: Constraints ->
            val placeables = measurables.map {
                /*
                 * 每个 Measurable 对象（即子组件）在布局过程中，可能携带有 ParentData；
                 * 这些数据是通过子组件上的 ParentDataModifier 即  CountNumParentData 传过来的。
                 */
                if (it.parentData is CountNumParentData) {
                    num += (it.parentData as CountNumParentData).countNum
                }
                it.measure(constraints)
            }
            logi("CountChildrenNumber: 总价格是：$num")
            val width = placeables.maxOf { it.width }
            val height = placeables.sumOf { it.height }
            layout(width, height) {
                var y = 0
                placeables.forEach {
                    it.placeRelative(0, y)
                    y += it.height
                }
            }
        }
    }

/***************************自定义实现 weight 纵向布局 示例 *********************************************/
    interface VerticalScope {
        @Stable
        fun Modifier.weight(weight: Float) : Modifier
    }

    class WeightParentData(val weight: Float = 0f) : ParentDataModifier {
        override fun Density.modifyParentData(parentData: Any?) = this@WeightParentData
    }

    object VerticalScopeInstance : VerticalScope {
        @Stable
        override fun Modifier.weight(weight: Float): Modifier = this.drawWithContent {
            drawIntoCanvas { canvas ->
                val paint = android.graphics
                    .Paint()
                    .apply {
                        textSize = 40f
                    }
                canvas.nativeCanvas.drawText(weight.toString(), 40F, 40F, paint)
            }
            drawContent()
        }.then(
            WeightParentData(weight)
        )
    }

    @Composable
    fun WeightedVerticalLayout(
        modifier: Modifier = Modifier,
        content: @Composable VerticalScope.() -> Unit
    ) {
        val measurePolicy = MeasurePolicy { measurables, constraints ->
            // 获取各weight值
            val weights = measurables.map {
                (it.parentData as WeightParentData).weight
            }
            val totalHeight = constraints.maxHeight
            val totalWeight = weights.sum()

            val placeables = measurables.mapIndexed { i, mesurable ->
                // 根据比例计算高度
                val h = (weights[i] / totalWeight * totalHeight).roundToInt()
                mesurable.measure(constraints.copy(minHeight = h, maxHeight = h, minWidth = 0))
            }
            // 宽度：最宽的一项
            val width = placeables.maxOf { it.width }

            layout(width, totalHeight) {
                var y = 0
                placeables.forEachIndexed { i, placeable ->
                    placeable.placeRelative(0, y)
                    y += placeable.height
                }
            }
        }
        Layout(modifier = modifier, content = { VerticalScopeInstance.content() }, measurePolicy = measurePolicy)
    }
}