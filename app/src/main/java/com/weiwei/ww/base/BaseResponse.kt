package com.weiwei.ww.base

/**
 * @Author weiwei
 * create by 2019-10-24
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
data class BaseResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)