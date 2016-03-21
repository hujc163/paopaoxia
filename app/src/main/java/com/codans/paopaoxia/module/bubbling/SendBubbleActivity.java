package com.codans.paopaoxia.module.bubbling;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codans.paopaoxia.adapter.MyGridViewAdapter;
import com.codans.paopaoxia.app.MyApplications;
import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;
import com.codans.paopaoxia.module.Assist.BigImageActivity;
import com.codans.paopaoxia.utils.UrlUtils;
import com.codans.paopaoxia.utils.cache.Utils;
import com.codans.paopaoxia.view.SelectPicPopupWindow;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 项目名称：paopaoxia
 * 类描述：
 * 创建人：胡继春
 * 创建时间：2016/3/17 13:57
 * 修改人：胡继春
 * 修改时间：2016/3/17 13:57
 * 修改备注；
 */
public class SendBubbleActivity extends BaseActivity {
    private String TAG = "SendBubbleActivity";
    private String CreatThread_PATH = UrlUtils.Url + UrlUtils.Url_CreatThread;           //发泡泡的路径
    private String saveDir = Environment.getExternalStorageDirectory()
            .getPath() + "/paopaoxia_photo";

    private String ciphertext;                                                           //加密后的字符串

    private String title, content;

    private static final int REQUEST_IMAGE = 2;                                          //选择照片的请求码
    private int CAMERA_RESULT = 100;                                                     //调用相机拍照请求码
    private int BIG_IMAGE = 999;                                                         //查看大图的请求码
    private int mLifeHours, mReward, mVisibility, mMoveFlag;


    private File mPhotoFile, savePath;
    private Bitmap photo,defaultPhoto;

    private LinearLayout ll_sendbubble_title;
    private LinearLayout ll_sendbubble_context;
    private EditText et_sendbubble_title, et_sendbubble_content;
    private SelectPicPopupWindow mPopupWindow;
    private CheckBox cb_sendbubble_bubbleinfo, cb_sendbubble_move;
    private TextView tv_sendbubble_setting;
    private GridView mgridView;                                                         //放选择的图片的GridView

    private List<Bitmap> mListDrawable;                                                 //选择的照片数组
    private List<String> imagepath;                                                     //存放图片地址的数组

    private MyGridViewAdapter myAdapter;                                                //从相册进行选择

