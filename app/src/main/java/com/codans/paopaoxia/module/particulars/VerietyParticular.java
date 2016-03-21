package com.codans.paopaoxia.module.particulars;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;
import com.codans.paopaoxia.module.message.MessageActivity;
import com.codans.paopaoxia.module.varietyshop.DealActivity;

/**
 * 项目名称：paopaoxia
 * 类描述：
 * 创建人：胡继春
 * 创建时间：2016/3/9 17:19
 * 修改人：胡继春
 * 修改时间：2016/3/9 17:19
 * 修改备注；
 */
public class VerietyParticular extends BaseActivity{
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_verietyparticular);
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
            case R.id.btn_verietyparticular_calllz:
                intent.setClass(this, MessageActivity.class);
                break;
            case R.id.btn_verietyparticular_comment:
                intent.setClass(this,CommentActivity.class);
                break;
            case R.id.btn_verietyparticular_myneed:
                intent.setClass(this, DealActivity.class);
                break;
            case R.id.btn_verietyparticular_userinfo:
                intent.setClass(this,UserInfoActivity.class);
            default:
                break;
        }
        startActivity(intent);
    }
}
