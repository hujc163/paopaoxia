package com.codans.paopaoxia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;
import com.codans.paopaoxia.module.personalcenter.AttentionActivity;

/**
 * 项目名称：paopaoxia
 * 类描述：关于界面
 * 创建人：hujc
 * 创建时间：2016/3/4 20:01
 * 修改人：hujc
 * 修改时间：2016/3/4 20:01
 * 修改备注；
 */
public class AboutActivity extends BaseActivity{
    private String TAG = "AboutActivity";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_about);
    }

    @Override
    public void getIntentData(Bundle savedInstanceState) {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners()
    {

    }
    @Override
    public void initData() {

    }

    public void onClick(View V){
        switch (V.getId()){
             case R.id.tv_test_about:
                 Intent i = new Intent(AboutActivity.this, AttentionActivity.class);
                 startActivity(i);
                 break;
            default:
                break;
        }
    }
}
