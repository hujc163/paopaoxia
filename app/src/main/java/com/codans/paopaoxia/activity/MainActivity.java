package com.codans.paopaoxia.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codans.paopaoxia.app.MyApplications;
import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;
import com.codans.paopaoxia.module.homepage.HomepageActivity;
import com.codans.paopaoxia.module.loginregistration.LoginActivity;
import com.codans.paopaoxia.module.loginregistration.RegisterActivity;
import com.codans.paopaoxia.utils.UrlUtils;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {
    private String TAG = "MainActivity";

    private Context mContext = this;
    //包的信息
    private PackageInfo info;

    private String PACKAGE_NAME = "com.codans.paopaoxia.app";
    private String VERSION_KEY = "version_key";
    private String Url_Report = UrlUtils.Url + UrlUtils.Url_Report;

    private IWXAPI mWeixinAPI;



    private ImageView iv;

    @Override
    public void setContentView() {
        getMetrics();
        //判断是否有帐号登录，如果有则直接进入主页
        if (MyApplications.mTag) {
            Intent intent = new Intent(this, HomepageActivity.class);
            startActivity(intent);
        } else {
            getIsFirstStart();
            setContentView(R.layout.activity_main);
        }

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
        mWeixinAPI = WXAPIFactory.createWXAPI(this, "wx84a51e51fb653572", true);
        mWeixinAPI.registerApp("wx84a51e51fb653572");
    }


    public void onClick(View V) {
        switch (V.getId()) {
            //点击登录的操作
            case R.id.btn_main_login:
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                break;
            //点击注册的操作
            case R.id.btn_main_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_main_weixinlogin:
                loginWithAPI();
                break;
            default:
                break;
        }
    }

    /**
     * 判断设备是否第一次运行程序
     */

    //TODO 判断设备是否第一次运行程序
    private void getIsFirstStart() {

        try {
            info = getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int currentVersion = info.versionCode;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int lastVersion = prefs.getInt(VERSION_KEY, 0);
        if (currentVersion > lastVersion) {
            //如果当前版本大于上次版本，该版本属于第一次启动
            postEquipmentInfo();
            //将当前版本写入preference中，则下次启动的时候，据此判断，不再为首次启动
            prefs.edit().putInt(VERSION_KEY, currentVersion).commit();
            postVerifyToken();
        }
    }

    /**
     * 程序首次运行上传设备信息的方法
     */
    //TODO 程序首次运行上传设备信息的方法
    private void postEquipmentInfo() {
        StringRequest postEquipmentInfo = new StringRequest(Request.Method.POST, Url_Report,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("DeviceId", MyApplications.DeviceId);
                map.put("Os", MyApplications.Os);
                map.put("Model", MyApplications.Model);


                return map;
            }
        };
        if (MyApplications.queues != null) {
            MyApplications.queues.cancelAll(this);
        }
        MyApplications.queues.add(postEquipmentInfo);
    }

    /**
     * 使用volley提交数据的方法
     */
    private void volleyPostInfo(int Name, String Url_Path, final Context mContext, final Map<String, String> mMap) {


    }

    /**
     * 上报设备信息
     */
    //TODO 上报设备信息
    private void postVerifyToken() {
        StringRequest postVerifyToken = new StringRequest(Request.Method.POST, Url_Report,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
//                        Map<String, String> map = new HashMap<String, String>();
//                        map.put("name1", "value1");
//                        map.put("name2", "value2");
                Map<String, String> map = new HashMap<String, String>();
                map.put("DeviceId", MyApplications.DeviceId);
                map.put("Os", MyApplications.Os);
                map.put("Model", MyApplications.Model);


                return map;
            }
        };
        if (MyApplications.queues != null) {
            MyApplications.queues.cancelAll(this);
        }
        MyApplications.queues.add(postVerifyToken);
    }

    /**
     * 唤起微信登录
     */
    private void loginWithAPI() {
        if (!mWeixinAPI.isWXAppInstalled()) {
            Toast.makeText(this, "没有安装微信呢", Toast.LENGTH_SHORT).show();
            return;
        }

        SendAuth.Req req = new SendAuth.Req();

        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";

        mWeixinAPI.sendReq(req);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取屏幕尺寸
     */
    private void getMetrics(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）

        MyApplications.windowsWidth =  metric.widthPixels;
        MyApplications.windowsheight = metric.heightPixels;
    }
}
