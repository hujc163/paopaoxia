package com.codans.paopaoxia.module.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;
import com.codans.paopaoxia.module.particulars.VerietyParticular;
import com.codans.paopaoxia.module.varietyshop.ReleaseVerietyActivity;

/**
 * 项目名称：paopaoxia
 * 类描述：我的杂货铺界面
 * 创建人：胡继春
 * 创建时间：2016/3/9 17:17
 * 修改人：胡继春
 * 修改时间：2016/3/9 17:17
 * 修改备注；
 */
public class MyVerietyShop extends BaseActivity{
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_myverietyshop);
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
            case R.id.btn_myverietyshop_fabu:
                intent.setClass(this, ReleaseVerietyActivity.class);
                break;
            case R.id.btn_myverietyshop_shangpin:
                intent.setClass(this, VerietyParticular.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
