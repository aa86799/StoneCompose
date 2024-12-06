package com.stone.stonecompose.page

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stone.stonecompose.base.BaseMviCpActivity
import com.stone.stonecompose.page.foundation.TestTextImageButtonActivity
import com.stone.stonecompose.common.openActivity
import com.stone.stonecompose.page.foundation.TestButtonActivity
import com.stone.stonecompose.page.foundation.TestCardActivity
import com.stone.stonecompose.page.foundation.TestDialogActivity
import com.stone.stonecompose.page.foundation.TestFloatActionButtonActivity
import com.stone.stonecompose.page.foundation.TestIconActivity
import com.stone.stonecompose.page.foundation.TestImageActivity
import com.stone.stonecompose.page.foundation.TestMaterialDesignActivity
import com.stone.stonecompose.page.foundation.TestRememberStateLazyColumnActivity
import com.stone.stonecompose.page.foundation.TestRowColumnBoxActivity
import com.stone.stonecompose.page.layout.TestScaffoldActivity
import com.stone.stonecompose.page.foundation.TestSliderActivity
import com.stone.stonecompose.page.foundation.TestTextActivity
import com.stone.stonecompose.page.foundation.TestTextFieldActivity
import com.stone.stonecompose.page.layout.TestBottomNavigationActivity
import com.stone.stonecompose.page.layout.TestBottomSheetActivity
import com.stone.stonecompose.page.layout.TestBoxActivity
import com.stone.stonecompose.page.layout.TestColumnActivity
import com.stone.stonecompose.page.layout.TestCustomLayoutActivity
import com.stone.stonecompose.page.layout.TestFlowLayoutActivity
import com.stone.stonecompose.page.layout.TestPagerActivity
import com.stone.stonecompose.page.layout.TestParentDataActivity
import com.stone.stonecompose.page.layout.TestPullRefreshActivity
import com.stone.stonecompose.page.layout.TestRowActivity
import com.stone.stonecompose.page.layout.TestSurfaceActivity
import com.stone.stonecompose.ui.theme.C_4F57FF
import com.stone.stonecompose.ui.theme.Purple80
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : BaseMviCpActivity<HomeViewModel>() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            showMenu()
//        }
//    }

    @Composable
    override fun InitContent() {
        showMenu()
    }


    override fun initObserver() {
        
    }

    override fun initBiz(savedInstanceState: Bundle?) {
        
    }


    @Preview
    @Composable
    private fun showMenu() {
        val scope = rememberCoroutineScope()
        Column(Modifier.fillMaxWidth(1f)) {
            // 垂直滚动列表
            LazyColumn {
//                items(100) { // 100 个item
//                    Text("test")
//                }
                // 含有索引(从0开始)
                itemsIndexed(TITLES) { index, item ->
                    Text(text = "${index + 1}. $item",
                        color = if (index % 2 == 0) C_4F57FF else Purple80,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(16.dp, Dp(8f))
                            .clickable {
                                scope.launch {
                                    mViewModel.showLoadingMessage("点击了 $item", true)
                                    delay(1500)
                                    clickToShowMenu(item)
                                }
                            })
                }

            }
        }
    }

    private fun clickToShowMenu(item: String) {
        when (item) {
            "test ParentData" -> openActivity<TestParentDataActivity>()
            "test Layout/Measure" -> openActivity<TestCustomLayoutActivity>()
            "test PullRefresh" -> openActivity<TestPullRefreshActivity>()
            "test Pager" -> openActivity<TestPagerActivity>()
            "test BottomSheet" -> openActivity<TestBottomSheetActivity>()
            "test BottomNavigation" -> openActivity<TestBottomNavigationActivity>()
            "test Surface" -> openActivity<TestSurfaceActivity>()
            "test Scaffold" -> openActivity<TestScaffoldActivity>()
            "test FlowLayout" -> openActivity<TestFlowLayoutActivity>()
            "test Column" -> openActivity<TestColumnActivity>()
            "test Row" -> openActivity<TestRowActivity>()
            "test Box" -> openActivity<TestBoxActivity>()
            "test Text" -> openActivity<TestTextActivity>()
            "test TextField" -> openActivity<TestTextFieldActivity>()
            "test Slider" -> openActivity<TestSliderActivity>()
            "test Image" -> openActivity<TestImageActivity>()
            "test Icon" -> openActivity<TestIconActivity>()
            "test FAB" -> openActivity<TestFloatActionButtonActivity>()
            "test Card" -> openActivity<TestCardActivity>()
            "test Button" -> openActivity<TestButtonActivity>()
            "test Text/Image/Button" -> openActivity<TestTextImageButtonActivity>()
            "test Row/Column/Box" -> openActivity<TestRowColumnBoxActivity>()
            "test Material Design" -> openActivity<TestMaterialDesignActivity>()
            "test RememberState LazyColumn" -> openActivity<TestRememberStateLazyColumnActivity>()
            "test AlertDialog" -> openActivity<TestDialogActivity>()
        }
    }

    companion object {
        val TITLES = listOf(
            "test ParentData",
            "test Layout/Measure",
            "test Pager",
            "test BottomSheet",
            "test BottomNavigation",
            "test Surface",
            "test Scaffold",
            "test FlowLayout",
            "test Column",
            "test Row",
            "test Box",
            "test TextField",
            "test Text",
            "test Slider",
            "test Image",
            "test Icon",
            "test FAB",
            "test Card",
            "test Button",
            "test Text/Image/Button",
            "test Row/Column/Box",
            "test Material Design",
            "test RememberState LazyColumn",
            "test AlertDialog",
            "test ",
            "test ",
            "test ",
        )
    }
}