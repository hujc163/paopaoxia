package com.codans.paopaoxia.module.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;
import com.codans.paopaoxia.module.particulars.BubbleParticularsActivity;

/**
 * 项目名称：paopaoxia
 * 类描述：参与的泡泡界面
 * 创建人：hujc
 * 创建时间：2016/3/4 19:40
 * 修改人：hujc
 * 修改时间：2016/3/4 19:40
 * 修改备注；
 */
public class ParticipationActivity extends BaseActivity{
    private String TAG = "ParticipationActivity";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_participation);
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

    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_participation_bubble:
                intent.setClass(this, BubbleParticularsActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
