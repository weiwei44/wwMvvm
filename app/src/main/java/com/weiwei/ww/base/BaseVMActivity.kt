package com.weiwei.ww.base

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.BarProperties
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.OnBarListener
import com.gyf.immersionbar.components.SimpleImmersionOwner
import com.weiwei.ww.R
import java.lang.reflect.ParameterizedType

/**
 * @Author weiwei
 * create by 2019-10-24
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
abstract class BaseVMActivity<VM : BaseViewModel> : AppCompatActivity(), LifecycleObserver,
    SimpleImmersionOwner {

    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVM()
        startObserve()
        if(immersionBarEnabled()){
            initImmersionBar()
        }
        setContentView(getLayoutResId())
        initView()
        initData()
    }

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()

    override fun immersionBarEnabled() = true
    open fun needWhiteStatusBar(): Boolean = false
    open fun statusBarDarkFont(): Boolean = true
    open fun isKeyboardEnable(): Boolean = true

    override fun initImmersionBar() {
        if (needWhiteStatusBar()) {
            ImmersionBar.with(this)
                .keyboardEnable(isKeyboardEnable())
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
        } else {
            //图片沉浸式状态栏
            ImmersionBar.with(this)
                .keyboardEnable(isKeyboardEnable())
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
                .transparentStatusBar()
                .fitsSystemWindows(false)
                .setOnBarListener { adjustView(it) }
                .statusBarDarkFont(statusBarDarkFont())
        }.init()
    }

    /**
     * 适配刘海屏遮挡数据问题
     * Adjust view.
     *
     * @param barProperties the bar properties,ImmersionBar#setOnBarListener
     */
    open fun adjustView(barProperties: BarProperties) {

    }

    private fun initVM() {
        (javaClass.genericSuperclass as ParameterizedType)?.let {
            mViewModel = ViewModelProvider(this).get(it.actualTypeArguments[0] as Class<VM>)
            //将ViewModel声明周期跟activity绑定
            mViewModel.let(lifecycle::addObserver)
        }

    }


    open fun startObserve() {
        mViewModel.mException.observe(this, Observer { it?.let { onError(it) } })
    }

    /**
     * 协程异常时触发
     */
    open fun onError(e: Throwable) {}

    override fun onDestroy() {
        mViewModel.let {
            //将ViewModel声明周期跟activity解绑
            lifecycle.removeObserver(it)
        }
        super.onDestroy()
    }
}