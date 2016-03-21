package com.codans.paopaoxia.module.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;
import com.codans.paopaoxia.module.particulars.BubbleParticularsActivity;

/**
 * 项目名称：paopaoxia
 * 类描述：我的足迹界面
 * 创建人：hujc
 * 创建时间：2016/3/4 19:31
 * 修改人：hujc
 * 修改时间：2016/3/4 19:31
 * 修改备注；
 */
public class MyFootPrintActivity extends BaseActivity{
    private String TAG = "MyFootPrintActivity";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_myfootprint);
    }

    @Override
    public void getIntentData(Bundle savedInstanceState) {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    public void onClick(View V){
        Intent intent = new Intent();
        switch (V.getId()){
            case R.id.btn_myfootprint_bubble:
                intent.setClass(this, BubbleParticularsActivity.class);
            break;
            case R.id.btn_myfootprint_hotmap:
                intent.setClass(this,HotMapActivity.class);
            break;
            default:
                break;
        }
        startActivity(intent);
    }
}
