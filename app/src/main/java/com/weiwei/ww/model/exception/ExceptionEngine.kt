package com.weiwei.ww.model.exception

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException

/**
 * @Author weiwei
 * create by 2019/3/4
 * Des: <异常分发引擎>
 * 代码不优雅，写锤子代码
 */
class ExceptionEngine {
    companion object {
        const val UN_KNOWN_ERROR_MSG = "未知错误" //未知错误
        const val HTTP_ERROR_MSG = "网络错误" //网络错误
        const val PARSE_ERROR_MSG = "解析错误" //解析错误
        const val CONN_ERROR_MSG = "连接失败" //连接失败
        const val TIMEOUT_ERROR_MSG = "网络超时" //网络超时
        const val HTTP_PROXY_MSG = "请关闭网络代理" //代理错误

        const val UN_KNOWN_ERROR = 1000   //未知错误码
        const val ANALYTIC_SERVER_DATA_ERROR = 1001  //解析(服务器)数据错误
        const val ANALYTIC_CLIENT_DATA_ERROR = 1002  //解析(客户端)数据错误
        const val CONNECT_ERROR = 1003  //网络连接错误
        const val TIME_OUT_ERROR = 1004 //网络连接超时
        const val SUCCESS = 200  //成功

        const val UNKNOW_HOST_SERVER = -1 //域名失效
        const val UNKNOW_HTTP = 404 //未知域名

        const val TOKEN_ERROR = 9999  //token失效

        fun handleException(e:Throwable) : ApiException =
            when(e){
                is HttpException-> ApiException(e, e.code(), HTTP_ERROR_MSG)
                is ServerException-> ApiException(e, e.code, e.msg)
                is JsonParseException ->ApiException(e, ANALYTIC_CLIENT_DATA_ERROR, PARSE_ERROR_MSG)
                is JSONException ->ApiException(e, ANALYTIC_CLIENT_DATA_ERROR, PARSE_ERROR_MSG)
                is ParseException->ApiException(e, ANALYTIC_CLIENT_DATA_ERROR, PARSE_ERROR_MSG)
                is MalformedJsonException->ApiException(e, ANALYTIC_CLIENT_DATA_ERROR, PARSE_ERROR_MSG)
                is ConnectException->ApiException(e, CONNECT_ERROR, CONN_ERROR_MSG)
                is SocketTimeoutException->ApiException(e, TIME_OUT_ERROR, TIMEOUT_ERROR_MSG)
                is UnknownHostException-> ApiException(e,UNKNOW_HOST_SERVER)
                is UnknownServiceException-> ApiException(e,UNKNOW_HOST_SERVER)
                else->ApiException(e, UN_KNOWN_ERROR, e.message?:UN_KNOWN_ERROR_MSG)
            }
    }



}