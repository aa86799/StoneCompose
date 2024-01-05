package com.stone.stonecompose.page.foundation

import android.content.res.Resources
import android.graphics.BitmapShader
import android.graphics.Shader
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stone.stonecompose.R
import com.stone.stonecompose.page.HomeViewModel
import com.stone.stonecompose.ui.theme.C_4F57FF
import com.stone.stonecompose.ui.theme.Purple40
import com.stone.stonecompose.ui.theme.Purple80
import com.stone.stonecompose.util.logi
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

/**
 * desc:    状态
 *      可组合函数可以使用 remember API 将对象存储在内存中。
 *      注意：remember 会将对象存储在组合中，当调用 remember 的可组合项从组合中移除后，它会忘记该对象。
 *
 *      rememberSaveable 是一个用于在配置更改（如屏幕旋转）或进程重启时保存和恢复状态的函数。
 *          rememberSaveable 会自动将状态保存到保存状态的实例中，并在需要时恢复它。
 *          这意味着即使应用程序被系统销毁，用户界面状态也可以在用户返回应用程序时恢复。
 *      rememberSaveable 通常与 mutableStateOf 结合使用，以创建一个可观察的状态，
 *          该状态可以在重建 Composable 时保持不变。
 *
 *  LazyColumn 垂直滚动列表，不可被 另一个垂直滚动组件
 *      "Column(modifier = Modifier.verticalScroll(scrollState))"  或 LazyColumn 嵌套。
 *      同理的 LazyRow。
 *  如果需要在同一个屏幕上同时使用垂直和水平滚动，可以将 LazyRow 作为 LazyColumn 中的一个项，或者反过来。
 * author:  stone
 * email:   aa86799@163.com
 * time:    2023/12/27 11:24
 */
