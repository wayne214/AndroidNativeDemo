package com.androidnativedemo.swipeMenuList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidnativedemo.R;

import java.util.List;

/**
 * Created by wayne on 2018/4/25.
 */

public class MyAdapter extends BaseAdapter {
    Context context;
    List<String> dataSource;

    public MyAdapter(Context context, List<String> dataSource) {
        this.context = context;
        this.dataSource = dataSource;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public String getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item, null);
        }
        TextView tvDesc = (TextView) convertView.findViewById(R.id.tv_desc);
        tvDesc.setText(getItem(position));
        return convertView;
    }
}
