package com.stone.stonecompose.page.foundation

import android.os.Bundle
import android.text.style.ClickableSpan
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Indication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.ripple
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stone.stonecompose.R
import com.stone.stonecompose.util.ToastUtil
import com.stone.stonecompose.util.logi
import okhttp3.internal.toHexString


class TestTextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            test()
        }
    }

    @Preview
    @Composable
    fun test() {
        Column {
            Text("Hello World")
            // 从 res 中加载文字
            Text(stringResource(id = R.string.app_name))
            Text(
                "标题格式",
                style = MaterialTheme.typography.h6
            )
            Text(
                "字体大小、粗细、间距",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W900,
                    letterSpacing = 2.sp
                )
            )
            Text(
                "三个参数实现，字体大小、粗细、间距",
                fontSize = 20.sp,
                fontWeight = FontWeight.W900,
                letterSpacing = 2.sp
            )
            Text(
                text = "(测试 maxLines)桃花盛开，粉嫩的花瓣在春风中轻轻摇曳，展现出青春和生命的活力",
                maxLines = 1,
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "(测试 overflow=clip)桃花盛开，粉嫩的花瓣在春风中轻轻摇曳，展现出青春和生命的活力",
                maxLines = 1,
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "(测试 overflow=Ellipsis)桃花盛开，粉嫩的花瓣在春风中轻轻摇曳，展现出青春和生命的活力",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body2
            )
            // 文本行内溢出也展示所有，配合 Row+滚动，可见其效果
            val hScrollState = rememberScrollState()
            Row(modifier = Modifier.horizontalScroll(hScrollState)) {
                Text(
                    text = "(测试 overflow=Visible)桃花盛开，粉嫩的花瓣在春风中轻轻摇曳，展现出青春和生命的活力",
                    maxLines = 1,
                    overflow = TextOverflow.Visible,
                    style = MaterialTheme.typography.body2
                )
            }
            Text(
                "设置了 fillMaxWidth() 之后，可以指定 Text 的对齐方式",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            ) // start/left, end/right
            Text("modifier 设置居中", modifier = Modifier.align(Alignment.CenterHorizontally))
            Text(
                "AA. lineHeight 设置每行的行高间距\nAA.第二行",
                lineHeight = 30.sp
            )
            Text(
                "fontFamily 设置字体，内置字体",
                fontFamily = FontFamily.Monospace
            )
            /*
             * 加载 res/font 下的字体：
             * 右键 res 文件夹，选择 Android Resource Directory -> 选择 font，创建;
             * 将 .ttf 字体 放入 res/font/
             */
            Text(
                "fontFamily 设置字体，外置字体",
                fontFamily = FontFamily(Font(R.font.originality, FontWeight.W400))
            )
            Text("添加点击动作", Modifier.clickable {
                ToastUtil.showToast("click 1")
            })
            Text(
                "添加点击动作，并取消点击的波纹", Modifier.clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = {
                        ToastUtil.showToast("click 2")
                    },
                )
            )
            Text(buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontSize = 20.sp, fontWeight = FontWeight.W900,
                        color = Color.Red, textDecoration = TextDecoration.Underline
                    )
                ) {
                    withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
                        append("AnnotatedString")
                    }
                }
                appendLine("可实现不同的样式，比如粗体提醒，特殊颜色，下划线等")
            }, modifier = Modifier.clickable {
                ToastUtil.showToast("整体点击")
            })

            val clickText = buildAnnotatedString {
                append("点击")
                pushStringAnnotation("url1", annotation = "https://www.baidu.com")
                withStyle(
                    SpanStyle(
                        color = Color.Blue,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("这里")
                }
                pop() // 结束链接注解
                append("访问", "网站资源")
            }
            ClickableText(text = clickText) { offset ->
                clickText.getStringAnnotations(tag = "url1", offset, offset).firstOrNull()?.let {
                    ToastUtil.showToast("点到了${it.item}")
                }
            }
            ClickableText(
                text = buildAnnotatedString {
                    appendLine("某个文字的点击示例：")
                    append("勾选即代表同意")
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF0E9FF2),
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("用户协议")
                    }
                },
                modifier = Modifier.padding(top = 10.dp),
                onClick = { offset ->
                    ToastUtil.showToast("点击了第 $offset 位的文字")
                }
            )

            /*
             * 计算出两个汉字所占的宽高；
             * appendInlineContent 添加文本内容，注意其 id 值；
             * Text#inlineContent 定义的 组件 map，取出 对应 key替换 上一步对应id的位置
             */
            val textMeasurer = rememberTextMeasurer()
            val textSize = textMeasurer.measure(
                text = "谷歌",
                style = TextStyle(fontSize = 14.sp)
            ).size
            val textWh = with(LocalDensity.current) {
                textSize.width.toSp()
            }
            Text(
                text = buildAnnotatedString {
                    append("多个局部点击示例：")
                    append("你可以访问")
                    withStyle(
                        SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        // alternateText 看成是描述？
                        appendInlineContent("link1", "谷歌aaa")
                    }
                    append("或者")
                    withStyle(
                        SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        appendInlineContent("link2", "必应bbb")
                    }
                    append("搜索")
                },
                /*
                 *InlineContent 是 Compose 中一个强大的功能，
                 * 它允许在文本中插入自定义的可组合项（Composable），
                 * 比如图标、按钮或其他自定义视图。这对于创建富文本内容特别有用
                 */
                inlineContent = mapOf(
                    "link1" to InlineTextContent(
                        Placeholder(
                            width = textWh,
                            height = textWh,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.Top
                        )
                    ) {
                        Text(
                            text = "谷歌",
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = ripple()
                            ) {
                                ToastUtil.showToast("点击了 谷歌")
                            }
                        )
                    },
                    "link2" to InlineTextContent(
                        Placeholder(
                            width = textWh,
                            height = textWh,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.Top
                        )
                    ) {
                        Text(
                            text = "必应",
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = ripple()
                            ) {
                                ToastUtil.showToast("点击了 必应")
                            }
                        )
                    }
                ), modifier = Modifier.padding(top = 10.dp))

            SelectionContainer {
                Text("文字复制".repeat(3))
            }

//            CompositionLocalProvider(LocalContentAlpha.provides(ContentAlpha.high)) { }
//             provides 是一个 infix 定义的 中缀函数，so：
//            文字根据不同情况来确定文字的强调程度，以突出重点并体现出视觉上的层次感 (alpha 值的变化)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                Text("这里是high效果")
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text("这里是medium效果")
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
                Text("这里是disable效果")
            }
        }
    }
}
