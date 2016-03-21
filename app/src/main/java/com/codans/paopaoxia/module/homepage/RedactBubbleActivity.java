package com.codans.paopaoxia.module.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;

/**
 * 项目名称：paopaoxia
 * 类描述：点击冒泡，编辑新的泡泡界面
 * 创建人：胡继春
 * 创建时间：2016/3/9 13:41
 * 修改人：胡继春
 * 修改时间：2016/3/9 13:41
 * 修改备注；
 */
public class RedactBubbleActivity extends BaseActivity{
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_redactbubble);
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
            case R.id.btn_moresettings:
                intent.setClass(this,MoreSettingActivity.class);
                break;
            case R.id.btn_redactbubble_next:
                intent.setClass(this,ReleaseBubbleActivity.class);
                break;

            default:
                break;
        }
        startActivity(intent);
    }

}
