package com.stone.stonecompose.page.foundation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stone.stonecompose.R
import com.stone.stonecompose.util.ToastUtil

// Icon , IconButton , 自定义 IconButton
// Icon 主要用于显示小图标，界面中的指示图标
class TestIconActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            test()
        }
        // 内置图标的矢量图集合
        Icons.Default
        Icons.Filled
        Icons.Outlined
        Icons.Sharp
        Icons.TwoTone
        Icons.AutoMirrored
    }

    @Preview
    @Composable
    fun test() {
        // Icon 组件支持三种不同类型的图片设置
        Column {
            // 矢量xml资源 图
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_android_black_24dp
                ), "", tint = Color.Magenta
            )

            // 内置图标的矢量图
            Icon(imageVector = Icons.Default.Delete, "", tint = Color.Magenta)
            Icon(imageVector = Icons.Outlined.Delete, "", tint = Color.Magenta)
            Icon(imageVector = Icons.Sharp.Delete, "", tint = Color.Magenta)

            Icon(
                bitmap = ImageBitmap.imageResource(
                    id = R.mipmap.capsule_man
                ), contentDescription = "胶囊超人-位图",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp),
                tint = Color.Unspecified
            ) // 不着色，否则就是一整块的填充色

            Icon(
                painter = painterResource(
                    id = R.mipmap.camera
                ), contentDescription = "位图",
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp),
                tint = Color.Unspecified
            )
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_android_black_24dp
                    ), contentDescription = "矢量图",
                    tint = Color.Blue
                )
            }


            MyIconButton(onClick = {
                ToastUtil.showToast("click it")
            }, Modifier.background(Color.Magenta)) {
                // AS 将.svg 导入步骤：
                // 1. 右键 res/drawable 文件夹
                // 2. New -> Vector Asset
                // 3. 选择 "Local file (SVG, PSD)" 导入 SVG 文件
                // 4. Android Studio 会自动转换为 XML 格式
                Icon(
                    painter = painterResource(
                        id = R.drawable.exchange
                    ), contentDescription = "矢量图",
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }

    // 复制源码中的 IconButton 实现，将 indication = 波纹指示效果 置为 null，
    // 即 取消了 波纹点击效果
    @Composable
    fun MyIconButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        interactionSource: MutableInteractionSource? = null,
        content: @Composable () -> Unit
    ) {
        Box(
            modifier = modifier
                .minimumInteractiveComponentSize()
                .clickable(
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.Button,
                    interactionSource = interactionSource,
                    indication = null)
                .then(Modifier.size(120.dp)), // 增加了.then修饰
            contentAlignment = Alignment.Center
        ) {
            val contentAlpha = if (enabled) LocalContentAlpha.current else ContentAlpha.disabled
            CompositionLocalProvider(LocalContentAlpha provides contentAlpha, content = content)
        }
    }
}
