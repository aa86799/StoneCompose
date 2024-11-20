package com.stone.stonecompose.page.foundation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class TestFloatActionButtonActivity : AppCompatActivity() {

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
            FloatingActionButton(onClick = { /*do something*/ }) {
                Icon(Icons.Filled.Favorite, contentDescription = "desc")
            }

            val state = remember { MutableInteractionSource() }
            val expand = state.collectIsPressedAsState().value
            ExtendedFloatingActionButton(
                icon = { Icon(Icons.Filled.AccessAlarm, contentDescription = null) },
                text = { Text("Alarm") },
                onClick = {

                },
                modifier = Modifier.padding(top = 10.dp, start = 20.dp),
                expanded = expand,
                interactionSource = state
            )
        }

    }

}