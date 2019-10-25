package com.weiwei.ww.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.SimpleImmersionOwner
import com.weiwei.ww.R
import java.lang.reflect.ParameterizedType

/**
 * @Author weiwei
 * create by 2019-10-24
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
abstract class BaseVMFragment<VM : BaseViewModel> : androidx.fragment.app.Fragment(),
    SimpleImmersionOwner {

    protected lateinit var mViewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVM()
        initView()
        initData()
        startObserve()
        super.onViewCreated(view, savedInstanceState)
    }

    open fun startObserve() {
        mViewModel.mException.observe(this, Observer { it?.let { onError(it) } })
    }

    override fun immersionBarEnabled() = false
    open fun needWhiteStatusBar(): Boolean = true
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
            ImmersionBar.with(this)
                .keyboardEnable(isKeyboardEnable())
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
                .transparentStatusBar()
                .statusBarDarkFont(statusBarDarkFont())
        }.init()
    }

    open fun onError(e: Throwable) {}

    abstract fun getLayoutResId(): Int

    abstract fun initView()

    abstract fun initData()

    private fun initVM() {
        (javaClass.genericSuperclass as ParameterizedType)?.let {
            mViewModel = ViewModelProvider(this).get(it.actualTypeArguments[0] as Class<VM>)
            mViewModel.let(lifecycle::addObserver)
        }
    }


    override fun onDestroy() {
        lifecycle.removeObserver(mViewModel)
        super.onDestroy()
    }
}