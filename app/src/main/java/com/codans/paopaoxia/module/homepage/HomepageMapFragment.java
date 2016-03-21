package com.codans.paopaoxia.module.homepage;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Telephony;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.codans.paopaoxia.app.MyApplications;
import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.module.information.Constants;
import com.codans.paopaoxia.modulebean.homepage.ThreadsBean;
import com.codans.paopaoxia.utils.UrlUtils;
import com.codans.paopaoxia.utils.cache.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：paopaoxia
 * 类描述：这里是显示地图的fragment 包含一些地图的具体设置信息
 * 创建人：胡继春
 * 创建时间：2016/3/12 11:07
 * 修改人：胡继春
 * 修改时间：2016/3/12 11:07
 * 修改备注；
 */
public class HomepageMapFragment extends Fragment implements LocationSource,
        AMapLocationListener,CompoundButton.OnCheckedChangeListener ,AMap.OnCameraChangeListener,AMap.OnMarkerClickListener,AMap.OnInfoWindowClickListener,AMap.InfoWindowAdapter {
    private String TAG = "HomepageMapFragment";

    //MyApplications实例
    private MyApplications app;


    //获取周围所有泡泡的路径
    private String ListThreads_PATH= UrlUtils.Url+UrlUtils.Url_ListThreads;

    /**
     * 基础地图
     */
    private MapView mapView;

    private AMap aMap;


    //定位功能
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private OnLocationChangedListener mListener;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    private SharedPreferences mLatlng;

    private LatLng cussertLatlng;

    private ThreadsBean mThreadsBean;
    private List<ThreadsBean.mThreads> mThreadsList;
    private List<LatLng> mListLatLng;

    private MarkerOptions mMarkerOptions;

    private Map<String,Bitmap> iconMap;                          //泡泡图标的HashMap
    private Map<String,ThreadsBean.mThreads> mThreadsMap;        //存储mThreads对象的Map集合

    //用于Gson解析的对象
    private Gson mGson;
    private ThreadsBean.mThreads bean;

    //当前坐标
    private LatLng cussertCenterLatlng;

    private LinearLayout ll_homepage_main;
    private TextView tv_infowindows,tv_infowindows_hits,tv_infowindows_membercount,tv_infowindows_postcount,tv_infowindows_likecount,tv_infowindows_content,tv_infowindows_distance,tv_infowindows_photocount;
    private ImageView iv_infowindows_close;
    private PopupWindow popupWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_homepage_map,null);

        ll_homepage_main = (LinearLayout) view.findViewById(R.id.ll_homepage_main);
        mapView = (MapView) view.findViewById(R.id.mv_main);
        mapView.onCreate(savedInstanceState);
        app = (MyApplications) getActivity().getApplication();
        mLatlng = MyApplications.mLatlng;


        mThreadsList = new ArrayList<ThreadsBean.mThreads>();           //mThreads类的数组
        mListLatLng = new ArrayList<LatLng>();                          //Marker点的坐标数组

        iconMap = new HashMap<String,Bitmap>();
        mThreadsMap = new HashMap<String,ThreadsBean.mThreads>();

        mMarkerOptions = new MarkerOptions();

        mGson = new Gson();

        cussertCenterLatlng = MyApplications.currentLatlng;

        initView();

        initMap();

        initListener();

        initLocation();

        return view;
    }

    private void initView(){
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_info_windows,null);
        popupWindow = new PopupWindow(view, 900,500);

        tv_infowindows = (TextView)view.findViewById(R.id.tv_infowindows_title);
        tv_infowindows_hits = (TextView) view.findViewById(R.id.tv_infowindows_hits);
        tv_infowindows_membercount = (TextView) view.findViewById(R.id.tv_infowindows_membercount);
        tv_infowindows_postcount = (TextView) view.findViewById(R.id.tv_infowindows_postcount);
        tv_infowindows_likecount = (TextView) view.findViewById(R.id.tv_infowindows_likecount);
        tv_infowindows_content = (TextView) view.findViewById(R.id.tv_infowindows_content);
        tv_infowindows_distance = (TextView) view.findViewById(R.id.tv_infowindows_distance);
        tv_infowindows_photocount = (TextView) view.findViewById(R.id.tv_infowindows_photocount);

        iv_infowindows_close = (ImageView) view.findViewById(R.id.iv_infowindows_close);
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
            if (MyApplications.currentLatlng!=null){
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MyApplications.currentLatlng, 19));
            }

        }
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(500));
    }

    /**
     * 设置监听
     */

    private void initListener(){

    }

    /**
     *对marker标注点击响应事件
     * @param marker
     * @return
     */
    //TODO 对marker标注点击响应事件
    @Override
    public boolean onMarkerClick(final Marker marker) {

        if (marker.getTitle()!=null){
            tv_infowindows.setText(marker.getTitle());
        }


        if (mThreadsMap.get(marker.getTitle())!= null){
            tv_infowindows_hits.setText(mThreadsMap.get(marker.getTitle()).Hits+"");
        }
        if (mThreadsMap.get(marker.getTitle())!= null){
            tv_infowindows_membercount.setText(mThreadsMap.get(marker.getTitle()).MemberCount+"");
        }
        if (mThreadsMap.get(marker.getTitle())!= null){
            tv_infowindows_postcount.setText(mThreadsMap.get(marker.getTitle()).PostCount+"");
        }
        if (mThreadsMap.get(marker.getTitle())!= null){
            tv_infowindows_likecount.setText(mThreadsMap.get(marker.getTitle()).LikeCount+"");
        }
        if (mThreadsMap.get(marker.getTitle()) != null){
            tv_infowindows_content.setText(mThreadsMap.get(marker.getTitle()).Summary+"");
        }
        if (mThreadsMap.get(marker.getTitle())!= null){
            tv_infowindows_distance.setText(mThreadsMap.get(marker.getTitle()).Distance+"");
        }
        if (mThreadsMap.get(marker.getTitle()) != null){
            tv_infowindows_photocount.setText(mThreadsMap.get(marker.getTitle()).PostCount + "");
        }

        popupWindow.showAtLocation(ll_homepage_main, Gravity.TOP|Gravity.CENTER_HORIZONTAL,0,400);


        iv_infowindows_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (marker != null) {
                    marker.hideInfoWindow();
                }
            }
        });

        return false;
    }




    /**
     * 定位功能
     */
    private void initLocation(){
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();


    }

    /**
     * 定位回调接口
     * @param amapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                cussertLatlng = new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
                mLatlng.edit().putFloat("lat", (float) amapLocation.getLatitude()).putFloat("lng", (float) amapLocation.getLongitude()).commit();
                MyApplications.currentLatlng = cussertLatlng;
                if (mThreadsList.size() == 0){
                    getAllBubble(cussertLatlng.latitude, cussertLatlng.longitude);
                }

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
            if (cussertLatlng!=null){
                cussertLatlng = null;
            }
        }

    }

    /**
     * 定位相关
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getActivity());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    /**
     * 获取周围所有的泡泡
     * @param lat
     * @param lng
     */

    public void getAllBubble(final double lat, final double lng){
        StringRequest ListThreads = new StringRequest(Request.Method.POST, ListThreads_PATH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("str",response);
                            ThreadsBean threadsBean =  mGson.fromJson(response,ThreadsBean.class);
                            mThreadsList = threadsBean.Threads;
                        for (int i = 0;i < mThreadsList.size(); i++){

                            getIcon(mThreadsList.get(i));

                        }
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
                params.put("Longitude",lng+"");
                params.put("Latitude",lat+"");
                params.put("IsRemote", "false");
                params.put("Keywords","");
                return params;
            }
        };
        if (MyApplications.queues != null) {
            MyApplications.queues.cancelAll(this);
        }
        MyApplications.queues.add(ListThreads);
    }

    //关于marker的方法
    private void setUpMap(){
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_colors53x));
//        aMap.setMyLocationStyle(myLocationStyle);
//        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
//        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
//        addMarkersToMap();// 往地图上添加marker
        aMap.setOnCameraChangeListener(this);
    }

