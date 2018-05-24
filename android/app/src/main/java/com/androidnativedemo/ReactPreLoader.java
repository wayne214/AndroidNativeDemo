package com.androidnativedemo;

import android.app.Activity;
import android.content.IntentFilter;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactRootView;
import com.facebook.react.module.model.ReactModuleInfo;

import java.util.Map;

/**
 * Created by wayne on 2018/5/10.
 */

public class ReactPreLoader {
    private static final String TAG = "ReactPreLoader";
    private static final Map<String, ReactRootView> CACHE_VIEW_MAP = new ArrayMap<>();


    /**
     * 初始化ReactRootView,并添加到缓存
     * @param activity
     * @param componentName
     * */
    public static void preLoad(Activity activity, String componentName) {
        if (CACHE_VIEW_MAP.get(componentName) != null) {
            return;
        }
        // 1.创建ReactRootView
        ReactRootView rootView = new ReactRootView(activity);
        rootView.startReactApplication( ((ReactApplication)activity.getApplication()).getReactNativeHost().getReactInstanceManager(),
                componentName,
                null);
        // 2.添加到缓存
        CACHE_VIEW_MAP.put(componentName, rootView);
        Log.i(TAG, "preLoad: "+ componentName);
    }
    /**
     * 获取ReactRootView
     * @param componentName
     * @return
     * */
    public static ReactRootView getReactRootView(String componentName) {
        return CACHE_VIEW_MAP.get(componentName);
    }

    /**
     * 从当前页面移除ReactRootView
     *
     * */
    public static void deatchView(String component) {
        try {
            ReactRootView rootView = getReactRootView(component);
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }catch (Throwable e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
