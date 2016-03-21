package com.codans.paopaoxia.module.bubbling;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;
import com.codans.paopaoxia.utils.cache.Utils;
import com.codans.paopaoxia.view.SelectPicPopupWindow;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目名称：paopaoxia
 * 类描述：冒泡界面
 * 创建人：hujc
 * 创建时间：2016/3/4 18:47
 * 修改人：hujc
 * 修改时间：2016/3/4 18:47
 * 修改备注；
 */
public class BubblingActivity extends BaseActivity{
    private String TAG = "BubblingActivity";

    private EditText et_bubbling_title,et_bubbling_content,et_bubbling_number,et_bubbling_modou;
    private ImageView iv_bubbling_icon;
    private SelectPicPopupWindow mPopupWindow;

    private String title,content,number,modou;
    private int modouNumber;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_bubbling);
    }

    @Override
    public void getIntentData(Bundle savedInstanceState) {

    }

    @Override
    public void initViews() {
        //标题内容
        et_bubbling_title = (EditText) findViewById(R.id.et_bubbling_title);
        //描述内容
        et_bubbling_content = (EditText) findViewById(R.id.et_bubbling_content);
        //数量
        et_bubbling_number = (EditText) findViewById(R.id.et_bubbling_number);
        //魔豆数量
        et_bubbling_modou = (EditText) findViewById(R.id.et_bubbling_modou);

        //泡泡图标
        iv_bubbling_icon = (ImageView) findViewById(R.id.iv_bubbling_icon);
    }

    @Override
    public void initListeners() {
     iv_bubbling_icon.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            mPopupWindow = new SelectPicPopupWindow(BubblingActivity.this,itemsOnclick);

             mPopupWindow.showAtLocation(BubblingActivity.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
         }
     });
    }

    @Override
    public void initData() {
            title = et_bubbling_title.getText().toString();
    }

    private void onClick(View v){
        switch (v.getId()){
            case R.id.btn_bubbling_ok:
                //标题信息
                title = et_bubbling_title.getText().toString();
                //内容信息
                content = et_bubbling_content.getText().toString();
                //数量信息
                number = et_bubbling_number.getText().toString();
                //魔豆数量信息
                modou = et_bubbling_modou.getText().toString();
                //判断魔豆数量是否为int值
                if (Utils.judgeStringType(modou) == 0){
                    modouNumber = Integer.parseInt(modou);
                }else{
                    Toast.makeText(this,"魔豆数量只能为数字类型",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * popup弹出框中的按钮监听
     */
    private View.OnClickListener itemsOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopupWindow.dismiss();
            switch (v.getId()){
                case R.id.btn_take_photo:
                    break;
                case R.id.btn_pick_photo:
                    break;
            }
        }
    };

}
