package com.weiwei.ww.model.exception

import java.lang.Exception

/**
 * @Author weiwei
 * create by 2019/3/4
 * Des: <统一异常处理>
 * 代码不优雅，写锤子代码
 */

class ApiException : Exception{

    var code:Int = 0  //错误码
    var msg:String = "" //错误信息

    constructor(throwable:Throwable,code :Int) : super(throwable){
        this.code = code
    }

    constructor(code: Int,msg:String){
        this.code = code
        this.msg = msg
    }

    constructor(throwable:Throwable,code :Int,msg:String) : super(throwable){
        this.code = code
        this.msg = msg
    }

}