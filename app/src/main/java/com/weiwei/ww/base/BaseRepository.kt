package com.weiwei.ww.base

import android.util.Log
import com.weiwei.ww.etx.isSuccess
import com.weiwei.ww.model.api.Result
import com.weiwei.ww.model.exception.ExceptionEngine
import com.weiwei.ww.model.exception.ServerException

/**
 * @Author weiwei
 * create by 2019-10-24
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
open class BaseRepository {
//    suspend fun <T : Any> apiCall(call: suspend () -> BaseResponse<T>): BaseResponse<T> {
//        return call.invoke()
//    }

    suspend fun <T : Any> apiCall(call: suspend () -> BaseResponse<T>): Result<T> {
        return try {
            val response = call()
            if(response.isSuccess()){
                Result.Success(response.data)
            }else{
                Result.Error(ExceptionEngine.handleException(ServerException(response.errorCode,response.errorMsg)))
            }
        } catch (e: Exception) {
            Result.Error(ExceptionEngine.handleException(e))
        }
    }
}