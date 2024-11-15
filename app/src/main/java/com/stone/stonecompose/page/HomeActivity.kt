package com.stone.stonecompose.page

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stone.stonecompose.page.foundation.TestTextImageButtonActivity
import com.stone.stonecompose.common.openActivity
import com.stone.stonecompose.page.foundation.TestDialogActivity
import com.stone.stonecompose.page.foundation.TestMaterialDesignActivity
import com.stone.stonecompose.page.foundation.TestRememberStateLazyColumnActivity
import com.stone.stonecompose.page.foundation.TestRowColumnBoxActivity
import com.stone.stonecompose.page.foundation.TestScaffoldActivity
import com.stone.stonecompose.ui.theme.C_4F57FF
import com.stone.stonecompose.ui.theme.Purple80

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            showMenu()
        }
    }


    @Preview
    @Composable
    private fun showMenu() {
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
                                when (item) {
                                    "test Text/Image/Button" -> {
                                        openActivity<TestTextImageButtonActivity>()
                                    }
                                    "test Row/Column/Box" -> {
                                        openActivity<TestRowColumnBoxActivity>()
                                    }
                                    "test Material Design" -> {
                                        openActivity<TestMaterialDesignActivity>()
                                    }
                                    "test Scaffold" -> {
                                        openActivity<TestScaffoldActivity>()
                                    }
                                    "test RememberState LazyColumn" -> {
                                        openActivity<TestRememberStateLazyColumnActivity>()
                                    }
                                    "test AlertDialog" -> {
                                        openActivity<TestDialogActivity>()
                                    }
                                }
                            })
                }

            }
        }
    }

    companion object {
        val TITLES = listOf(
            "test Text/Image/Button",
            "test Row/Column/Box",
            "test Material Design",
            "test Scaffold",
            "test RememberState LazyColumn",
            "test AlertDialog",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
            "test ",
        )
    }
}