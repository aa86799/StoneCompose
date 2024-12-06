package com.stone.net

import android.content.Context

interface NetConfig {

    /**
     * 请求头 Authorization，注入
     */
    fun getAuthorization(): String?

    fun getContext(): Context

    fun aliLog(process: String, log: String)

}