package com.stone.stonecompose.page.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.stone.stonecompose.util.logi

// 包含多个 BottomNavigationItems 项，每个导航项代表一个单一的目的地
class TestBottomNavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            test()
        }
    }

    @Preview
    @Composable
    private fun test() {
        val scaffoldState = rememberScaffoldState()
        var selectedItem by remember { mutableIntStateOf(0) }
        val items = listOf("主页", "我喜欢的", "设置")
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Text("主页")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            finish()
                        }) {
                            Icon(Icons.Filled.ArrowBack, null)
                        }
                    }
                )
            },
            bottomBar = {
                /*BottomNavigation {
                    items.forEachIndexed { index, item ->
                        BottomNavigationItem(
                            icon = {
                                when(index){
                                    0 -> Icon(Icons.Filled.Home, contentDescription = null)
                                    1 -> Icon(Icons.Filled.Favorite, contentDescription = null)
                                    else -> Icon(Icons.Filled.Settings, contentDescription = null)
                                }
                            },
                            label = { Text(item) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }
                        )
                    }
                }*/
                myBottomNavigation(items)
            },
            content = { paddingValues ->
//                when (selectedItem) {
//                    0 -> // 内容 composable
//                    else -> // 内容 composable
//                }
                logi("${paddingValues.calculateStartPadding(LayoutDirection.Ltr)}")
                logi("${paddingValues.calculateStartPadding(LayoutDirection.Rtl)}")
                logi("${paddingValues.calculateEndPadding(LayoutDirection.Rtl)}")
                logi("${paddingValues.calculateTopPadding()}")
                logi("${paddingValues.calculateBottomPadding()}")
                Text("paddingValues=${paddingValues.calculateStartPadding(LayoutDirection.Ltr)}", modifier = Modifier.padding(paddingValues.calculateStartPadding(LayoutDirection.Ltr)))
            }
        )

    }

    // BottomNavigation中，不使用 RowScope.BottomNavigationItem；自定义 view 的组合
    @Composable
    private fun myBottomNavigation(items: List<String>) {
        var selectedItem by remember{ mutableIntStateOf(0) }
        BottomNavigation(
            backgroundColor = Color.Yellow,
            modifier = Modifier.border(width = 0.5.dp, color = Color.Blue)
        ) {
            for(index in 0..2 ) {
                Column(
                    modifier = Modifier
                        .border(width = 0.5.dp, color = Color.Magenta)
                        .padding(start = 10.dp, end = 10.dp)
                        .clickable(
                            onClick = {
                                selectedItem = index
                            },
                            indication = null,
                            interactionSource = null
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val alpha = if (selectedItem != index ) 0.5f else 1f
                    val color = Color.Magenta
                    navigationIcon(index, alpha, color)
                    Text(items[index], modifier = Modifier.alpha(alpha), color = color)
                    Spacer(Modifier.padding(top = 2.dp))
                    // 显示动画  小圆点
                    AnimatedVisibility(visible = index == selectedItem) {
                        Surface(shape = CircleShape, modifier = Modifier.size(5.dp),color = color) { }
                    }
                }
            }
        }
    }

    @Composable
    private fun navigationIcon(index:Int, alpha: Float, color: Color) {
        CompositionLocalProvider(LocalContentAlpha provides alpha) {
            when(index){
                0 -> Icon(Icons.Filled.Home, contentDescription = null, modifier = Modifier.alpha(alpha), tint = color)
                1 -> Icon(Icons.Filled.Favorite, contentDescription = null, modifier = Modifier.alpha(alpha), tint = color)
                else -> Icon(Icons.Filled.Settings, contentDescription = null, modifier = Modifier.alpha(alpha), tint = color)
            }
        }
    }
}
