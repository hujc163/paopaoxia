package com.codans.paopaoxia.module.bubbling;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;
import com.codans.paopaoxia.utils.cache.Utils;

/**
 * 项目名称：paopaoxia
 * 类描述：更多设置界面
 * 创建人：胡继春
 * 创建时间：2016/3/17 19:05
 * 修改人：胡继春
 * 修改时间：2016/3/17 19:05
 * 修改备注；
 */
public class SendBubbleSetting extends BaseActivity{
    private EditText et_sendbubblesetting_time,et_sendbubblesetting_visibility,et_sendbubblesetting_reward,et_sendbubblesetting_love;

    private int time,visibility,reward,love;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_sendbubblesetting);
    }

    @Override
    public void getIntentData(Bundle savedInstanceState) {

    }

    @Override
    public void initViews() {
        et_sendbubblesetting_time = (EditText) findViewById(R.id.et_sendbubblesetting_time);
        et_sendbubblesetting_visibility = (EditText) findViewById(R.id.et_sendbubblesetting_visibility);
        et_sendbubblesetting_reward = (EditText) findViewById(R.id.et_sendbubblesetting_reward);
        et_sendbubblesetting_love = (EditText) findViewById(R.id.et_sendbubblesetting_love);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        time = 24;
        visibility = 5000;
        reward = 0;
    }

    public void onClick(View v){
        if (v.getId() == R.id.btn_sendbubblesetting_ok){
            if (Utils.judgeStringType(et_sendbubblesetting_time.getText().toString()) == 0){
                time = Integer.parseInt(et_sendbubblesetting_time.getText().toString());
                Log.i("大力出奇迹",time+"");
            }
            if (Utils.judgeStringType(et_sendbubblesetting_visibility.getText().toString()) == 0){
                visibility = Integer.parseInt(et_sendbubblesetting_visibility.getText().toString());
            }else{
            }
            if (Utils.judgeStringType(et_sendbubblesetting_reward.getText().toString()) == 0&&!"".equals(et_sendbubblesetting_reward.getText().toString())){
                reward = Integer.parseInt(et_sendbubblesetting_reward.getText().toString());
            }else{
            }
            //使用Intent传回数据
            Intent intent = new Intent();
            //把返回数据放入Intent
            intent.putExtra("time",time);
            intent.putExtra("visibility",visibility);
            intent.putExtra("reward",reward);
            SendBubbleSetting.this.setResult(113, intent);
            SendBubbleSetting.this.finish();
        }
        if (v.getId() == R.id.btn_sendbubblesetting_cancel){
            SendBubbleSetting.this.finish();
        }
    }
}
