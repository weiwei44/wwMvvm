package com.weiwei.ww.model.api

import com.weiwei.ww.base.BaseRepository
import com.weiwei.ww.base.BaseResponse
import com.weiwei.ww.bean.Banner
import com.weiwei.ww.bean.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @Author weiwei
 * create by 2019-10-24
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
interface HttpService{

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @GET("/banner/json")
    suspend fun getBanner(): BaseResponse<List<Banner>>

    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(@Field("username") userName: String,
                         @Field("password") passWord: String,
                         @Field("repassword") rePassWord: String):BaseResponse<User>

}