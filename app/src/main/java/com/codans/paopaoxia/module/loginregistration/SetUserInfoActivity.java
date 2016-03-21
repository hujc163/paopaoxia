package com.codans.paopaoxia.module.loginregistration;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codans.paopaoxia.app.MyApplications;
import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;
import com.codans.paopaoxia.module.homepage.HomepageActivity;
import com.codans.paopaoxia.utils.UrlUtils;
import com.codans.paopaoxia.utils.cache.Utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 项目名称：paopaoxia
 * 类描述：注册后上传设置用户信息的界面
 * 创建人：胡继春
 * 创建时间：2016/3/11 16:44
 * 修改人：胡继春
 * 修改时间：2016/3/11 16:44
 * 修改备注；
 */
public class SetUserInfoActivity extends BaseActivity{
    private String TAG = "SetUserInfoActivity";
    //上传注册信息网址
    private String AppendInfo_PATH = UrlUtils.Url+UrlUtils.Url_AppendInfo;

    private EditText et_setuserinfo_name;
    private Button btn_take_photo,btn_pick_photo,btn_cancel;
    private ImageView iv_setuserinfo_icon;
    private LinearLayout setuserinfo;

    private int CAMERA_RESULT = 100;
    private int RESULT_LOAD_IMAGE = 200;

    private String saveDir = Environment.getExternalStorageDirectory()
            .getPath() + "/temp_image";

    //用户姓名
    private String Name;
    //图片url
    private String HeadImage;
    //性别标识，0为女，1为男
    private String Gender;

    private Bitmap photo;
    private File mPhotoFile;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_setuserinfo);
    }

    @Override
    public void getIntentData(Bundle savedInstanceState) {

    }

    @Override
    public void initViews() {
        et_setuserinfo_name = (EditText) findViewById(R.id.et_setuserinfo_name);
        iv_setuserinfo_icon = (ImageView) findViewById(R.id.iv_setuserinfo_icon);
        setuserinfo = (LinearLayout) findViewById(R.id.setuserinfo);
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        Gender = 0+"";
        HeadImage = "http://img1.touxiang.cn/uploads/20120509/09-014416_603.jpg";
    }

    public void onClick(View v){
        if (v.getId() == R.id.iv_setuserinfo_icon){
            Toast.makeText(this,"dd",Toast.LENGTH_SHORT).show();
            shouPopupWindow();
        }
        if (v.getId() == R.id.btn_setuserinfo_ok){
            Gender = 0+"";
            Name = et_setuserinfo_name.getText().toString();
            AppendInfo();
        }
    }

    //上传注册信息
    private void AppendInfo(){
        StringRequest appendInfo = new StringRequest(Request.Method.POST, AppendInfo_PATH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        startActivity(new Intent(SetUserInfoActivity.this, HomepageActivity.class));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        }) {
            //在这里设置需要post的参数
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> params = new HashMap<String, String>();
                params.put("Token", MyApplications.Token);
                params.put("Name", "sf");
                params.put("HeadImage", HeadImage);
                params.put("Gender", "0");
                return params;
            }
        };
        if (MyApplications.queues != null) {
            MyApplications.queues.cancelAll(this);
        }
        MyApplications.queues.add(appendInfo);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void shouPopupWindow(){
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(SetUserInfoActivity.this).inflate(
                R.layout.item_popup, null);

        btn_take_photo = (Button) contentView.findViewById(R.id.btn_take_photo);
        btn_pick_photo = (Button) contentView.findViewById(R.id.btn_pick_photo);
        btn_cancel = (Button) contentView.findViewById(R.id.btn_cancel);



        final PopupWindow popupWindow = new PopupWindow(contentView,
                300,400, true);

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popub));
        popupWindow.setTouchable(true);



        popupWindow.showAsDropDown(et_setuserinfo_name, Gravity.CENTER, 0, 0);

        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                destoryImage();
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    mPhotoFile = new File(saveDir, "temp.jpg");
                    mPhotoFile.delete();
                    if (!mPhotoFile.exists()) {
                        try {
                            mPhotoFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplication(), "照片创建失败!",
                                    Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    Intent intent = new Intent(
                            "android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(mPhotoFile));
                    startActivityForResult(intent, CAMERA_RESULT);
                } else {
                    Toast.makeText(getApplication(), "sdcard无效或没有插入!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_pick_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {
            if (mPhotoFile != null && mPhotoFile.exists()) {
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                bitmapOptions.inSampleSize = 8;
                int degree = readPictureDegree(mPhotoFile.getAbsolutePath());
                Bitmap bitmap = BitmapFactory.decodeFile(mPhotoFile.getPath(),
                        bitmapOptions);
                bitmap = rotaingImageView(degree, bitmap);
                iv_setuserinfo_icon.setImageBitmap(bitmap);
            }
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            iv_setuserinfo_icon.setImageBitmap(BitmapFactory
                    .decodeFile(picturePath));
        }
    }

    private static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    private static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    private void destoryImage() {
        if (photo != null) {
            photo.recycle();
            photo = null;
        }
    }

    @Override
    protected void onDestroy() {
        destoryImage();
        super.onDestroy();
    }
}
