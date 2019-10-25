package com.weiwei.ww.ui

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.weiwei.ww.bean.Banner
import com.weiwei.ww.utils.Cache

/**
 * @Author weiwei
 * create by 2019-10-25
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
class ProcessTestService :Service(){
//    private var banners:List<Banner> by Cache(Cache.BANNERS, listOf())
    override fun onBind(intent: Intent?): IBinder?  = null

    override fun onCreate() {
        super.onCreate()
//        Log.e("weiwei","service $banners")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("weiwei","onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }
}