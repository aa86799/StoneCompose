package com.stone.stonecompose.page.foundation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import com.stone.stonecompose.R
import com.stone.stonecompose.util.ToastUtil

// Image 对图片内容有更多处理
class TestImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            test()
        }
    }

    @Preview
    @Composable
    fun test() {
        // Icon 组件支持三种不同类型的图片设置
        Column {
            // 矢量xml资源 图
            Image(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_android_black_24dp
                ),
                contentDescription = "",
            )

            // 内置图标的矢量图
            Image(imageVector = Icons.Default.Delete, "")
            Image(imageVector = Icons.Outlined.Delete, "")
            Image(imageVector = Icons.Sharp.Delete, "")

            Image(
                bitmap = ImageBitmap.imageResource(
                    id = R.mipmap.capsule_man
                ), contentDescription = "胶囊超人-位图",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )

            Image(
                painter = painterResource(
                    id = R.mipmap.camera
                ), contentDescription = "位图",
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
            )
            Image(
                painter = painterResource(
                    id = R.drawable.ic_android_black_24dp
                ),
                contentDescription = "矢量图",
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp),
                // 矢量图 可以使用  colorFilter 改变颜色
                colorFilter = ColorFilter.tint(Color.Red),
            )

            Surface(
                modifier = Modifier.size(150.dp),
                shape = CircleShape,
                border = BorderStroke(
                    12.dp, // 边框
                    Brush.linearGradient( // 线性渐变
                        0.1f to Color.Red,
                        0.6f to Color.Blue,
                        1.0f to Color.Green
                    )
                ),
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.exchange
                    ), contentDescription = "矢量图",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Yellow)
                        .clickable {
                            ToastUtil.showToast("click exchange")
                        },
                    // 缩放模式默认为 Fit
                    contentScale = ContentScale.Crop,
                    // 矢量图 可以使用  colorFilter 改变颜色
                    colorFilter = ColorFilter.tint(Color.Magenta),
                    alpha = 0.5f
                )
            }

            coilLoadSvgImage()
        }
    }

    @Composable
    private fun coilLoadSvgImage() {
        val context = LocalContext.current
        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()

        Image(
            painter = rememberAsyncImagePainter(
                model = "https://coil-kt.github.io/coil/images/coil_logo_black.svg",
                imageLoader = imageLoader
            ),
            contentDescription = null,
            modifier = Modifier.size(54.dp)
        )

        // todo 一个图片加载库， 可通过 Glide、Coil 和 Fresco 获取并显示网络图片
        // todo https://jetpackcompose.cn/docs/elements/image  有个示例
        // https://github.com/skydoves/landscapist
    }
}
