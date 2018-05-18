package com.androidnativedemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.Callback;
import com.facebook.react.devsupport.DoubleTapReloadRecognizer;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionListener;

import javax.annotation.Nullable;

/**
 * Created by wayne on 2018/5/10.
 */

public class PreLoadReactDelegate {

    private final int REQUEST_OVERLAY_PERMISSION_CODE = 1111;

    private final @Nullable
    Activity mActivity;
    private final @Nullable
    FragmentActivity mFragmentActivity;
    private final @Nullable String mMainComponentName;

    private @Nullable
    ReactRootView mReactRootView;
    private @Nullable
    DoubleTapReloadRecognizer mDoubleTapReloadRecognizer;
    private @Nullable
    PermissionListener mPermissionListener;
    private @Nullable
    Callback mPermissionsCallback;

    public PreLoadReactDelegate(Activity activity, @Nullable String mainComponentName) {
        mActivity = activity;
        mMainComponentName = mainComponentName;
        mFragmentActivity = null;
    }

    public PreLoadReactDelegate(
            FragmentActivity fragmentActivity,
            @Nullable String mainComponentName) {
        mFragmentActivity = fragmentActivity;
        mMainComponentName = mainComponentName;
        mActivity = null;
    }

    protected @Nullable
    Bundle getLaunchOptions() {
        return null;
    }

    protected ReactRootView createRootView() {
        return new ReactRootView(getContext());
    }

    /**
     * Get the {@link ReactNativeHost} used by this app. By default, assumes
     * {@link Activity#getApplication()} is an instance of {@link ReactApplication} and calls
     * {@link ReactApplication#getReactNativeHost()}. Override this method if your application class
     * does not implement {@code ReactApplication} or you simply have a different mechanism for
     * storing a {@code ReactNativeHost}, e.g. as a static field somewhere.
     */
    protected ReactNativeHost getReactNativeHost() {
        return ((ReactApplication) getPlainActivity().getApplication()).getReactNativeHost();
    }

    public ReactInstanceManager getReactInstanceManager() {
        return getReactNativeHost().getReactInstanceManager();
    }

    protected void onCreate() {
//        if (mMainComponentName != null) {
//            loadApp(mMainComponentName);
//        }

        boolean needsOverlayPermission = false;
        if (getReactNativeHost().getUseDeveloperSupport() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Get permission to show redbox in dev builds.
            if (!Settings.canDrawOverlays(mActivity)) {
                needsOverlayPermission = true;
                Intent serviceIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + mActivity.getPackageName()));
                mActivity.startActivityForResult(serviceIntent, REQUEST_OVERLAY_PERMISSION_CODE);
            }
        }
        // 1.从缓存中获取RootView
        if (mMainComponentName != null && !needsOverlayPermission) {
            mReactRootView = ReactPreLoader.getReactRootView(mMainComponentName);

            if (mReactRootView == null) {
                // 2.缓存中不存在RootView,直接创建
                mReactRootView = new ReactRootView(mActivity);
                mReactRootView.startReactApplication(getReactInstanceManager(), mMainComponentName, null);
            }
            // 3.将RootView设置到Activity布局
            mActivity.setContentView(mReactRootView);
        }


        mDoubleTapReloadRecognizer = new DoubleTapReloadRecognizer();
    }

    protected void loadApp(String appKey) {
        if (mReactRootView != null) {
            throw new IllegalStateException("Cannot loadApp while app is already running.");
        }
        mReactRootView = createRootView();
        mReactRootView.startReactApplication(
                getReactNativeHost().getReactInstanceManager(),
                appKey,
                getLaunchOptions());
        getPlainActivity().setContentView(mReactRootView);
    }

    protected void onPause() {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onHostPause(getPlainActivity());
        }
    }

    protected void onResume() {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onHostResume(
                    getPlainActivity(),
                    (DefaultHardwareBackBtnHandler) getPlainActivity());
        }

        if (mPermissionsCallback != null) {
            mPermissionsCallback.invoke();
            mPermissionsCallback = null;
        }
    }

    protected void onDestroy() {
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
            mReactRootView = null;
        }
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onHostDestroy(getPlainActivity());
        }

        // 清除View
        ReactPreLoader.deatchView(mMainComponentName);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager()
                    .onActivityResult(getPlainActivity(), requestCode, resultCode, data);
        }
    }
    public boolean onRNKeyUp(int keyCode) {
        if (getReactNativeHost().hasInstance() && getReactNativeHost().getUseDeveloperSupport()) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                getReactInstanceManager().showDevOptionsDialog();
                return true;
            }
            boolean didDoubleTapR = Assertions.assertNotNull(mDoubleTapReloadRecognizer)
                    .didDoubleTapR(keyCode, mActivity.getCurrentFocus());
            if (didDoubleTapR) {
                getReactInstanceManager().getDevSupportManager().handleReloadJS();
                return true;
            }
        }
        return false;
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (getReactNativeHost().hasInstance()
                && getReactNativeHost().getUseDeveloperSupport()
                && keyCode == KeyEvent.KEYCODE_MEDIA_FAST_FORWARD) {
            event.startTracking();
            return true;
        }
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (getReactNativeHost().hasInstance() && getReactNativeHost().getUseDeveloperSupport()) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                getReactNativeHost().getReactInstanceManager().showDevOptionsDialog();
                return true;
            }
            boolean didDoubleTapR = Assertions.assertNotNull(mDoubleTapReloadRecognizer)
                    .didDoubleTapR(keyCode, getPlainActivity().getCurrentFocus());
            if (didDoubleTapR) {
                getReactNativeHost().getReactInstanceManager().getDevSupportManager().handleReloadJS();
                return true;
            }
        }
        return false;
    }

    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (getReactNativeHost().hasInstance()
                && getReactNativeHost().getUseDeveloperSupport()
                && keyCode == KeyEvent.KEYCODE_MEDIA_FAST_FORWARD) {
            getReactNativeHost().getReactInstanceManager().showDevOptionsDialog();
            return true;
        }
        return false;
    }

    public boolean onBackPressed() {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onBackPressed();
            return true;
        }
        return false;
    }

    public boolean onNewIntent(Intent intent) {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onNewIntent(intent);
            return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(
            String[] permissions,
            int requestCode,
            PermissionListener listener) {
        mPermissionListener = listener;
        getPlainActivity().requestPermissions(permissions, requestCode);
    }

    public void onRequestPermissionsResult(
            final int requestCode,
            final String[] permissions,
            final int[] grantResults) {
        mPermissionsCallback = new Callback() {
            @Override
            public void invoke(Object... args) {
                if (mPermissionListener != null && mPermissionListener.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
                    mPermissionListener = null;
                }
            }
        };
    }

    private Context getContext() {
        if (mActivity != null) {
            return mActivity;
        }
        return Assertions.assertNotNull(mFragmentActivity);
    }

    private Activity getPlainActivity() {
        return ((Activity) getContext());
    }
}
