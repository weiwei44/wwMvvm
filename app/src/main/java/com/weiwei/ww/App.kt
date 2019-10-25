package com.weiwei.ww

import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV
import kotlin.properties.Delegates


class App : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext

        initMmkv()
    }

    private fun initMmkv() {
        // 设置初始化的根目录
        val dir = filesDir.absolutePath + "/cache"
        val rootDir = MMKV.initialize(dir)
    }
}