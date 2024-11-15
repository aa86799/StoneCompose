package com.stone.stonecompose.page.foundation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.stone.stonecompose.base.alertDialog1
import com.stone.stonecompose.base.alertDialog2
import com.stone.stonecompose.base.dialog

class TestDialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            test()
        }
    }

    @Preview
    @Composable
    fun test() {
        val openDialog1 = remember { mutableStateOf(true) }
        alertDialog1(openDialog1)
        val openDialog2 = remember { mutableStateOf(true) }
        alertDialog2(openDialog2)

        dialog()
    }
}

