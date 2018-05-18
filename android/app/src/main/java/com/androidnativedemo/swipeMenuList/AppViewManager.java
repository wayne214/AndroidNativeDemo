package com.androidnativedemo.swipeMenuList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.androidnativedemo.R;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import java.util.ArrayList;
import java.util.List;

/**
 * 原生视图创建和管理类
 * Created by wayne on 2018/4/25.
 */

public class AppViewManager extends SimpleViewManager<SwipeMenuListView> {

    List<String> dataSource;
    MyAdapter adapter;
    private Context mContext;
    @Override
    public String getName() {
        return "SwipeMenuListView"; // 用于js端引用的view名称
    }

    @Override
    protected SwipeMenuListView createViewInstance(final ThemedReactContext reactContext) {
        mContext = reactContext;
        final SwipeMenuListView swipeMenuListView = new SwipeMenuListView(reactContext);
        swipeMenuListView.setMenuCreator(initMenu(reactContext));
        swipeMenuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // 删除数据
                        String language = adapter.getItem(position);
                        dataSource.remove(position);
                        adapter.notifyDataSetChanged();

                        WritableMap map = Arguments.createMap();
                        map.putString("language", language);
                        // "topChange"事件在JS端映射到"onChange"，参考UIManagerModuleConstants.java
                        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(swipeMenuListView.getId(), "topChange", map);
                        break;
                }
                return false;
            }
        });
        return swipeMenuListView;
    }

    /**
     * 初始化菜单
     * @param context
     * @return
     * */
    private SwipeMenuCreator initMenu(final Context context) {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // 创建"delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(context);
                // 设置item 背景
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                // 设置item 宽
                deleteItem.setWidth(100);
                // 设置item icon
                deleteItem.setTitle("删除");
                deleteItem.setIcon(R.mipmap.ic_launcher);
                // 添加到菜单
                menu.addMenuItem(deleteItem);

            }
        };
        return creator;
    }
    /**
     * 导出属性"array"给JS模块使用
     * @param swipeMenuListView
     * @param array
     *
     * */
    @ReactProp(name = "array")
    public void setDataSource(SwipeMenuListView swipeMenuListView, ReadableArray array) {
        dataSource = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            dataSource.add(array.getString(i));
        }
        adapter = new MyAdapter(mContext, dataSource);
        swipeMenuListView.setAdapter(adapter);
    }
}
