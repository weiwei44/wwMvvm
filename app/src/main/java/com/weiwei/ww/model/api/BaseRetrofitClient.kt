package com.weiwei.ww.model.api

import com.weiwei.ww.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @Author weiwei
 * create by 2019-10-24
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
abstract class BaseRetrofitClient {
    companion object{
        val TIME_OUT = 5L
    }

    private val client: OkHttpClient
            get(){
                val builder = OkHttpClient.Builder()

                val logging = HttpLoggingInterceptor()
                if (BuildConfig.DEBUG) {
                    logging.level = HttpLoggingInterceptor.Level.BODY
                } else {
                    logging.level = HttpLoggingInterceptor.Level.BASIC
                }

                builder
                    .addInterceptor(logging)
                    .addInterceptor {
                        val mRequest = it.request()
                            .newBuilder()
                            .addHeader("os", "android")
                            .build()
                        return@addInterceptor it.proceed(mRequest)
                    }
                    .connectTimeout(TIME_OUT,TimeUnit.SECONDS)
                    .readTimeout(TIME_OUT,TimeUnit.SECONDS)
                    .writeTimeout(TIME_OUT,TimeUnit.SECONDS)

                handleBuilder(builder)

                return builder.build()
            }

    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build().create(serviceClass)
    }

    protected abstract fun handleBuilder(builder: OkHttpClient.Builder)
}