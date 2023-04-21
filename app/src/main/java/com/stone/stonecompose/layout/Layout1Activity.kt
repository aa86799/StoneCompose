package com.stone.stonecompose.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stone.stonecompose.R

class Layout1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PreviewLayout()
        }
    }

    @Preview
    @Composable
    fun PreviewLayout() {
        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Box {
                Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "launcher")
                Row {
                    Text("hi, ", fontSize = 20.sp)
                    Text("stone", fontSize = 20.sp)
                }
            }
            Row(modifier = Modifier
                .width(150.dp)
                .height(100.dp)
                .padding(top = 10.dp)
                .background(Color.Cyan, RoundedCornerShape(20.dp)),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically) {
                Text("hi, ", fontSize = 20.sp)
                Text("stone", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.size(5.dp)) // 间隔
            Card(elevation = CardDefaults.cardElevation(1.dp)) {
                Button(modifier = Modifier
                    .background(Color.Red) // 除边框以外
                    .border(2.dp, color = Color.Green), // 边框
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Yellow, // 在 Color.Red 以内
                        contentColor = Color.DarkGray), // 内容
                    onClick = { /*TODO*/ }) { // 接受一个 composable 函数
                    Text(text = "忽啦啦")
                }
            }
        }
    }
}