//    private void addMarkersToMap

    //TODO 按钮的监听方法
    private void onClick(View v){
        switch (v.getId()){
            case R.id.tv_maopao_cancel:
                break;
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 获取泡泡图标的方法
     */
    private void getIcon(final ThreadsBean.mThreads mthreads){
        float mLat = mthreads.Latitude;
        float mLng = mthreads.Longitude;
        final String icon_url = "http://api.renlafun.com/UserFiles"+mthreads.IconUrl;

        final LatLng mylatlng = new LatLng(mLat,mLng);
        mListLatLng.add(mylatlng);
        mMarkerOptions.position(mylatlng);
        ImageRequest iconRequest = new ImageRequest(icon_url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                if (bitmap!=null){
                    iconMap.put(icon_url, bitmap);
                    mMarkerOptions.icon(BitmapDescriptorFactory.fromBitmap(Utils.toRoundCorner(iconMap.get(icon_url))));
                    mMarkerOptions.position(mylatlng);
                    mMarkerOptions.title(mthreads.Subject);
                    mThreadsMap.put(mthreads.Subject,mthreads);
                    aMap.addMarker(mMarkerOptions);

                }
            }
        }, 80, 80, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        if (MyApplications.queues != null) {
            MyApplications.queues.cancelAll(this);
        }
        MyApplications.queues.add(iconRequest);
    }


    //获取屏幕中心点地图位置的方法
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        MyApplications.currentMapCentreLatlng = cameraPosition.target;
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
    }

    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        Point startPoint = proj.toScreenLocation(Constants.XIAN);
        startPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * Constants.XIAN.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * Constants.XIAN.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                aMap.invalidate();// 刷新地图
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });

    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(final Marker marker, View view) {


    }

    /**
     * 监听点击infowindow窗口事件回调
     */
    //TODO 监听点击infowindow窗口事件回调
    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindows = getActivity().getLayoutInflater().inflate(R.layout.nulllayout,null);
        render(marker,infoWindows);
        return infoWindows;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

