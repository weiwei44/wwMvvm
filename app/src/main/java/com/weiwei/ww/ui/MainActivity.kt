package com.weiwei.ww.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.gyf.immersionbar.BarProperties
import com.weiwei.ww.R
import com.weiwei.ww.base.BaseObserver
import com.weiwei.ww.base.BaseVMActivity
import com.weiwei.ww.bean.Banner
import com.weiwei.ww.utils.Cache
import com.weiwei.ww.utils.ViewUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast

class MainActivity : BaseVMActivity<MainViewModel>() {

    private var isLogin by Cache(Cache.IS_LOGIN,false)
    private var banners:List<Banner> by Cache(Cache.BANNERS, listOf())

    var dialog:ProgressDialog? = null

    override fun getLayoutResId() = R.layout.activity_main

    override fun initView() {
        register.setOnClickListener {
            mViewModel.register()
            isLogin = true
        }

    }

    override fun adjustView(barProperties: BarProperties) {
        super.adjustView(barProperties)

        //适配刘海屏
        ViewUtils.increaseViewHeightByStatusBarHeight(this,top)
    }

    override fun initData() {
        Log.e("weiwei","cache $banners")
        this.startService(Intent(this,ProcessTestService::class.java))
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            mBanners.observe(this@MainActivity, BaseObserver {
                onSuccess {
                    Log.e("weiwei","结果 = $it")
                    banners = it!!
                }
            })

            register.observe(this@MainActivity,BaseObserver{
                onSuccess {
                    Log.e("weiwei","register 结果 = $it")
                }
                onFail { code, msg ->
                    toast(msg)
                    Log.e("weiwei","code = $code,msg= $msg")
                }
            })

            state.observe(this@MainActivity, Observer {

                if(it.showLoading){
                    dialog = indeterminateProgressDialog(it.showError?:"加载中")
                    dialog?.show()
                }else{
                    dialog?.cancel()
                }
            })
        }
    }
}
