package com.weiwei.ww.base

import androidx.lifecycle.Observer
import com.weiwei.ww.model.api.HttpCallBaclProxy
import com.weiwei.ww.model.api.Result

/**
 * @Author weiwei
 * create by 2019-09-06
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
class BaseObserver<T : Any>(mListener: HttpCallBaclProxy<T>.()->Unit) :Observer<Result<T>>{

    val listener:HttpCallBaclProxy<T> = HttpCallBaclProxy<T>().also(mListener)

    override fun onChanged(t: Result<T>) {
        if(t is Result.Success){
            listener.success?.invoke(t.data)
        }else if(t is Result.Error){
            listener.fail?.invoke(t.exception.code,t.exception.msg)
        }
    }
}