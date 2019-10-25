package com.weiwei.ww.init

import android.util.Log
import com.tencent.mmkv.MMKV
import com.weiwei.ww.base.BaseInitializer

/**
 * @Author weiwei
 * create by 2019-10-25
 * Des: <初始化mmkv>
 * 代码不优雅，写锤子代码
 */
class MmkvInit : BaseInitializer(){
    override fun init() {
        val dir = context?.filesDir?.absolutePath + "/cache"
        val rootDir = MMKV.initialize(dir)
    }

}