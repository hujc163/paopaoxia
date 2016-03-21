package com.codans.paopaoxia.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.model.LatLng;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.codans.paopaoxia.utils.HttpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目名称：paopaoxia
 * 类描述：
 * 创建人：胡继春
 * 创建时间：2016/3/5 10:55
 * 修改人：胡继春
 * 修改时间：2016/3/5 10:55
 * 修改备注；
 */
public class MyApplications extends Application{
    private String TAG = "MyApplications";
    public static final boolean debugOn = false;//日志打印开关

    //当前登录的用户的Token值
    public static final String SETUSERTOKEN = "setusertoken";

    //当前坐标
    public static LatLng currentLatlng;
    float lat;
    float lng;

    //当前地图中心坐标
    public static LatLng currentMapCentreLatlng;

    //定位功能
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    public static MyApplications mContext;
    public static MyApplications app;
    public static RequestQueue queues;

    public static String DeviceId;
    public static String Os;
    public static String Model;
    public static String Token;
    public static SharedPreferences setInfo,mLatlng;
    public static boolean mTag = false;

    //屏幕的款高度
    public static int windowsWidth,windowsheight ;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        queues = Volley.newRequestQueue(mContext);

        mLatlng = getSharedPreferences("latlng", 0);
        lat = mLatlng.getFloat("lat",0);
        lng = mLatlng.getFloat("lng",0);
        if (lat!=0&&lng!=0){
            currentLatlng = new LatLng(lat,lng);
            currentMapCentreLatlng = new LatLng(lat,lng);
        }

        windowsheight = 0;
        windowsWidth = 0;

        DeviceId = getDeviceId();
        Os = getSystemVersions();
        Model = getModel();

        //判断当前sharedpreferences中是否有Token
        setInfo = getSharedPreferences(SETUSERTOKEN,0);
        Token = setInfo.getString("token",null);
        if (Token == null){
            mTag = false;
        }else{
            mTag = true;
        }


    }

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        // 1,得到网络的管理类
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 2,得到网络的信息
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        // 3,判断网络是否可用
        if (networkinfo == null)
            return false;
        return networkinfo.isAvailable() && networkinfo.isConnected();
    }

    /**
     * 获取Context对象
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    public static MyApplications getMyApplications(){
        app = getMyApplications();
        return app;
    }

    /**
     * 返回当前的DeviceId
     * @return DeviceId
     */
    private String getDeviceId(){
        DeviceId = HttpUtils.getDeviceId(mContext);
        return DeviceId;
    }

    /**
     *获取当前系统版本
     */
    private String getSystemVersions(){
        Os = Build.VERSION.RELEASE;
        return Os;
    }

    /**
     * 获取设备型号
     * @return 设备型号
     */
    private String getModel(){
        Model = android.os.Build.MODEL;
        return Model;
    }

    /**
     * 获取当前坐标的方法
     */
    public LatLng getCurrentCoord(){
        return currentLatlng;
    }

    /**
     * 存入当前坐标
     */
    public void putCurrentCoord(LatLng coord){
        this.currentLatlng = coord;
    }



}
