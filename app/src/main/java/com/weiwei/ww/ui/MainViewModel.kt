package com.weiwei.ww.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.weiwei.ww.base.BaseViewModel
import com.weiwei.ww.bean.Banner
import com.weiwei.ww.bean.User
import com.weiwei.ww.model.api.Result
import com.weiwei.ww.model.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


/**
 * @Author weiwei
 * create by 2019-10-24
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
class MainViewModel : BaseViewModel() {

    private val repository by lazy { MainRepository() }

    val register: MutableLiveData<Result<User>> = MutableLiveData()

    val mBanners: LiveData<Result<List<Banner>>> = liveData {
        kotlin.runCatching {
            val data = withContext(Dispatchers.IO) { repository.getBanners() }
            emit(data)
        }
    }

    fun register(showProgress: Boolean = true) {
        launch {
            if (showProgress) emitState(true)
            val data = withContext(Dispatchers.Default) {
                delay(1000)
                repository.register()
            }
            register.value = data
            if (showProgress) emitState(false)
        }
    }
}