package com.stone.stonecompose.page.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.stone.stonecompose.util.ToastUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// 下拉刷新
class TestPullRefreshActivity : AppCompatActivity() {

    private val dataList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataList.addAll(List(10) { "Item $it" })
        setContent {
            test()
        }
    }

    /*
     * 当文本默认宽度时，这个下拉刷新动作，必须在 “ Page Content: item..” 文本 容区域内 下滑才有效；
     * 所以对 Text 设置 Modifier.fillMaxWidth()，就可以只要横向有内容时就能下拉刷新了；
     * 但横向无内容时，下拉刷新还是不行的。
     */
    @OptIn(ExperimentalMaterialApi::class)
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
                        modifier = Modifier
                            .padding(
                                start = 10.dp,
                                end = if (index == titles.lastIndex) 10.dp else 0.dp
                            )
                            .background(Color.Blue),
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    )
                }
            }

            var refreshing by remember { mutableStateOf(false) }
            val pullRefreshState = rememberPullRefreshState(refreshing, onRefresh = {
                ToastUtil.showToast("下拉刷新")
                scope.launch {
                    refreshing = true
                    refresh()
                    refreshing = false
                }
            })
            // 水平分页组件   显示 10 个项目，横向滚动
            HorizontalPager(
                state = pagerState,
//            beyondViewportPageCount = 1, // 在可见之前或之后，预加载几个页面
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0x99abc777)),
                pageSpacing = 16.dp
            ) { page ->
                Box(modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 14.dp)
                    .fillMaxSize()
                    .background(color = Color(0xa0963fff))
                    .pullRefresh(pullRefreshState)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 16.dp)
                            .fillMaxSize()
                            .background(color = Color(0xa0cf6f63))
//                            .pullRefresh(pullRefreshState)
                    ) {
                        Text(
                            text = "Page: $page",
                            fontSize = 28.sp,
                            modifier = Modifier
                                .background(color = Color.Magenta)
                        )
                        LazyColumn {
                            items(dataList.size) {
                                Text(
                                    text = "Page Content: $it",
                                    fontSize = 20.sp,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                    // 刷新指示器放在Box中，确保它显示在内容上层
                    PullRefreshIndicator(
                        refreshing = refreshing,
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter),
                        contentColor = Color.Red
                    )
                }
            }
        }
    }

    private suspend fun refresh() {
        // 模拟网络请求
        delay(1500)
        // 刷新新数据
//        dataList.clear()
        dataList += "Item ${dataList.size + 1}"
    }
}
