package com.stone.stonecompose

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stone.stonecompose.ui.theme.StoneComposeTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoneComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
//                    Greeting("Android")

//                    Column() {
//                        (1..9).forEach {
//                            ClickCounter(it) {
//                                Toast.makeText(this@MainActivity, "$it", 0).show()
//                            }
//                        }
//                    }

//                    SharedPreDemo()

                    val header = remember { mutableStateOf("alpha") }
                    NamePicker(header.value, listOf("C", "B", "A")) {
                        Log.i("call NamePicker", it)
                        header.value = "Beta-$it"
                    }
                }
            }

//            GreetingPreview()
        }
    }
}

/**
 * Display a list of names the user can click with a header
 */
@Composable
fun NamePicker(
    header: String,
    names: List<String>,
    onNameClicked: (String) -> Unit
) {
    Column {
        // this will recompose when [header] changes, but not when [names] changes
        Text(header, style = androidx.compose.material.MaterialTheme.typography.h5)
        Divider()

        Log.i("NamePicker", "")

        // LazyColumn is the Compose version of a RecyclerView.
        // The lambda passed to items() is similar to a RecyclerView.ViewHolder.
        LazyColumn {
            items(names) { name ->
                // When an item's [name] updates, the adapter for that item
                // will recompose. This will not recompose when [header] changes
                NamePickerItem(name, onNameClicked)
                Log.i("NamePicker", "call NamePickerItem $name")
                // header 变化后， LazyColumn 跟随变化
            }
        }
    }
}

/**
 * Display a single name the user can click.
 */
@Composable
private fun NamePickerItem(name: String, onClicked: (String) -> Unit) {
    Text(name, Modifier.clickable(onClick = { onClicked(name) }))
}

//实例化SharePreference对象
val mainSharePref: SharedPreferences = MyApplication.getInstance().getSharedPreferences("main_share", android.content.Context.MODE_PRIVATE)
var value: Boolean = mainSharePref.getBoolean("isSelected", false)
val editor: SharedPreferences.Editor = mainSharePref.edit()
@Composable
fun SharedPreDemo() {
    Column() {
        val value1 = remember { mutableStateOf(value) }
        // 怎么点击checkbox 都没效果
        SharedPrefsToggle("是否选中", value) {
            Log.i("SharedPrefsToggle", "isSelected $it")
//            editor.putBoolean("isSelected", it)
//            editor.commit()
            /*GlobalScope.launch {// 这里的 it 值一直不变，加上协程也没用
                editor.putBoolean("isSelected", it)
                editor.commit()
            }*/

            // 还是无效，即使和 SharedPrefsToggle1() 一样
            // 唯一的不同，是 Checkbox 的checked 也需要是基于 MutableState
            // 最终发现，不用协程，没关系。Checkbox 的checked 改为 MutableState 就可以
            value1.value = it
            GlobalScope.launch {
                editor.putBoolean("isSelected", value1.value)
                editor.commit()
            }
        }

//        val value1 = remember { mutableStateOf(value) }
        SharedPrefsToggle1("是否选中", value1) {
            Log.i("SharedPrefsToggle1", "isSelected $it")
            value1.value = it
            GlobalScope.launch {
                editor.putBoolean("isSelected", value1.value)
                editor.commit()
            }
        }
    }
}
@Composable // 怎么点击checkbox 都没效果
fun SharedPrefsToggle(text: String, value: Boolean, onValueChanged: (Boolean) -> Unit
) {
    Row { Text(text)
    Checkbox (checked = value, onCheckedChange = onValueChanged) }
}
@Composable
fun SharedPrefsToggle1(text: String, value: MutableState<Boolean>, onValueChanged: (Boolean) -> Unit
) {
    Row {
        Text(text)
        Checkbox(checked = value.value, onCheckedChange = onValueChanged)
    }
}

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
//    Button(onClick = onClick) {
//        Log.i("TAG", "ClickCounter: ")
//        Text("I've been clicked $clicks times")
//        Image(painter = painterResource(R.drawable.ic_launcher_background), contentDescription = "img")
//    }

    var items = 0
    val myList = listOf("a$clicks", "b", "c")
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            for (item in myList) { // 这里会发生重组；会更新 myList.size 次
                Text("Item: $item")
                items++ // Avoid! Side-effect of the column recomposing.
            }
        }
        Text("Count: $items", modifier = Modifier.padding(start = 10.dp))
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Text(
        text = "Hello $name!",
        modifier = modifier,
        fontSize = 20.sp
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StoneComposeTheme {
        val mContext = LocalContext.current
        Greeting("Android", Modifier.clickable { mContext.startActivity(Intent(mContext, Demo1Activity::class.java)) })
    }
}