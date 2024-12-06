package com.stone.stonecompose.page.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stone.stonecompose.util.logi
import kotlin.math.cos
import kotlin.math.sin

// 自定义布局(测量)，示例：自定义； 固有特性测量；SubcomposeLayout
class TestCustomLayoutActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                test()

                TextWithPaddingToBaselinePreview()

                MyOwnColumn(padding = PaddingValues(horizontal = 50.dp, vertical = 10.dp)) {
                    Text("AAAA")
                    Text("BBBB", fontSize = 30.sp, modifier = Modifier.padding(top = 20.dp))
                }

                Surface {
                    TwoTexts(text1 = "Hi", text2 = "there")
                }

                SubcomposeRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = {
                        Text(text = "Left", Modifier.wrapContentWidth(Alignment.Start))
                        Text(text = "Right", Modifier.wrapContentWidth(Alignment.End))
                    }
                ) {
                    val heightPx = with(LocalDensity.current) { it.toDp() }
                    Divider(
                        color = Color.Black,
                        modifier = Modifier
                            .width(4.dp)
                            .height(heightPx)
                    )
                }

                CircularLayout(modifier = Modifier.size(200.dp), radius = 100.dp) {
                    IconButton({}) {
                        Row {
                            Icon (imageVector = Icons.Default.AcUnit, "", tint = Color.Blue)
                            Text("111")
                        }
                    }
                    IconButton({}) {
                        Row {
                            Icon(imageVector = Icons.Default.BrokenImage, "", tint = Color.Blue)
                            Text("222")
                        }
                    }
                    IconButton({}) {
                        Row {
                            Icon (imageVector = Icons.Default.AcUnit, "", tint = Color.Blue)
                            Text("3333")
                        }
                    }
                    IconButton({}) {
                        Row {
                            Icon(imageVector = Icons.Default.BrokenImage, "", tint = Color.Blue)
                            Text("4444")
                        }
                    }

                    IconButton({}) {
                        Row {
                            Icon (imageVector = Icons.Default.AcUnit, "", tint = Color.Blue)
                            Text("5555")
                        }
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun test() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            // 基本使用
            BadgeText(
                text = "消息",
                badgeCount = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 自定义样式
            BadgeText(
                text = "通知",
                badgeCount = 99,
                modifier = Modifier.padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 单独使用Badge
            Badge(
                count = 110,
                backgroundColor = MaterialTheme.colors.error,
                size = 20.dp,
                fontSize = 10.sp
            )
        }
    }

    @Composable
    fun BadgeText(
        text: String,
        badgeCount: Int,
        modifier: Modifier = Modifier
    ) {
        Box(modifier = modifier) {
            Row {
                Text(text)
                Badge(
                    count = badgeCount,
                    modifier = Modifier.badgeLayout()
                )
            }
        }
    }

    @Composable
    fun Badge(
        count: Int,
        modifier: Modifier = Modifier,
        backgroundColor: Color = Color.Red,
        contentColor: Color = Color.White,
        size: Dp = 13.dp,
        fontSize: TextUnit = 10.sp
    ) {
        Box(
            modifier = modifier
                .size(size)
                .background(backgroundColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (count > 99) "99+" else count.toString(),
                color = contentColor,
                fontSize = fontSize,
                maxLines = 1
            )
        }
    }

    @Composable
    fun Modifier.badgeLayout() = this.layout { measurable, constraints ->
//        val cs = Constraints(100, 200, 100, 200) // 自定义 约束
//        val cs = Constraints.fixedWidth(100)
//        val cs = Constraints.fixedHeight(100)
//        val cs = Constraints.fixed(100, 100)
        // 测量子项
        val placeable = measurable.measure(constraints)
        FirstBaseline
        // 决定布局大小
        layout(placeable.width, placeable.height) {
            // 放置子项
            placeable.place(-10, -10)
        }
    }

    // 圆形布局示例
    @Composable
    fun CircularLayout(
        modifier: Modifier = Modifier,
        radius: Dp = 100.dp,
        content: @Composable () -> Unit
    ) {
        Layout(
            modifier = modifier,
            content = content
        ) { measurables, constraints ->
            val radiusPx = radius.roundToPx()

            // 测量所有子项
            val placeableList = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            // 计算布局大小
            val diameter = radiusPx * 2

            layout(diameter, diameter) {
                // 计算每个项的角度
                val angleStep = 360f / placeableList.size

                placeableList.forEachIndexed { index, placeable ->
                    val angle = index * angleStep

                    // 计算位置
                    val x = radiusPx + (radiusPx * cos(Math.toRadians(angle.toDouble()))).toInt()
                    val y = radiusPx + (radiusPx * sin(Math.toRadians(angle.toDouble()))).toInt()

                    placeable.place(
                        x = x - placeable.width / 2,
                        y = y - placeable.height / 2
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    fun TextWithPaddingToBaselinePreview() {
        Row {
            Text("Hi xyz!",
                Modifier.firstBaselineToTop(24.dp).border(1.dp, Color.Magenta),
                color = Color.Blue, fontSize = 15.sp)
            Text("Hi jmWoq!",
                Modifier.firstBaselineToTop(24.dp).padding(start = 20.dp).border(1.dp, Color.Magenta),
                color = Color.Blue, fontSize = 18.sp)
            Text("石破天惊",
                Modifier.firstBaselineToTop(34.dp).padding(start = 20.dp).border(1.dp, Color.Magenta),
                color = Color.Blue, fontSize = 22.sp)
        }
    }

    /**
     * 调整文本基线位置
     * @param firstBaselineToTop 表示文本第一基线到顶部的距离
     */
    fun Modifier.firstBaselineToTop(
        firstBaselineToTop: Dp
    ) = this.layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            // placeRelative 会根据当前 layoutDirection (布局方向) 自动调整子元素的(相对)位置。
            logi("placeableY=$placeableY")
            placeable.placeRelative(0, placeableY)
        }
    }

    // 自定义 column
    @Composable
    fun MyOwnColumn(
        modifier: Modifier = Modifier,
        padding: PaddingValues = PaddingValues(),
        content: @Composable () -> Unit
    ) {
        var contentPadding by remember { mutableStateOf(PaddingValues(0.dp)) }
        // 获取实际的内边距
        val density = LocalDensity.current
        Layout(
            // 全局相对于父布局的 坐标， 可计算 上下左右 paddingValues
//            modifier = modifier.onGloballyPositioned { coordinates ->
//                with(density) {
//                    contentPadding = PaddingValues(
//                        top = coordinates.parentCoordinates?.let { parent ->
//                            (coordinates.positionInParent().y).toDp()
//                        } ?: 0.dp,
//                        // 其他方向的 padding 类似计算
//                    )
//                }
//            },
            // contentPadding.calculateTopPadding().roundToPx()
            modifier = modifier,
            content = content
        ) { measurables, constraints ->
            // 将 Padding 转换为像素值
            val startPadding = padding.calculateStartPadding(LayoutDirection.Ltr).roundToPx()
            val topPadding = padding.calculateTopPadding().roundToPx()
            val endPadding = padding.calculateEndPadding(LayoutDirection.Ltr).roundToPx()
            val bottomPadding = padding.calculateBottomPadding().roundToPx()
            // 调整约束条件，考虑 padding
            val contentConstraints = constraints.copy(
                maxWidth = constraints.maxWidth - startPadding - endPadding,
                maxHeight = constraints.maxHeight - topPadding - bottomPadding
            )

            // 每次测量的结果集
            val placeableList = measurables.map { measurable ->
                // Measure each child
                measurable.measure(contentConstraints)
//                measurable.measure(constraints) // 多次测量会抛异常： measure() may not be called multiple times on the same Measurable.
            }

            val totalHeight = placeableList.sumOf { it.height } + topPadding + bottomPadding
            var yPosition = 0
            // 将宽度与高度设置为其父元素所允许的最大高度与宽度
            layout(constraints.maxWidth, totalHeight) {
                // Place children
                placeableList.forEach { placeable ->
                    placeable.placeRelative(x = startPadding, y = yPosition)
                    yPosition += placeable.height
                }
            }
        }
    }

    @Composable
    fun TwoTexts(modifier: Modifier = Modifier, text1: String, text2: String) {
        /*
         * modifier.height(IntrinsicSize.Min)) 固有特性测量 最小高度
         * 对应 MeasurePolicy 中的 minIntrinsicHeight()
         */
        Row(modifier = modifier.height(IntrinsicSize.Min)) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .wrapContentWidth(Alignment.Start)
                    .background(Color.Red),
                text = text1
            )

            Divider(color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
                    .wrapContentWidth(Alignment.End)
                    .background(Color.Cyan),
                text = text2
            )
        }
    }

    // 固有特性测量示例，重写 MeasurePolicy#minIntrinsicHeight()
    @Composable
    fun IntrinsicRow(modifier: Modifier, content: @Composable () -> Unit){
        Layout(
            content = content,
            modifier = modifier,
            measurePolicy = object: MeasurePolicy {
                override fun MeasureScope.measure(
                    measurables: List<Measurable>,
                    constraints: Constraints
                ): MeasureResult {
                    val devideConstraints = constraints.copy(minWidth = 0)
                    val mainPlaceables = measurables.filter {
                        it.layoutId == "main"
                    }.map {
                        it.measure(constraints)
                    }
                    val dividerPlaceable = measurables.first { it.layoutId == "divider"}.measure(devideConstraints)
                    val midPos = constraints.maxWidth / 2
                    return layout(constraints.maxWidth, constraints.maxHeight) {
                        mainPlaceables.forEach {
                            it.placeRelative(0, 0)
                        }
                        dividerPlaceable.placeRelative(midPos, 0)
                    }
                }

                // 重写此函数，
                override fun IntrinsicMeasureScope.minIntrinsicHeight(
                    measurables: List<IntrinsicMeasurable>,
                    width: Int
                ): Int {
                    var maxHeight = 0
                    measurables.forEach {
                        maxHeight = it.maxIntrinsicHeight(width).coerceAtLeast(maxHeight)
                    }
                    return maxHeight
                }
            }
        )
    }

    @Composable
    fun SubcomposeRow(
        modifier: Modifier,
        text: @Composable () -> Unit,
        divider: @Composable (Int) -> Unit // 传入高度
    ) {
        var maxHeight = 0
        SubcomposeLayout(modifier = modifier) { constraints->
            val placeables = subcompose("text", text).map {
                val placeable = it.measure(constraints)
                maxHeight = placeable.height.coerceAtLeast(maxHeight)
                placeable
            }

            val dividerPlaceable = subcompose("divider") {
                divider(maxHeight)
            }.map {
                it.measure(constraints.copy(minWidth = 0))
            }
            assert(dividerPlaceable.size == 1, { "DividerScope Error!" })

            layout(constraints.maxWidth, maxHeight) {
                placeables.forEach {
                    it.placeRelative(0, 0)
                }
                dividerPlaceable.forEach {
                    it.placeRelative(constraints.maxWidth / 2, 0)
                }
            }
        }
    }
}
