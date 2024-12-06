package com.stone.stonecompose.page.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stone.stonecompose.R
import kotlinx.coroutines.launch

// 在 App 的底部弹出，并且能够将背景暗化 布局组件
class TestBottomSheetActivity : AppCompatActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            test()
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Preview
    @Composable
    fun test() {
        val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()
        ModalBottomSheetLayout(
            sheetState = state,
            sheetContent = {
                Column{
                    ListItem(
                        text = { Text("选择分享到哪里吧~") }
                    )

                    ListItem(
                        text = { Text("github") },
                        icon = {
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFF181717)
                            ) {
                                Icon(
                                    painterResource(R.mipmap.camera),
                                    null,
                                    tint = Color.White,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        },
                        modifier = Modifier.clickable {  }
                    )

                    ListItem(
                        text = { Text("微信") },
                        icon = {
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFF07C160)
                            ) {
                                Icon(
                                    painterResource(R.mipmap.home
                                    ),
                                    null,
                                    tint = Color.White,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        },
                        modifier = Modifier.clickable {  }
                    )
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { scope.launch { state.show() } }
                ) {
                    Text("点我展开")
                }
            }
        }
    }


}
