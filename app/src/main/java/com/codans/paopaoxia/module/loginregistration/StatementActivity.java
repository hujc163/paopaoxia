package com.codans.paopaoxia.module.loginregistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;

/**
 * 项目名称：paopaoxia
 * 类描述：
 * 创建人：胡继春
 * 创建时间：2016/3/10 17:20
 * 修改人：胡继春
 * 修改时间：2016/3/10 17:20
 * 修改备注；
 */
public class StatementActivity extends BaseActivity{
    private CheckBox cb_statement_ok;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_statement);
    }

    @Override
    public void getIntentData(Bundle savedInstanceState) {

    }

    @Override
    public void initViews() {
        cb_statement_ok = (CheckBox) findViewById(R.id.cb_statement_ok);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

    }

    public void onClick(View V){
        switch (V.getId()){
            case R.id.btn_statement_back:
                finish();
                break;
            case R.id.btn_statement_next:
                if (cb_statement_ok.isChecked()){
                    Intent intetn = new Intent(this,RegisterActivity.class);
                    startActivity(intetn);
                }
        }
    }
}
