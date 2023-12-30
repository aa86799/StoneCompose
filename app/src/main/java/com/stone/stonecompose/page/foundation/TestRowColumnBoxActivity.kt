package com.stone.stonecompose.page.foundation

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.stone.stonecompose.R
import com.stone.stonecompose.view.LongBigImageView

class TestRowColumnBoxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            test()
        }
    }

    @Preview
    @Composable
    fun test() {
        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier
                .fillMaxWidth(1f)
                .background(Color.Gray),
                contentAlignment = Alignment.Center,
                propagateMinConstraints = true) {
                Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "launcher")
                Row {
                    Text("hi, ", fontSize = 20.sp)
                    Text("box", fontSize = 20.sp)
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
                Text("row", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.size(5.dp)) // 间隔
            AndroidView(factory = {
                LongBigImageView(it)
            }, modifier = Modifier
                .fillMaxSize()
//                .background(Color.Yellow)
                .border(2.dp, Color.Red, shape = RoundedCornerShape(6.dp))
            ) { lbiv ->
                assets.open("qinlan.jpg").use {
                    lbiv.setImage(it)
                }
            }
        }
    }
}

