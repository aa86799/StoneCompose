package com.stone.stonecompose.page.foundation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stone.stonecompose.R
import com.stone.stonecompose.ui.theme.Pink80
import com.stone.stonecompose.ui.theme.Purple80
import com.stone.stonecompose.util.logi

class TestTextImageButtonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            testText()
        }
    }

    @Preview
    @Composable
    fun testText() {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "this is 文本框 \n admin admin admin admin admin admin admin admin admin admin admin admin admin admin admin",
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .clickable {
                        logi("click text")
                        // todo 点击改变背景色
                    },
                color = Color.Red,
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic, // 斜体
                fontWeight = FontWeight.Medium, // 字的粗细
                fontFamily = FontFamily.SansSerif, // 字体
                letterSpacing = 3.sp, // 字符间距
                textDecoration = TextDecoration.LineThrough, // 装饰  UnderLine
                textAlign = TextAlign.Center,
                lineHeight = 30.sp, // 第二开始所占的行高
                overflow = TextOverflow.Ellipsis, // 溢出时的表现 Ellipsis-省略号
                softWrap = true, // 是否在换行处断行； 若false，则不断行只有一行
                maxLines = 2,
                minLines = 1,
                onTextLayout = {
                    // 对象，包含段落信息，大小, 文本，基线和其他细节
                    logi(it.multiParagraph.maxLines)
                    logi(it.multiParagraph.lineCount)
                    logi(it.multiParagraph.width)
                    logi(it.multiParagraph.height)
                    logi(it.multiParagraph.firstBaseline)
                    logi(it.multiParagraph.lastBaseline)
                    logi(it.multiParagraph.intrinsics)

                    logi(it.size)
                    logi(it.firstBaseline)
                    logi(it.lastBaseline)
                    logi(it.placeholderRects)
                },
                style = LocalTextStyle.current
            )
            Image(painter = painterResource(R.mipmap.home),
                contentDescription = "image A",
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .wrapContentHeight()
                    .background(Color.Black)
                    .clickable {
                        logi("click image A")
                    },
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Inside,
                alpha = 0.8f,
                colorFilter = ColorFilter.tint(Purple80, BlendMode.Hardlight)
            )
            Image(painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "image B",
                modifier = Modifier.clickable {
                    logi("click image B")
                },
                colorFilter = ColorFilter.tint(Pink80, BlendMode.Hardlight)
            )
            Image(imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_background),
                modifier = Modifier.clickable {
                    logi("click image C")
                },
                contentDescription = "image C"
            )
//            val ibHome = BitmapFactory.decodeResource(resources, R.mipmap.home).asImageBitmap()
            Image(bitmap = ImageBitmap.imageResource(R.mipmap.home),
                modifier = Modifier.clickable {
                    logi("click image ibHome")
                },
                contentDescription = "image ibHome"
            )
            val ibCustom1 = ImageBitmap(200, 200, ImageBitmapConfig.Rgb565, true, ColorSpaces.DisplayP3)
            androidx.compose.foundation.Canvas(Modifier.fillMaxWidth(1f)) {
                drawIntoCanvas {
                    val paint = android.graphics.Paint()
                    paint.color = android.graphics.Color.RED
                    it.nativeCanvas.drawCircle(100f, 100f, 100f, paint)

                    val paint2 = Paint()
                    paint2.color = Color.Green
                    it.drawCircle(Offset(100f, 100f), 50f, paint2)
                }
            }
            Image(
                bitmap = ibCustom1,
                modifier = Modifier.clickable {
                    logi("click image ibCustom1")
                },
                alignment = Alignment.Center,
                contentDescription = "image ibCustom1",
            )

            val ibCustom2 = ImageBitmap(200, 200, ImageBitmapConfig.Rgb565, true, ColorSpaces.DisplayP3)
            Canvas(ibCustom2).apply {
                val paint = Paint()
                paint.color = Color(0xcb, 0x0a, 0xf8, 0xa0)
                drawRect(0f, 0f, 200f, 200f, paint)
                paint.color = Color.Red
                drawCircle(Offset(100f, 100f), 100f, paint)
                paint.color = Color.Green
                drawCircle(Offset(100f, 100f), 50f, paint)
            }
            Image(
                bitmap = ibCustom2,
                modifier = Modifier.clickable {
                    logi("click image ibCustom2")
                },
                alignment = Alignment.Center,
                contentDescription = "image ibCustom2",
            )

            Button(
                onClick = {
                    logi("click button")
                },
                modifier = Modifier.background(Color.Cyan),
                enabled = true,
                interactionSource = remember { MutableInteractionSource() },
                elevation = ButtonDefaults.elevation(),
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(3.dp, Color.Magenta),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black, // 受 enabled=true 影响
                    contentColor = Color.Yellow, // 受 enabled=true 影响
                    disabledBackgroundColor = Color.Gray, // 受 enabled=false 影响
                    disabledContentColor = Color.Blue),  // 受 enabled=false 影响
                contentPadding = ButtonDefaults.ContentPadding,
            ) { // 以row方式 加载内容
                Text("stone")
                Image(
                    painter = painterResource(R.mipmap.home),
                    alignment = Alignment.CenterEnd,
                    contentDescription = "image home",
                )
            }

        }
    }
}

