package com.weiwei.ww.model.repository

import com.weiwei.ww.base.BaseRepository
import com.weiwei.ww.bean.Banner
import com.weiwei.ww.model.api.Result
import com.weiwei.ww.model.api.RetrofitClient
import java.io.IOException

/**
 * @Author weiwei
 * create by 2019-10-24
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
class MainRepository :BaseRepository(){

    suspend fun getBanners() = apiCall{ RetrofitClient.service.getBanner() }

    suspend fun register() = apiCall { RetrofitClient.service.register("13348845054","123456","123456") }
}