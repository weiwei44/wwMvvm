package com.weiwei.ww

import android.app.Application
import android.content.Context
import android.util.Log
import com.tencent.mmkv.MMKV
import kotlin.properties.Delegates

/**
 * @Author weiwei
 * create by 2019-10-24
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
class App : Application() {

    companion object {
        var CONTEXT: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("weiwei","onCreate")
        CONTEXT = applicationContext

        val dir = filesDir?.absolutePath + "/cache"
        val rootDir = MMKV.initialize(dir)
    }


}