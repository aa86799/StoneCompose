package com.stone.stonecompose.page.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Row 子项按照从左到右的方向水平排列
class TestRowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            test()
        }
    }

    @Preview
    @Composable
    fun test() {
        val flag = remember { mutableStateOf(Arrangement.Start) }
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            elevation = 10.dp
        ) {
            Column(
                modifier = Modifier.padding(12.dp) // 里面内容的外边距
            ) {
                Text(
                    text = "Jetpack Compose 是什么？",
                    style = MaterialTheme.typography.h6
                )
                Spacer(Modifier.padding(vertical = 5.dp))
                Text(
                    text = "Jetpack Compose 是用于构建原生 Android 界面的新工具包。" +
                            "它可简化并加快 Android 上的界面开发，使用更少的代码、强大的工具和直观的 Kotlin API，快速让应用生动而精彩。"
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = flag.value
                ) {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(Icons.Filled.Favorite, null)
                    }
                    IconButton(
                        onClick = { /*TODO*/ },
                    ) {
                        Icon(imageVector = Icons.Default.ChatBubble, null)
                    }
                    IconButton(
                        onClick = { /*TODO*/ },
                    ) {
                        Icon(Icons.Filled.Share, null)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button({
                        flag.value = Arrangement.Start
                    }) {
                        Text("Start")
                    }
                    Button({
                        flag.value = Arrangement.End
                    }) {
                        Text("End")
                    }
                    Button({
                        flag.value = Arrangement.Center
                    }) {
                        Text("Center")
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button({
                        flag.value = Arrangement.SpaceEvenly
                    }) {
                        Text("SpaceEvenly")
                    }
                    Button({
                        flag.value = Arrangement.SpaceAround
                    }) {
                        Text("SpaceAround")
                    }
                    Button({
                        flag.value = Arrangement.SpaceBetween
                    }) {
                        Text("SpaceBetween")
                    }
                }
            }
        }
    }


}
