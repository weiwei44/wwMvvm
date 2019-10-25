package com.weiwei.ww.bean

/**
 * @Author weiwei
 * create by 2019-10-24
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
data class User(val collectIds: List<Int>,
                val email: String,
                val icon: String,
                val id: Int,
                val password: String,
                val type: Int,
                val username: String)