package com.codans.paopaoxia.module.homepage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.codans.paopaoxia.activity.AboutActivity;
import com.codans.paopaoxia.activity.MainActivity;
import com.codans.paopaoxia.app.MyApplications;
import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.module.assignment.TaskActivity;
import com.codans.paopaoxia.module.bubbling.BubblingActivity;
import com.codans.paopaoxia.module.bubbling.SendBubbleActivity;
import com.codans.paopaoxia.module.particulars.BubbleParticularsActivity;
import com.codans.paopaoxia.module.personalcenter.MyAttentionActivity;
import com.codans.paopaoxia.module.personalcenter.MyBubbleActivity;
import com.codans.paopaoxia.module.personalcenter.MyFootPrintActivity;
import com.codans.paopaoxia.module.personalcenter.MyVerietyShop;
import com.codans.paopaoxia.module.personalcenter.ParticipationActivity;
import com.codans.paopaoxia.module.personalcenter.PersonalCenterActivity;
import com.codans.paopaoxia.module.systemsettings.SystemSetingActivity;
import com.codans.paopaoxia.modulebean.homepage.PersonalInfoBean;
import com.codans.paopaoxia.utils.UrlUtils;
import com.codans.paopaoxia.utils.cache.ImageCacheManger;
import com.codans.paopaoxia.utils.cache.Utils;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：paopaoxia
 * 类描述：泡泡的首页面，地图的显示什么的
 * 创建人：胡继春
 * 创建时间：2016/3/9 13:19
 * 修改人：胡继春
 * 修改时间：2016/3/9 13:19
 * 修改备注；
 */
public class HomepageActivity extends Activity {
    private String TAG = "HomepageActivity";
    //获取个人信息的网址
    private String MEMBERLOAD_PATH = UrlUtils.Url + UrlUtils.Url_MemberLoad;

    public MyApplications app;

    HomepageMapFragment homepageMapFragment;

    //事物管理类对象
    private FragmentManager fm;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    private SharedPreferences mSp;                     //读取Token的ShapredPreferences的对象

    private LinearLayout ll_maopao;
    private SlidingMenu menu;
    private AlertDialog.Builder builder;                //用于创建自定义弹出框的builder
    private AlertDialog mDialog;                        //弹出的显示框

    public int width, height;                            //屏幕的宽高

    View dialogView = null;                              //dialog的布局文件

    View MenuView;

    private ImageView iv_homepage_user, iv_homepage_search, iv_user_icon;
    private TextView tv_user_name, tv_maopao_cancel, tv_maopao_go;
    private Button btn_dialog_ok, btn_dialog_no;
    private TextView tv_dialog_title, tv_dialog_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getMetrics();

        app = (MyApplications) getApplication();
        mSp = MyApplications.setInfo;

        ll_maopao = (LinearLayout) findViewById(R.id.ll_maopao);
        //这里设置发布泡泡的图片宽高
        if (MyApplications.windowsheight != 0 && MyApplications.windowsWidth != 0) {
            int wid = MyApplications.windowsWidth / 2;
            int hei = MyApplications.windowsheight / 7;
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(wid, hei);
            layoutParams.setMargins(MyApplications.windowsWidth / 4, MyApplications.windowsheight / 2 - hei, MyApplications.windowsWidth / 4, MyApplications.windowsheight / 2);
            ll_maopao.setLayoutParams(layoutParams);
        }


        iv_homepage_user = (ImageView) findViewById(R.id.iv_homepage_user);
        iv_homepage_search = (ImageView) findViewById(R.id.iv_homepage_search);

        //自定义dialog中控件的初始化
        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_normal_layout, null);
        builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        mDialog = builder.create();

        tv_dialog_title = (TextView) dialogView.findViewById(R.id.tv_dialog_title);
        tv_dialog_text = (TextView) dialogView.findViewById(R.id.tv_dialog_text);
        btn_dialog_ok = (Button) dialogView.findViewById(R.id.btn_dialog_ok);
        btn_dialog_no = (Button) dialogView.findViewById(R.id.btn_dialog_no);

        tv_maopao_cancel = (TextView) findViewById(R.id.tv_maopao_cancel);
        tv_maopao_go = (TextView) findViewById(R.id.tv_maopao_go);

        initSlidingMenu();
        initLitener();
        initData();
        getUserInfo();

        /**
         * 开启fragment事务并提交更改fragment
         */
//        fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.fm_homepage,new HomepageMapFrame());
//        ft.commit();


    }

    //初始化侧滑菜单
    //TODO 侧滑菜单
    private void initSlidingMenu() {

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        int menuWidth = (int) (width * 0.8);

        MenuView = LayoutInflater.from(this).inflate(R.layout.item_sliding_user, null);

        //侧滑栏属性
        menu = new SlidingMenu(getBaseContext());
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setBehindWidth(menuWidth);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(MenuView);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);


        iv_user_icon = (ImageView) MenuView.findViewById(R.id.iv_user_icon);
        tv_user_name = (TextView) MenuView.findViewById(R.id.tv_user_name);

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_100);
        iv_user_icon.setImageBitmap(Utils.toRoundCorner(bmp));