class TestRememberStateLazyColumnActivity : AppCompatActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestRememberState()
//            val scrollState = rememberScrollState()
            LazyColumn {
                // 内容有 LazyColumn，不可嵌套
//                 item { TestRememberState() }
                // 自定义对象 增加 stateSaver
                // 屏幕旋转(配置更改)后， 能保存状态
                item { TestCustomSaver() }
                item { TestMapSaver() }
                item { TestListSaver() }
                item { TestTextField() }

//                // 自定义对象，实现 Parcelable
//                // 屏幕旋转(配置更改)后， 能保存状态
                item { TestCustomParcelableDataState() }

                item { TestFlowState() }
//                // LiveData 观察状态，配置更改后 能保存状态
                item { TestLiveDataState() }
                /*
                 * 类似的
                 * RxJava2: subscribeAsState()
                 *      androidx.compose.runtime:runtime-rxjava2
                 * RxJava3: subscribeAsState()
                 *      androidx.compose.runtime:runtime-rxjava3
                 *
                 * 如果使用自定义可观察类，请使用 produceState API 对其进行转换，以生成 State<T>
                 */


                item {
                    TestRememberSelectImage()
                }
                item {
                    TestWindowSizeChanged(WindowSizeClass.calculateFromSize(DpSize(100.dp, 200.dp)))
                }
                item {
                    TestDerived()
                }
                item { TestStateFlow() }
                item { TestStateFlow2() }

                /*
                  * 垂直滚动 嵌套 水平滚动
                  */
                TestMix(this)

            }
        }
    }

    @Composable
    private fun TestRememberSelectImage() {
        // 假设这是用户可以选择的图片资源列表
        val imageResourceList = listOf(R.mipmap.home, R.mipmap.scompose)

        // 使用 remember 保存当前选择的图片资源 ID
        var selectedImageRes by remember { mutableStateOf(imageResourceList.first()) }

        // 用于用户选择图片的简单界面
        imageResourceList.forEach { imageRes ->
            // 当图片被点击时，更新 selectedImageRes
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clickable { selectedImageRes = imageRes }
            )
        }

        // 使用 BackgroundBanner 显示选定的背景图片
        BackgroundBanner(
            avatarRes = selectedImageRes,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
        )
    }

    @Composable
    fun BackgroundBanner(@DrawableRes avatarRes: Int, modifier: Modifier,
                         res: Resources = LocalContext.current.resources) {
        val brush = remember(key1 = avatarRes) { // key1 变化后，关联的 compose就会变化更新
            ShaderBrush(
                BitmapShader(
                    ImageBitmap.imageResource(res, avatarRes).asAndroidBitmap(),
                    Shader.TileMode.REPEAT,
                    Shader.TileMode.REPEAT
                )
            )
        }
        Box(
            modifier = modifier.background(brush)
        ) {
            // ...
        }
    }

    @Composable
    private fun TestListSaver() {
        data class City(var name: String, var country: String)
        val CitySaver = listSaver<City, Any>(
            save = { listOf(it.name, it.country) },
            restore = { City(it[0] as String, it[1] as String) }
        )
        val selectedCity = rememberSaveable(stateSaver = CitySaver) {
            mutableStateOf(City("Madrid", "Spain"))
        }
        val cityText = rememberSaveable { mutableStateOf(selectedCity.toString()) }
        Button(onClick = {
//            selectedCity.value = City("New York", "US")
            selectedCity.value.name = "New York"
            selectedCity.value.country = "US"
            cityText.value = selectedCity.toString()
        }) {
            Text("change list: selectedCity ${cityText.value}")
        }
    }

    @Composable
    private fun TestMapSaver() {
        data class City(var name: String, var country: String)
        val CitySaver = run {
            val nameKey = "Name"
            val countryKey = "Country"
            mapSaver(
                save = { mapOf(nameKey to it.name, countryKey to it.country) },
                restore = { City(it[nameKey] as String, it[countryKey] as String) }
            )
        }
        val selectedCity = rememberSaveable(stateSaver = CitySaver) {
            mutableStateOf(City("Madrid", "Spain"))
        }
        val cityText = rememberSaveable { mutableStateOf(selectedCity.toString()) }
        Button(onClick = {
            selectedCity.value = City("New York", "US")
//                    selectedCity.value.name = "New York"
//                    selectedCity.value.country = "US"
            cityText.value = selectedCity.toString()
        }) {
            Text("change selectedCity ${cityText.value}")
        }
    }

    @Composable
    private fun TestCustomSaver() {
        val user = rememberSaveable(stateSaver = UserSaver) {
            mutableStateOf(UserData("Stone", 22))
        }
        Button(onClick = {
            logi(user.value.age++.toString())
        }) {
            Text("user click")
        }
    }

    @Composable
    private fun TestTextField() {
        // 使用 rememberSaveable 保存文本字段的状态
        val textState = rememberSaveable { mutableStateOf("init") }
        // 输入框
        TextField(
            value = textState.value,
            onValueChange = { newText ->
                textState.value = newText
            },
            label = {
                Text("Enter text： ${textState.value}")
            }
        )
        // 运行时改变 没有影响
        Text(text = "click clear", modifier = Modifier.clickable {
            logi("click clear")
            textState.value = ""
        })
    }

    @Composable
    private fun TestRememberState() {
        val header = rememberSaveable { mutableStateOf("header") }
//            val header = remembeememr { mutableStateOf("header") } // 配置更改不保存状态
//            val header: String by rber { mutableStateOf("header") }
//            val (value, setValue) = remember { mutableStateOf("header") }
        val list = listOf("C", "B", "A")
        // 状态提升：将 TextPicker 中要使用的状态，提升到外部调用的地方；
        //      TextPicker 就变成了 无状态的，可复用的
        TextPicker(header.value, list) {
            logi("click NamePickerItem $it")
            header.value = "Beta-$it"
        }
    }

    @Composable
    private fun TestCustomParcelableDataState() {
        val user2 = rememberSaveable {
            mutableStateOf(UserData2("Stone", 22))
        }
        Button(onClick = {
            logi(user2.value.age++.toString())
        }) {
            Text("user2 click")
        }
    }

    @Composable
    private fun TestFlowState() {
        // 以生命周期感知方式，从 Flow 收集值； 配置更改后不保存状态
        val flowState by viewModel.myFlow.collectAsStateWithLifecycle(
            -1, minActiveState = Lifecycle.State.RESUMED)
        // 无生命周期感知
//        val flowState by viewModel.myFlow.collectAsState(-2)
        Button(onClick = {
            logi("flowState: $flowState")
        }) {
            Text("change flow state $flowState")
        }
    }

    @Composable
    private fun TestLiveDataState() {
        // val obState by viewModel.sumData.observeAsState()
        val obState by viewModel.sumData.observeAsState(101)
        Button(onClick = {
            viewModel.sumData.value = Random.nextInt()
            logi("obState: $obState")
        }) {
            Text("change obState $obState")
        }
    }

    @Parcelize
    private data class UserData2(
        var name: String,
        var age: Int
    ): Parcelable


    private data class UserData(
        var name: String,
        var age: Int
    )

    private object UserSaver: Saver<UserData, Bundle> {
        override fun restore(value: Bundle): UserData? {
            val name = value.getString("name") ?: return null
            val age = value.getInt("age")
            return UserData(name, age)
        }

        override fun SaverScope.save(value: UserData): Bundle {
            return bundleOf("name" to value.name, "age" to value.age)
        }
    }

    private class NamesPreviewProvider : PreviewParameterProvider<String> {
        override val values: Sequence<String>
            get() = sequenceOf("header-A", "header-B", "header-C")
    }

    // 增加 @PreviewParameter，就不报红了，但还是无法预览
    @Preview(showBackground = true)
    @Composable
    private fun PreviewPicker(@PreviewParameter(NamesPreviewProvider::class) header: String) {
        TextPicker(header, listOf("C", "B", "A")) {

        }
    }

    /**
     * Display a list of names the user can click with a header
     */
