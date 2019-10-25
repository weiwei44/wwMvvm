package com.weiwei.ww.etx

import android.app.Activity
import com.weiwei.ww.R
import com.weiwei.ww.base.BaseResponse
import org.jetbrains.anko.toast
import retrofit2.HttpException

/**
 * @Author weiwei
 * create by 2019-10-24
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
fun BaseResponse<Any>.isSuccess()  = this.errorCode == 0

fun Activity.onNetError(e: Throwable, func: (e: Throwable) -> Unit) {
    if (e is HttpException) {
        toast(R.string.net_error)
        func(e)
    }
}