package com.stone.stonecompose.page.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.stone.stonecompose.util.logi
import kotlinx.coroutines.launch

/*
 * Scaffold 译：脚手架
 * 侧边应用栏drawerContent、底部导航栏bottomBar、
 * 顶部导航栏topBar、悬浮按钮floatingActionButton、等。
 */
class TestScaffoldActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            showScaffold()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview
    @Composable
    private fun showScaffold() {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()
        Scaffold(modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(title = {
                    Text("Scaffold Example")
                }, actions = {
                    IconButton(onClick = { logi("click favorite") }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "favorite")
                    }
                })
            },
            bottomBar = {
                BottomAppBar(modifier = Modifier.fillMaxWidth()) {
                    IconButton(modifier = Modifier.weight(1f),
                        onClick = { logi("click Home") }) {
                        Icon(Icons.Filled.Home, contentDescription = "Home")
                    }
                    Text(text = "TabB", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                    IconButton(modifier = Modifier.weight(1f),
                        onClick = { logi("click Settings") }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            },
//            /*snackbarHost = { // 在这声明，会固定在界面中
//                Snackbar {
//                    Text(text = "Snackbar")
//                }
//            },*/
            floatingActionButton = {
                FloatingActionButton(onClick = { logi("click FloatingActionButton") }) {
                    Icon(Icons.Filled.Add, contentDescription = "FloatingActionButton")
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            backgroundColor = Color.Green,
            contentColor = Color.Blue,
            content = { innerPadding ->
                Column(modifier = Modifier.border(width = 1.dp, color = Color.Blue)) {
                    Text(text = "click show SnackBar",
                        fontSize = 20.sp,
                        /*color = Color.Red,*/
                        modifier = Modifier
                            .padding(
                                innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 10.dp,
                                innerPadding.calculateTopPadding() + 1.dp
                            )
                            .fillMaxWidth()
                            .clickable {
                                lifecycleScope.launch {
                                    val snackbarResult =
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            message = "This is a Snackbar",
                                            actionLabel = "Retry", // 点击后 立即 关闭组件，状态为 performed
                                            duration = SnackbarDuration.Short
                                        )

                                    when (snackbarResult) {
                                        SnackbarResult.ActionPerformed -> {
                                            // Perform action on button click
//                                            showToast("Action performed!")
                                        }

                                        SnackbarResult.Dismissed -> {
                                            // Snackbar was dismissed
//                                            showToast("Snackbar dismissed")
                                        }
                                    }
                                }

                            }
                    )

                    Button({
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Text("open drawer", color = Color.White)
                    }
                }
            },
            drawerContent = {
                Column {
                    Text("Item A")
                    Text("Item B")
                    Text("Item C")
                    Button({
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }, modifier = Modifier.align(Alignment.End)) {
                        Text("close drawer", color = Color.White)
                    }
                }
            },
            drawerGesturesEnabled = true,
            drawerBackgroundColor = Color.White,
        )
    }
}