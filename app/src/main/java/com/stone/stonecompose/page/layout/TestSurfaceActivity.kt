package com.stone.stonecompose.page.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stone.stonecompose.R

// Surface 从字面上来理解，是一个平面，，
// 可以将很多的组件摆放在这个平面之上，可以设置这个平面的边框，圆角，颜色等等
class TestSurfaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            test()
        }
    }

    @Preview
    @Composable
    fun test() {
        Surface(
            shape = RoundedCornerShape(8.dp),
            elevation = 10.dp,
            modifier = Modifier
                .width(300.dp)
                .height(100.dp)
        ) {
            Row(
                modifier = Modifier
                    .clickable {}
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_android_black_24dp),
                    contentDescription = stringResource(R.string.app_name),
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Crop
                )
                // 留白组件，仅设置间距 padding 是有有效的；若设置了宽高，则 bg 才有效
                Spacer(Modifier.padding(horizontal = 12.dp)
                    .width(30.dp).fillMaxHeight().background(color= Color.Red))
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "AAA",
                        style = MaterialTheme.typography.h6
                    )
                    Spacer(Modifier.padding(vertical = 8.dp))
                    Text(
                        text = "BBB"
                    )
                }
            }
        }

    }


}
