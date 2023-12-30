package com.stone.stonecompose

import android.app.Activity
import android.app.Application
import android.os.Bundle

class MyApplication: Application() {

    var topActivity: Activity? = null

    companion object {
        var ins: MyApplication? = null
        fun getInstance(): MyApplication {
            return ins!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        ins = this
        lifeCallback()
    }

    private fun lifeCallback() {
        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                topActivity = activity
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {
                topActivity = activity
            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }
}