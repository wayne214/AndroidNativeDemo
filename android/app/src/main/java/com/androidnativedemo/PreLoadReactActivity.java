package com.androidnativedemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;


import com.facebook.react.ReactActivity;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;

import javax.annotation.Nullable;

public class PreLoadReactActivity extends ReactActivity implements DefaultHardwareBackBtnHandler, PermissionAwareActivity{
    private PreLoadReactDelegate mPreLoadReactDelegate;

//    public PreLoadReactActivity(PreLoadReactDelegate mPreLoadReactDelegate) {
//        this.mPreLoadReactDelegate = mPreLoadReactDelegate;
//    }
    private PreLoadReactDelegate createPreLoadReactDelegate() {
        return new PreLoadReactDelegate(this, getMainComponentName());
    }
    public PreLoadReactActivity() {
        this.mPreLoadReactDelegate = createPreLoadReactDelegate();
    }
    @Nullable
    @Override
    protected String getMainComponentName() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreLoadReactDelegate.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPreLoadReactDelegate.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPreLoadReactDelegate.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPreLoadReactDelegate.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPreLoadReactDelegate.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mPreLoadReactDelegate.onRNKeyUp(keyCode) || super.onKeyUp(keyCode, event);
    }

    @Override
    public void requestPermissions(String[] permissions, int requestCode, PermissionListener listener) {
        mPreLoadReactDelegate.requestPermissions(permissions, requestCode, listener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mPreLoadReactDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