    private Map<Object, Object> data;                                                  //要上传的信息
    private Map<String, String> params;                                                    //String参数

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_sendbubble);
    }

    @Override
    public void getIntentData(Bundle savedInstanceState) {

    }

    @Override
    public void initViews() {
        //标题布局
        ll_sendbubble_title = (LinearLayout) findViewById(R.id.ll_sendbubble_title);
        //内容布局
        ll_sendbubble_context = (LinearLayout) findViewById(R.id.ll_sendbubble_context);
        //标题内容
        et_sendbubble_title = (EditText) findViewById(R.id.et_sendbubble_title);
        //描述内容
        et_sendbubble_content = (EditText) findViewById(R.id.et_sendbubble_content);

//        //泡泡图标
//        iv_sendbubble_icon = (ImageView) findViewById(R.id.iv_sendbubble_icon);

        //泡泡是否跟随移动按钮
        cb_sendbubble_move = (CheckBox) findViewById(R.id.cb_sendbubble_move);

        //泡泡周期按钮
        cb_sendbubble_bubbleinfo = (CheckBox) findViewById(R.id.cb_sendbubble_bubbleinfo);

        //设置按钮
        tv_sendbubble_setting = (TextView) findViewById(R.id.tv_sendbubble_setting);

        mgridView = (GridView) findViewById(R.id.gv_sendbubble_photo);
    }

    @Override
    public void initListeners() {

        //点击选择泡泡默认周期
        cb_sendbubble_bubbleinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_sendbubble_bubbleinfo.isChecked()) {
                    cb_sendbubble_bubbleinfo.setChecked(true);
                    mMoveFlag = 0;
                } else {
                    cb_sendbubble_bubbleinfo.setChecked(false);
                    mMoveFlag = 1;
                }
            }
        });

        //点击设置泡泡跟随移动
        cb_sendbubble_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_sendbubble_move.isChecked()) {

                } else {

                }
            }
        });

        //设置按钮点击打开设置界面
        tv_sendbubble_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(SendBubbleActivity.this, SendBubbleSetting.class), 13);
            }
        });

        mgridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListDrawable.size() == 1&&position == 0){
                    mPopupWindow = new SelectPicPopupWindow(SendBubbleActivity.this, itemsOnclick);

                    mPopupWindow.showAtLocation(SendBubbleActivity.this.findViewById(R.id.sendbubble_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }else if (mListDrawable.size()!=0){
                   if (position == (mListDrawable.size()-1)){
                       mPopupWindow = new SelectPicPopupWindow(SendBubbleActivity.this, itemsOnclick);

                       mPopupWindow.showAtLocation(SendBubbleActivity.this.findViewById(R.id.sendbubble_main), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                   }else{
                       Intent intent = new Intent();
                       intent.putExtra("imagepath", imagepath.get(position));
                       intent.putExtra("position",position);
                       intent.setClass(SendBubbleActivity.this, BigImageActivity.class);
                       startActivityForResult(intent, BIG_IMAGE);

                   }
                }
            }
        });
    }

    @Override
    public void initData() {
        //代码适配标题布局的大小
        LinearLayout.LayoutParams mLayoutParamstitle = new LinearLayout.LayoutParams(MyApplications.windowsWidth,MyApplications.windowsheight/12*1);
        ll_sendbubble_title.setLayoutParams(mLayoutParamstitle);

        //代码适配内容布局的大小
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(MyApplications.windowsWidth/22*20,MyApplications.windowsheight/10*8);
        mLayoutParams.setMargins(MyApplications.windowsWidth/22,MyApplications.windowsheight/40,MyApplications.windowsWidth/22,MyApplications.windowsheight/20);
        ll_sendbubble_context.setLayoutParams(mLayoutParams);

        data = new HashMap<>();
        params = new HashMap<>();
        imagepath = new ArrayList<>();

        imagepath.add("kong");

        defaultPhoto = BitmapFactory.decodeResource(getResources(),R.drawable.icon_camera3x);
        //初始化照片数组
        mListDrawable = new ArrayList<Bitmap>();
        //添加默认打开图片选择的图片
        mListDrawable.add(defaultPhoto);
        //初始化用于显示照片的GridView的适配器
        myAdapter = new MyGridViewAdapter(mListDrawable, this);
        mgridView.setAdapter(myAdapter);

        //判断目录文件是否存在，如果不存在就创建目录
        savePath = new File(saveDir);
        if (!savePath.exists()) {
            savePath.mkdirs();
        }

        mMoveFlag = 0;
    }

    //TODO 按钮监听
    public void onClick(View v) throws JSONException {
        switch (v.getId()) {
            //点击下一步进行的操作
            case R.id.btn_sendbubble_next:
                //泡泡标题信息
                title = et_sendbubble_title.getText().toString();
                //泡泡内容信息
                content = et_sendbubble_content.getText().toString();

                if ("".equals(title)) {
                    Toast.makeText(SendBubbleActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if ("".equals(content)) {
                        Toast.makeText(SendBubbleActivity.this, "说点什么吧", Toast.LENGTH_SHORT).show();
                    } else {
                        setParams();
                        xUtilsPost();
                        startActivity(new Intent(SendBubbleActivity.this, CustomIconActivity.class));
                    }

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
            switch (v.getId()) {
                case R.id.btn_take_photo:                           //拍照获取

                    photoGraph();

                    break;
                case R.id.btn_pick_photo:                           //相册获取

                    Intent intent = new Intent(SendBubbleActivity.this, MultiImageSelectorActivity.class);
                    // 是否显示调用相机拍照
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, false);
                    // 最大图片选择数量
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 3);
                    // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                    startActivityForResult(intent, REQUEST_IMAGE);

                    break;
            }
        }
    };

    //startActivityForResult的返回方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == BIG_IMAGE&&20 == resultCode){
            mListDrawable.remove(data.getExtras().get("position"));
            imagepath.remove(data.getExtras().get("position"));
            myAdapter.notifyDataSetChanged();
        }

        //从相册获取的返回图片
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                // 获取返回的图片列表
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (mListDrawable != null && mListDrawable.size() <= 6) {
                    for (int i = 0; i < path.size(); i++) {
                        mListDrawable.add(BitmapFactory.decodeFile(path.get(i)));
                        imagepath.add(path.get(i));
                    }
                    imagepath.remove(imagepath.size()-path.size()-1);
                    imagepath.add("kong");
                    mListDrawable.remove(mListDrawable.size()-path.size()-1);
                    mListDrawable.add(defaultPhoto);
                    myAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(SendBubbleActivity.this, "图片太多了", Toast.LENGTH_SHORT).show();
                }

            }
        }

        //获取设置返回的信息
        if (resultCode == 113) {
            mReward = data.getExtras().getInt("reward");
            mLifeHours = data.getExtras().getInt("time");
            mVisibility = data.getExtras().getInt("visibility");

            cb_sendbubble_bubbleinfo.setText(mLifeHours + "小时/" + mVisibility + "米");
        }


        //拍照获取的返回照片
        if (requestCode == CAMERA_RESULT && resultCode == RESULT_OK) {
            if (mPhotoFile != null && mPhotoFile.exists()) {
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                bitmapOptions.inSampleSize = 8;
                int degree = readPictureDegree(mPhotoFile.getAbsolutePath());
                Bitmap bitmap = BitmapFactory.decodeFile(mPhotoFile.getPath(),
                        bitmapOptions);
                bitmap = rotaingImageView(degree, bitmap);
                imagepath.remove(imagepath.size()-1);
                imagepath.add("paizhao");
                imagepath.add("kong");
                mListDrawable.remove(mListDrawable.size() - 1);
                mListDrawable.add(bitmap);
                mListDrawable.add(defaultPhoto);
                myAdapter.notifyDataSetChanged();
//                iv_sendbubble_icon.setImageBitmap(bitmap);
            }
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


    /**
     * 旋转图片的方法
     *
     * @param angle
     * @param bitmap
     * @return
     */
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

    /**
     * 将参数加密的方法
     *
     * @throws JSONException
     */
    private void setParams() throws JSONException {
        JSONObject ClientKey = new JSONObject();
        ClientKey.put("Latitude", MyApplications.currentMapCentreLatlng.latitude + "");
        ClientKey.put("Subject", title);
        ClientKey.put("Longitude", MyApplications.currentMapCentreLatlng.longitude + "");
        ClientKey.put("LifeHours", 100 + "");
        ClientKey.put("LifeHours", mLifeHours + "");
        ClientKey.put("TaskId", "");
        ClientKey.put("Password", "");
        ClientKey.put("Token", MyApplications.Token);
        ClientKey.put("Reward", 100 + "");
        ClientKey.put("Reward", mReward + "");
        ClientKey.put("ThreadId", "");
        ClientKey.put("MoveFlag", 0 + "");
        ClientKey.put("Content", content);
        ClientKey.put("Visibility", 100 + "");
        ClientKey.put("Visibility", mVisibility + "");
        ClientKey.put("IsRemote", 1 + "");
        ciphertext = Utils.md5(ClientKey.toString());
// 接着我们使用JsonObject封装{"Person":{"username":"zhangsan","age":"12"}}
//        JSONObject Authorization = new JSONObject();
//        Authorization.put("Data", ClientKey);
    }

    private void xUtilsPost() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("Data", ciphertext);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, CreatThread_PATH, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
            }

            @Override
            public void onFailure(HttpException e, String s) {
            }
        });


    }

    private void destoryImage() {
        if (photo != null) {
            photo.recycle();
            photo = null;
        }
    }


    private void postJson() {
        //首先声明一下Url
        final String urlPath = CreatThread_PATH;
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL(urlPath);

                    // 然后我们使用httpPost的方式把lientKey封装成Json数据的形式传递给服务器
                    // 在这里呢我们要封装的时这样的数据
                    // {"Person":{"username":"zhangsan","age":"12"}}
                    // 我们首先使用的是JsonObject封装 {"username":"zhangsan","age":"12"}
                    JSONObject ClientKey = new JSONObject();
                    ClientKey.put("Latitude", MyApplications.currentMapCentreLatlng.latitude + "");
                    ClientKey.put("Subject", title);
                    ClientKey.put("Longitude", MyApplications.currentMapCentreLatlng.longitude + "");
                    ClientKey.put("LifeHours", 100 + "");
                    ClientKey.put("LifeHours", mLifeHours + "");
                    ClientKey.put("TaskId", "");
                    ClientKey.put("Password", "");
                    ClientKey.put("Token", MyApplications.Token);
                    ClientKey.put("Reward", 100 + "");
                    ClientKey.put("Reward", mReward + "");
                    ClientKey.put("ThreadId", "");
                    ClientKey.put("MoveFlag", 0 + "");
                    ClientKey.put("Content", content);
                    ClientKey.put("Visibility", 100 + "");
                    ClientKey.put("Visibility", mVisibility + "");
                    ClientKey.put("IsRemote", 1 + "");
// 接着我们使用JsonObject封装{"Person":{"username":"zhangsan","age":"12"}}
                    JSONObject Authorization = new JSONObject();
                    Authorization.put("Data", ClientKey);
                    Authorization.put("FileName", "");
//我们把JSON数据转换成String类型使用输出流向服务器写
                    String content = String.valueOf(Authorization);
// 现在呢我们已经封装好了数据,接着呢我们要把封装好的数据传递过去
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
// 设置允许输出
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
// 设置User-Agent: Fiddler
                    conn.setRequestProperty("ser-Agent", "Fiddler");
// 设置contentType
                    conn.setRequestProperty("Content-Type", "application/json");
                    OutputStream os = conn.getOutputStream();
                    os.write(content.getBytes());
                    os.close();
//服务器返回的响应码
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        Log.i("大力出奇迹", "上传成功了");
                    } else {
                        Log.i("大力出奇迹", code + "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }).start();


    }

    /**
     * 拍照的方法
     */
    private void photoGraph() {
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


    //将进行剪裁后的图片显示到UI界面上
    private void setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            Bitmap photo = bundle.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            //     iv.setBackgroundDrawable(drawable);
//            mListDrawable.add(drawable);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        myAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onDestroy() {
        destoryImage();
        super.onDestroy();
    }
}
