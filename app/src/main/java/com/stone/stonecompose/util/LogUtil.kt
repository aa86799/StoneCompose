package com.stone.stonecompose.util

import android.util.Log
import com.stone.stonecompose.BuildConfig

/**
 * desc   :
 * author : stone
 * email  : aa86799@163.com
 * time   : 09/06/2017 23 20
 */
private const val TAG = "stone-log--> "

fun logi(msg: Any) {
    if (BuildConfig.DEBUG)
        Log.i(TAG, msg.toString())
}

fun loge(msg: Any) {
    if (BuildConfig.DEBUG)
        Log.e(TAG, msg.toString())
}

fun loge(msg: Any, throwable: Throwable) {
    if (BuildConfig.DEBUG)
        Log.e(TAG, msg.toString(), throwable)
}