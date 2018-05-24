package com.androidnativedemo;

import javax.annotation.Nullable;

public class MyReactActivity extends PreLoadReactActivity {

    public MyReactActivity() {

    }

    @Nullable
    @Override
    protected String getMainComponentName() {
        return "AndroidNativeDemo";
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            ReactPreLoader.preLoad(MyReactActivity.this, "AndroidNativeDemo");
        }
    }
}
