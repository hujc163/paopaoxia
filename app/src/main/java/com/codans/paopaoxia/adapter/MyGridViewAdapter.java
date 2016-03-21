package com.codans.paopaoxia.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.codans.paopaoxia.base.BaseActivity;

import java.util.List;

/**
 * 项目名称：paopaoxia
 * 类描述：
 * 创建人：胡继春
 * 创建时间：2016/3/18 15:13
 * 修改人：胡继春
 * 修改时间：2016/3/18 15:13
 * 修改备注；
 */
public class MyGridViewAdapter extends BaseAdapter{
    private List<Bitmap> list;
    private Context mContext;

    public MyGridViewAdapter(List<Bitmap> list,Context context){
        this.list = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView mimageView;
        if (convertView == null) {
            mimageView = new ImageView(mContext);
            mimageView.setLayoutParams(new GridView.LayoutParams(150, 150));//设置ImageView对象布局
            mimageView.setPadding(8, 8, 8, 8);//设置间距
        } else {
            mimageView = (ImageView) convertView;
        }
        mimageView.setImageBitmap(list.get(position));
        return mimageView;
    }
}
