package com.stone.stonecompose.page.layout

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material3.FilterChip
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/*
Flow Layout 包括 FlowRow 和 FlowColumn ，
当一行（或一列）放不下里面的内容时，会自动换行。
这些流式布局还允许使用权重进行动态调整大小，以将项目分配到容器中。
 */
class TestFlowLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            test()
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Preview
    @Composable
    fun test() {
        val filters = listOf(
            "Washer/Dryer", "Ramp access", "Garden", "Cats OK", "Dogs OK", "Smoke-free"
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            filters.forEach { title ->
                var selected by remember { mutableStateOf(false) }
                val leadingIcon: @Composable () -> Unit = {
                    Icon(if (selected) Icons.Default.CheckBox else Icons.Default.CheckBoxOutlineBlank, null)
                }
                FilterChip(
                    selected,
                    onClick = { selected = !selected },
                    label = { Text(title) },
                    leadingIcon = leadingIcon,
//                    colors = SelectableChipColors(),
                    border = BorderStroke(0.5.dp, Color.Magenta)
                )
            }
        }

    }

}
