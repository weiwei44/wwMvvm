package com.weiwei.ww.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.immersionbar.ImmersionBar;

/**
 * @Author weiwei
 * create by 2019-10-24
 * Des: <功能简述>
 * 代码不优雅，写锤子代码
 */
public class ViewUtils {
    public static void increaseViewHeightByStatusBarHeight(Activity activity, View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) lp;
        layoutParams.height += ImmersionBar.getStatusBarHeight(activity);
        view.setLayoutParams(layoutParams);
    }
}
