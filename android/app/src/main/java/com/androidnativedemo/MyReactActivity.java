package com.androidnativedemo;

import javax.annotation.Nullable;

public class MyReactActivity extends PreLoadReactActivity {

    public MyReactActivity(PreLoadReactDelegate mPreLoadReactDelegate) {
        super(mPreLoadReactDelegate);
    }
    public MyReactActivity() {

    }

    @Nullable
    @Override
    protected String getMainComponentName() {
        return "HotRN";
    }
}
