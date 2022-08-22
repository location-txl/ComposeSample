package com.location.compose.sample

import android.app.Application

/**
 *
 * @author tianxiaolong
 * time：2022/7/28 19:41
 * description：
 */
class App:Application() {
    companion object{
        lateinit var context: App
        private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}