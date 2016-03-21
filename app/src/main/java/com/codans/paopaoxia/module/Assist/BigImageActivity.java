package com.codans.paopaoxia.module.Assist;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;

/**
 * 项目名称：paopaoxia
 * 类描述：显示大图的界面
 * 创建人：胡继春
 * 创建时间：2016/3/21 15:38
 * 修改人：胡继春
 * 修改时间：2016/3/21 15:38
 * 修改备注；
 */
public class BigImageActivity extends BaseActivity{

    private ImageView iv_bigimage_back,iv_bigimage_cancel,iv_bigimage_image;

    private int RESULT_CANCEL = 20;             //删除图片的标志

    private String imagepath;
    private int position;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_bigimage);
    }

    @Override
    public void getIntentData(Bundle savedInstanceState) {
    }

    @Override
    public void initViews() {
        iv_bigimage_cancel = (ImageView) findViewById(R.id.iv_bigimage_cancel);
        iv_bigimage_back = (ImageView) findViewById(R.id.iv_bigimage_back);

        iv_bigimage_image = (ImageView) findViewById(R.id.iv_bigimage_image);
    }

    @Override
    public void initListeners() {
        iv_bigimage_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("position",position);
                BigImageActivity.this.setResult(RESULT_CANCEL,intent);
                BigImageActivity.this.finish();
            }
        });

        iv_bigimage_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigImageActivity.this.finish();
            }
        });
    }

    @Override
    public void initData() {
        position = getIntent().getIntExtra("position", -1);
        imagepath = getIntent().getStringExtra("imagepath");
        if (imagepath!=null){
            iv_bigimage_image.setImageBitmap(BitmapFactory.decodeFile(imagepath));
        }
    }
}