//    @Preview // 不支持 有参函数的预览
    @Composable
    private fun TextPicker(header: String, values: List<String>, onItemClicked: (String) -> Unit) {
        Column {
            Text(header, style = androidx.compose.material.MaterialTheme.typography.h5)
            // this will recompose when [header] changes, but not when [names] changes
//            Text(header, style = androidx.compose.material.MaterialTheme.typography.h5)
            Divider()
            // LazyColumn is the Compose version of a RecyclerView.
            // The lambda passed to items() is similar to a RecyclerView.ViewHolder.
            LazyColumn { // 按列的纵向顺序，列中添加行
                items(values, key = { it }) { name ->
                    // When an item's [name] updates, the adapter for that item
                    // will recompose. This will not recompose when [header] changes
                    NamePickerItem(name, onItemClicked)
                    logi( "refresh NamePickerItem $name")
                    // header 变化后， LazyColumn 跟随变化
                }
            }
        }
    }

    /**
     * Display a single name the user can click.
     */
    @Composable
    private fun NamePickerItem(text: String, onClicked: (String) -> Unit) {
        Text(text, Modifier
            .height(50.dp)
            .fillMaxWidth()
            .clickable(onClick = { onClicked(text) }),
            fontSize = 22.sp)
        Divider(color = Color.Red)
    }

    private fun TestMix(scope: LazyListScope) {
        // 垂直滚动的列表项
        scope.items(20) { index ->
            Text("垂直项 #$index")
        }
        // 嵌套的水平滚动行
        scope.item {
            LazyRow {
                items(15) { index ->
                    Text("水平项 #$index")
                }
            }
        }
        // 其他垂直滚动的列表项
        scope.items(10) { index ->
            Text("垂直项 #${index + 20}")
        }
    }

    @Composable
    private fun TestWindowSizeChanged(windowSizeClass: WindowSizeClass) {
        val appState = remember(windowSizeClass) {
            MyAppState(windowSizeClass)
        }
        val colorState = remember { mutableStateOf(Purple80) }
        Box(Modifier
            .width(50.dp)
            .height(100.dp)
            .background(colorState.value)
            .clickable {
                colorState.value = when (appState.windowSizeClass.widthSizeClass) {
                    WindowWidthSizeClass.Compact /*尺寸小*/ -> Color(resources.getColor(R.color.teal_700))
                    WindowWidthSizeClass.Medium /*中*/ -> C_4F57FF
                    else/*WindowWidthSizeClass.Expanded*/ /*大*/ -> Purple40
                }
            })
    }

    @Stable // 当类型的公共属性发生变化时，将通知 compose
    class MyAppState(
        val windowSizeClass: WindowSizeClass
    )

    @Composable
    private fun TestDerived() {
        val count = remember { mutableStateOf(0) }
        // derivedStateOf 派生状态: 依赖另一个状态
        val isEven = remember { derivedStateOf { count.value % 2 == 0 } }
        Button(onClick = {
            count.value += 5
        }) {
            Text("test derived: ${isEven.value}")
        }
    }

    /*
     * 如果你在组合过程中直接调用 launch 来启动一个协程，你可能会遇到
     *  "Calls to launch should happen inside a LaunchedEffect and not composition" 的错误。
     * 这是因为在 Compose 中，组合函数应该是无副作用的，而直接启动协程是一种副作用。
     *
     * 使用 LaunchedEffect 来启动协程。LaunchedEffect 接受一个键（key）作为参数，
     *  当这个键发生变化时，它会取消当前的协程并重新启动一个新的协程。
     * 如果你希望协程在组件的整个生命周期内只启动一次，你可以使用 Unit 或其他不变的值作为键。
     *
     * 滚动出屏幕，再滚动进入屏幕，每次进入，就有如下输出：
     *      testStateFlow collect: -100 , 就表示 重新执行了一次重组
     */
    @Composable
    private fun TestStateFlow() {
        val scope = rememberCoroutineScope()
        LaunchedEffect(key1 = Unit) {
            scope.launch {
                viewModel.testStateFlow.collect {
                    logi("testStateFlow collect: $it")
                }
            }
        }

        Button(onClick = {
            viewModel.updateInput(viewModel.countValue + 50)
        }) {
            Text("testStateFlow: ${viewModel.countValue}")
        }
    }

    /*
     * 将 协程 flow的 处理，移到 viewModel 中，
     * scope 需要传递到 vm，
     */
    @Composable
    private fun TestStateFlow2() {
        val scope = rememberCoroutineScope()
//        LaunchedEffect(key1 = Unit) {
//            scope.launch {
//                viewModel.testStateFlow.collect {
//                    logi("testStateFlow collect: $it")
//                }
//            }
//        }
        viewModel.testStateFlowFunc(scope)

        Button(onClick = {
            viewModel.updateInput(viewModel.countValue + 20)
        }) {
            Text("testStateFlow: ${viewModel.countValue}")
        }
    }
}