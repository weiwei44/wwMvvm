package com.weiwei.ww.model.exception

/**
 * @Author weiwei
 * create by 2019/3/4
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */

data class ServerException(var code:Int, var msg:String): RuntimeException(msg)