//    private void initPopup(){
//        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_info_windows,null);
//        PopupWindow popupWindow = new PopupWindow(view,MyApplications.windowsheight/2,MyApplications.windowsWidth/2);
//
//        TextView tv_infowindows = (TextView)view.findViewById(R.id.tv_infowindows_title);
//        TextView tv_infowindows_hits = (TextView) view.findViewById(R.id.tv_infowindows_hits);
//        TextView tv_infowindows_membercount = (TextView) view.findViewById(R.id.tv_infowindows_membercount);
//        TextView tv_infowindows_postcount = (TextView) view.findViewById(R.id.tv_infowindows_postcount);
//        TextView tv_infowindows_likecount = (TextView) view.findViewById(R.id.tv_infowindows_likecount);
//        TextView tv_infowindows_content = (TextView) view.findViewById(R.id.tv_infowindows_content);
//        TextView tv_infowindows_distance = (TextView) view.findViewById(R.id.tv_infowindows_distance);
//        TextView tv_infowindows_photocount = (TextView) view.findViewById(R.id.tv_infowindows_photocount);
//
//        ImageView iv_infowindows_close = (ImageView) view.findViewById(R.id.iv_infowindows_close);
//
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MyApplications.windowsheight/2,MyApplications.windowsWidth/2);
//        view.setLayoutParams(layoutParams);
//
//        if (marker.getTitle()!=null){
//            tv_infowindows.setText(marker.getTitle());
//        }
//
//        if (mThreadsMap.get(marker.getTitle())!= null){
//            tv_infowindows_hits.setText(mThreadsMap.get(marker.getTitle()).Hits);
//        }
//        if (mThreadsMap.get(marker.getTitle())!= null){
//            tv_infowindows_membercount.setText(mThreadsMap.get(marker.getTitle()).MemberCount);
//        }
//        if (mThreadsMap.get(marker.getTitle())!= null){
//            tv_infowindows_postcount.setText(mThreadsMap.get(marker.getTitle()).PostCount);
//        }
//        if (mThreadsMap.get(marker.getTitle())!= null){
//            tv_infowindows_likecount.setText(mThreadsMap.get(marker.getTitle()).LikeCount);
//        }
//        if (mThreadsMap.get(marker.getTitle()) != null){
//            tv_infowindows_content.setText(mThreadsMap.get(marker.getTitle()).Summary);
//        }
//        if (mThreadsMap.get(marker.getTitle())!= null){
//            tv_infowindows_distance.setText(mThreadsMap.get(marker.getTitle()).Distance+"");
//        }
//        if (mThreadsMap.get(marker.getTitle()) != null){
//            tv_infowindows_photocount.setText(mThreadsMap.get(marker.getTitle()).PostCount+"");
//        }
//
//        iv_infowindows_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (marker!=null) {
//                    marker.hideInfoWindow();
//                }
//            }
//        });
//    }


}