package com.stone.stonecompose.base

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable

abstract class BaseCpActivity: AppCompatActivity() {

    @Composable
    abstract fun InitContent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InitContent()
        }
    }
}