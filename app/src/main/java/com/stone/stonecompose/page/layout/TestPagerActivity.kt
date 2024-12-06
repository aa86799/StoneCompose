package com.stone.stonecompose.page.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

// 传统 View 体系中 ViewPager 的替代。包括 HorizontalPager、VerticalPager 。
class TestPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            test()
        }
    }

    @Preview
    @Composable
    fun test() {
        val titles = listOf("AAA", "BBB", "CCC")
        // 创建 PagerState，用于控制和观察分页状态
        val pagerState = rememberPagerState(
            initialPage = 0, // 初始页面索引
            pageCount = {
                titles.size
            }
        )
        val scope = rememberCoroutineScope()

        Column {
            TabRow(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White,
                contentColor = Color.Black,
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        height = 8.dp,
                        color = Color.Yellow
                    )
                }
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title, fontSize = 20.sp, color = Color.Magenta) },
                        modifier = Modifier.padding(start = 10.dp, end = if (index == titles.lastIndex) 10.dp else 0.dp).background(Color.Blue),
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    )
                }
            }
            // 水平分页组件   显示 10 个项目，横向滚动
            HorizontalPager(
                state = pagerState,
//            beyondViewportPageCount = 1, // 在可见之前或之后，预加载几个页面
                modifier = Modifier.fillMaxSize(), // 填充整个屏幕
                pageSpacing = 16.dp
            ) { page ->
                // 每一页的内容，比如显示个文本
                Text(
                    text = "Page: $page",
                    fontSize = 28.sp,
                    modifier = Modifier.fillMaxWidth().background(color = Color.Magenta)
                )
            }

            // 垂直分组件
//            VerticalPager(
//                state = pagerState
//            ) {
//
//            }
        }
    }


}
