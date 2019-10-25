package com.weiwei.ww.model.api

import java.lang.RuntimeException
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * @Author weiwei
 * create by 2019/3/4
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
class SSLSocketClient{
    companion object {

        val  hostnameVerifier : HostnameVerifier
            get() = HostnameVerifier { _, _ -> true }

        private val trustManager : Array<TrustManager>
            get() = arrayOf(object :  X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })


        fun getSSLSocketFactory() : SSLSocketFactory{
            try {
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null,
                    trustManager, SecureRandom())
                return sslContext.socketFactory
            }catch (e:Exception){
                throw RuntimeException()
            }
        }

    }
}