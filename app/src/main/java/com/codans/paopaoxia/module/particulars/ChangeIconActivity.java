package com.codans.paopaoxia.module.particulars;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;
import com.codans.paopaoxia.module.homepage.HomepageActivity;

/**
 * 项目名称：paopaoxia
 * 类描述：
 * 创建人：胡继春
 * 创建时间：2016/3/9 14:48
 * 修改人：胡继春
 * 修改时间：2016/3/9 14:48
 * 修改备注；
 */
public class ChangeIconActivity extends BaseActivity{
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_changeicon);
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
            case R.id.btn_changeicon_modify:
                intent.setClass(this, HomepageActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
