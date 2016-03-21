package com.codans.paopaoxia.activity;

import android.content.Intent;
import android.os.Bundle;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;
import com.codans.paopaoxia.module.homepage.HomepageActivity;

/**
 * 项目名称：paopaoxia
 * 类描述：
 * 创建人：胡继春
 * 创建时间：2016/3/19 15:25
 * 修改人：胡继春
 * 修改时间：2016/3/19 15:25
 * 修改备注；
 */
public class WelcomeActivity extends BaseActivity {
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_welcome);
        mThread.start();
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

    Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(WelcomeActivity.this, HomepageActivity.class));
            finish();
        }
    }
    );
}
