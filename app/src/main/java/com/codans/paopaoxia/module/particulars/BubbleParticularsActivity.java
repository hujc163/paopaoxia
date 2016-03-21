package com.codans.paopaoxia.module.particulars;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;

/**
 * 项目名称：paopaoxia
 * 类描述：泡泡详情页
 * 创建人：胡继春
 * 创建时间：2016/3/9 14:23
 * 修改人：胡继春
 * 修改时间：2016/3/9 14:23
 * 修改备注；
 */
public class BubbleParticularsActivity extends BaseActivity{
    @Override
    public void setContentView() {

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
            case R.id.btn_bubbleparticulars_changeicon:
                intent.setClass(this,ChangeIconActivity.class);
                break;
            case R.id.btn_bubbleparticulars_comment:
                intent.setClass(this,CommentActivity.class);
                break;
            case R.id.btn_bubbleparticulars_userinfo:
                intent.setClass(this,UserInfoActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
