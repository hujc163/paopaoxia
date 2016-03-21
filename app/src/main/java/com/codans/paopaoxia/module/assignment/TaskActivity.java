package com.codans.paopaoxia.module.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;

/**
 * 项目名称：paopaoxia
 * 类描述：任务界面
 * 创建人：hujc
 * 创建时间：2016/3/4 19:00
 * 修改人：hujc
 * 修改时间：2016/3/4 19:00
 * 修改备注；
 */
public class TaskActivity extends BaseActivity {
    private String TAG = "TaskActivity";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_task);
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

    public void onClick(View v){
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_task_signin:
                intent.setClass(this,SignInActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
