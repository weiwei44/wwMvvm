package com.weiwei.ww.model.api

/**
 * @Author weiwei
 * create by 2019-09-06
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
class HttpCallBaclProxy <T>{
    var success:((data:T?)->Unit)? = null
    var fail:((code:Int,msg:String)->Unit)? = null

    fun onSuccess(action:(data:T?)->Unit){
        success = action
    }

    fun onFail(action:(code:Int,msg:String)->Unit){
        fail = action
    }
}