//        homepageMapFragment = new HomepageMapFragment();
//        homepageMapFragment.setOnMapCenterListener(new HomepageMapFragment.OnMapCenterLatlngListener() {
//            @Override
//            public void OnMapCenterLatlng(LatLng l) {
//                if (l != null) {
//                    currentLatlng = l;
//                }
//            }
//        });
    }

    /**
     * 初始化监听的方法
     */
    private void initLitener() {
        //用户的按钮监听
        iv_homepage_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menu.isMenuShowing()) {
                    menu.showMenu(false);
                } else {
                    menu.showMenu(true);
                }
            }
        });

        //搜索按钮的监听
        iv_homepage_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.fm_homepage, null);
//                ft.commit();
            }
        });

        //弹出框按钮的监听
        btn_dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSp.edit().putString("token", null).commit();
                MyApplications.mTag = false;
                startActivity(new Intent(HomepageActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                mDialog.dismiss();
            }
        });

        //关闭弹出框
        btn_dialog_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        //发布泡泡的按钮
        tv_maopao_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomepageActivity.this, SendBubbleActivity.class));

            }
        });

        //取消显示发布泡泡按钮
        tv_maopao_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_maopao.setVisibility(View.GONE);
            }
        });
    }

    private void initData() {
        tv_dialog_title.setText("提示");
        tv_dialog_text.setText("您确定要退出当前的帐号吗？");
    }

    /**
     * 用户信息的获取
     */
    private void getUserInfo() {
        if (MyApplications.currentLatlng != null) {

            StringRequest userInfoRequest = new StringRequest(Request.Method.POST, MEMBERLOAD_PATH,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("Stag", response);
                            PersonalInfoBean personalInfoBean = new Gson().fromJson(response, PersonalInfoBean.class);
                            tv_user_name.setText(personalInfoBean.MemberInfo.Mobile);
                            if ("".equals(personalInfoBean.MemberInfo.Name) && tv_user_name != null) {
                                tv_user_name.setText(personalInfoBean.MemberInfo.Mobile);
                            } else {
                                tv_user_name.setText(personalInfoBean.MemberInfo.Name);
                            }
                            if ("".equals(personalInfoBean.MemberInfo.HeadImage)) {
                                String icon_url = "http://api.renlafun.com/UserFiles" + personalInfoBean.MemberInfo.WeixinHeadImage;
                                ImageCacheManger.loadImage(icon_url, iv_user_icon, 0, 0);
                            } else if ("".equals(personalInfoBean.MemberInfo.WeixinHeadImage)) {

                            } else {
                                String icon_url = "http://api.renlafun.com/UserFiles" + personalInfoBean.MemberInfo.HeadImage;
                                ImageCacheManger.loadImage(icon_url, iv_user_icon, 0, 0);
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

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Token", MyApplications.Token);
                    params.put("MemberId", "00000000-0000-0000-0000-000000000000");
                    params.put("Latitude", MyApplications.currentLatlng.latitude + "");
                    params.put("Longitude", MyApplications.currentLatlng.longitude + "");
                    return params;
                }
            };
            if (MyApplications.queues != null) {
                MyApplications.queues.cancelAll(this);
            }
            MyApplications.queues.add(userInfoRequest);

        }
    }

    public void onClick(View V) {
        Intent intent = new Intent();
        switch (V.getId()) {
            //个人中心按钮
            case R.id.btn_user_personcenter:
                startActivity(new Intent(this, PersonalCenterActivity.class));
                break;
            //我的泡泡按钮
            case R.id.btn_user_mybubble:
                startActivity(new Intent(this, MyBubbleActivity.class));
                break;
            //我的杂货按钮
            case R.id.btn_user_myzahuo:
                startActivity(new Intent(this, MyVerietyShop.class));
                break;
            //我的任务按钮
            case R.id.btn_user_mytask:
                startActivity(new Intent(this, TaskActivity.class));
                break;
            //我的关注按钮
            case R.id.btn_user_myattention:
                startActivity(new Intent(this, MyAttentionActivity.class));
                break;
            //我的足迹按钮
            case R.id.btn_user_myfootprint:
                startActivity(new Intent(this, MyFootPrintActivity.class));
                break;
            //参与的泡泡按钮
            case R.id.btn_user_myjoinbubble:
                startActivity(new Intent(this, ParticipationActivity.class));
                break;
            //设置按钮
            case R.id.btn_user_settings:
                startActivity(new Intent(this, SystemSetingActivity.class));
                break;
            //关于按钮
            case R.id.btn_user_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            //注销按钮
            case R.id.btn_user_out:
                mDialog.show();
                break;
            //冒泡按钮
            case R.id.iv_homepage_maopao:
                ll_maopao.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_maopao_cancel:
                Toast.makeText(this, "dd", Toast.LENGTH_SHORT).show();
                ll_maopao.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    //重写后退按钮的触发事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (menu.isMenuShowing()) {
            menu.showContent(false);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取屏幕尺寸
     */
    private void getMetrics() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）

        MyApplications.windowsWidth = metric.widthPixels;
        MyApplications.windowsheight = metric.heightPixels;
    }

}
