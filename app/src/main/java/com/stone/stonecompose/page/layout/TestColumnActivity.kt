package com.stone.stonecompose.page.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Row 子项按照从上到下的顺序垂直排列
class TestColumnActivity : AppCompatActivity() {

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
给 Column 指定了:
高度，那么就能使用 verticalArrangement 参数来定位子项在 Column 中的垂直位置。
宽度，那么就能使用 horizontalAlignment 参数来定位子项在 Column 中的水平位置。
大小，那么就可以同时使用以上的两个参数来定位子项的水平/垂直位置。
width、height 优先于 size。
当有仅 设置 width/height ，size 可以作用于另一方 height/width。
当 width、height 都没设置，size 可以完全作用于双方  width & width。

当 w/h 都有定义(或定义了 size)，则内部组件，可以使用 Modifier.align来设置水平方向；
设置了 Modifier.align 属性的子项会优先于 Column 的 horizontalAlignment 参数来定位。
         */
        Column {
            Column(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .width(200.dp)
                    .height(100.dp)
//                .size(200.dp)
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Hello, World!",
                    style = MaterialTheme.typography.h6
                )
                Text("Jetpack Compose",
                    modifier = Modifier.align(Alignment.CenterHorizontally))

            }
            testArrangement()

            testAverage()
        }

    }

    @Composable
    fun testArrangement() {
        val verticalFlag = remember { mutableStateOf(Arrangement.Top) }
        verticalFlag.value = Arrangement.SpaceAround
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            elevation = 10.dp
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Column(verticalArrangement = verticalFlag.value,
                    modifier = Modifier.padding(8.dp)
                        .border(1.dp, Color.Blue)
                        .fillMaxWidth()
                        .height(200.dp)) {
                    Text(text = "A", modifier = Modifier.border(1.dp, Color.Red)
                        .width(100.dp).wrapContentHeight(Alignment.CenterVertically), textAlign = TextAlign.Center)
//                    Spacer(Modifier.height(10.dp))
                    Text(text = "B", modifier = Modifier.border(1.dp, Color.Green))
//                    Spacer(Modifier.height(10.dp))
                    Text(text = "C", modifier = Modifier.border(1.dp, Color.Cyan))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button({
                        verticalFlag.value = Arrangement.Top
                    }) {
                        Text("Top")
                    }
                    Button({
                        verticalFlag.value = Arrangement.Center
                    }) {
                        Text("Center")
                    }
                    Button({
                        verticalFlag.value = Arrangement.Bottom
                    }) {
                        Text("Bottom")
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button({
                        verticalFlag.value = Arrangement.SpaceEvenly
                    }) {
                        Text("SpaceEvenly")
                    }
                    Button({
                        verticalFlag.value = Arrangement.SpaceAround
                    }) {
                        Text("SpaceAround")
                    }
                    Button({
                        verticalFlag.value = Arrangement.SpaceBetween
                    }) {
                        Text("SpaceBetween")
                    }
                }
            }
        }
    }

    // 纵向等分，再设置 verticalArrangement 就看不出效果了；因纵向没有剩余空间了
    @Composable
    fun testAverage() {
        val verticalFlag = remember { mutableStateOf(Arrangement.Top) }
        verticalFlag.value = Arrangement.SpaceAround
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            elevation = 10.dp
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Column(verticalArrangement = verticalFlag.value,
                    modifier = Modifier.padding(8.dp)
                        .border(1.dp, Color.Blue)
                        .fillMaxWidth()
                        .height(200.dp)) {
                    Text(text = "E", modifier = Modifier.border(1.dp, Color.Red).weight(1f)
                        .width(100.dp).wrapContentHeight(Alignment.CenterVertically), textAlign = TextAlign.Center)
                    Spacer(Modifier.height(10.dp))
                    Text(text = "D", modifier = Modifier.border(1.dp, Color.Green).weight(1f))
                    Spacer(Modifier.height(10.dp))
                    Text(text = "F", modifier = Modifier.border(1.dp, Color.Cyan).weight(1f))
                }
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    Button({
//                        verticalFlag.value = Arrangement.Top
//                    }) {
//                        Text("Top")
//                    }
//                    Button({
//                        verticalFlag.value = Arrangement.Center
//                    }) {
//                        Text("Center")
//                    }
//                    Button({
//                        verticalFlag.value = Arrangement.Bottom
//                    }) {
//                        Text("Bottom")
//                    }
//                }
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceAround
//                ) {
//                    Button({
//                        verticalFlag.value = Arrangement.SpaceEvenly
//                    }) {
//                        Text("SpaceEvenly")
//                    }
//                    Button({
//                        verticalFlag.value = Arrangement.SpaceAround
//                    }) {
//                        Text("SpaceAround")
//                    }
//                    Button({
//                        verticalFlag.value = Arrangement.SpaceBetween
//                    }) {
//                        Text("SpaceBetween")
//                    }
//                }
            }
        }
    }
}
