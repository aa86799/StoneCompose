package com.stone.stonecompose

import android.app.Application
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance

class MyApplication: Application() {

    companion object {
        var ins: MyApplication? = null
        fun getInstance(): MyApplication {
            return ins!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        ins = this
    }
}