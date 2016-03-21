package com.codans.paopaoxia.module.homepage;

import android.os.Bundle;
import android.view.View;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;

/**
 * 项目名称：paopaoxia
 * 类描述：发布泡泡时的更多设置界面
 * 创建人：胡继春
 * 创建时间：2016/3/9 13:38
 * 修改人：胡继春
 * 修改时间：2016/3/9 13:38
 * 修改备注；
 */
public class MoreSettingActivity extends BaseActivity{
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_moresetting);
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
        switch (V.getId()){
            case R.id.btn_moresetting_ok:
                finish();
                break;
            default:
                break;
        }
    }
}
