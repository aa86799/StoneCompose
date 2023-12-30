package com.stone.stonecompose.page.foundation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.stone.stonecompose.ui.theme.StoneComposeTheme

class TestMaterialDesignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoneComposeTheme {
                // Surface 类似 FrameLayout或CardView. 带有背景颜色、形状、阴影
                // 通常用于实现Material Design的卡片和其他UI元素
                Surface(modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight(0.2f),
//                    color = MaterialTheme.colorScheme.surface,
                    color = Color.Magenta,
                    shape = RoundedCornerShape(8.dp),
                    shadowElevation = 3.dp) {
                    Text(
                        text = "Hello, Compose!",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }

                Card(modifier = Modifier
                    .background(Color.Yellow)
                    .padding(start=120.dp)
                    .width(100.dp)
                    .height(100.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Green,
                        contentColor = Color.Red),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(1.dp)) {

                    Column(
                        modifier = Modifier.fillMaxSize(), // 使Column填满Card
                        verticalArrangement = Arrangement.Center, // 垂直居中
                        horizontalAlignment = Alignment.CenterHorizontally // 水平居中
                    ) {
                        Text("card",
                            modifier = Modifier.wrapContentSize())

                    }
                }
            }

        }
    }
}