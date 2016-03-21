package com.codans.paopaoxia.base;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView();
        getIntentData(savedInstanceState);
        initViews();
        initListeners();
        initData();
    }

    /**
     * 设置布局文件
     */
    public abstract void setContentView();

    /**
     * 获取intent数据
     */
    public abstract void getIntentData(Bundle savedInstanceState);

    /**
     * 初始化view
     */
    public abstract void initViews();

    /**
     * 设置监听9
     */
    public abstract void initListeners();

    /**
     * 初始化数据
     */
    public abstract void initData();

    

}
