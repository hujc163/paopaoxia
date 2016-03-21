package com.codans.paopaoxia.module.loginregistration;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codans.paopaoxia.app.MyApplications;
import com.codans.paopaoxia.app.R;
import com.codans.paopaoxia.base.BaseActivity;
import com.codans.paopaoxia.module.homepage.HomepageActivity;
import com.codans.paopaoxia.modulebean.loginregistration.VerifysmsBean;
import com.codans.paopaoxia.utils.UrlUtils;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：paopaoxia
 * 类描述：注册界面
 * 创建人：胡继春
 * 创建时间：2016/3/9 16:51
 * 修改人：胡继春
 * 修改时间：2016/3/9 16:51
 * 修改备注；
 */
public class RegisterActivity extends BaseActivity{
    private String TAG = "LoginActivity";

    private EditText et_register_mobile, et_register_authcode;

    private String SendSmsVerify_PATH = UrlUtils.Url + UrlUtils.Url_SendSmsVerify;
    private String Verify_PATH = UrlUtils.Url + UrlUtils.Url_VerifySms;

    private String Mobile;
    private String DeviceId;
    private String AuthCode;
    private int Mode = 0;

    private Handler handler1 = null;

    private RadioButton rb_register_sms, rb_register_phone;
    private Button btn_register_getAuthCode;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void getIntentData(Bundle savedInstanceState) {

    }

    @Override
    public void initViews() {
        et_register_mobile = (EditText) findViewById(R.id.et_register_mobile);
        et_register_authcode = (EditText) findViewById(R.id.et_register_authcode);

        rb_register_phone = (RadioButton) findViewById(R.id.rbtn_register_phone);
        rb_register_sms = (RadioButton) findViewById(R.id.rbtn_register_sms);

        btn_register_getAuthCode = (Button) findViewById(R.id.btn_register_getauthcode);
    }

    @Override
    public void initListeners() {
        rb_register_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mode = 0;
            }
        });

        rb_register_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mode = 1;
            }
        });

        handler1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int i = (int) msg.obj;
                btn_register_getAuthCode.setText("重新获取验证码(" + i + "s" +")");
                btn_register_getAuthCode.setTextColor(Color.rgb(128, 128, 128));
                if (i == 1){
                    btn_register_getAuthCode.setClickable(true);
                    btn_register_getAuthCode.setText("获取验证码");
                    btn_register_getAuthCode.setTextColor(Color.rgb(15, 118, 168));
                }
            }
        };

    }

    @Override
    public void initData() {


    }


    public void onClick(View V) {
        Mobile = et_register_mobile.getText().toString();
        AuthCode = et_register_authcode.getText().toString();
        switch (V.getId()) {
            case R.id.btn_login_cancel:
                finish();
                break;

            case R.id.btn_register_next1:

                clickNext();
                break;

            case R.id.btn_register_next2:
                clickNext();
                break;

            case R.id.btn_register_getauthcode:
               clickGetAuth();
                break;
            default:
                break;
        }

    }

    /**
     * 弹出提示框的方法
     */
    //TODO 弹出提示框的方法
    private void PopupBox(String Title, String Message, String Ok) {
        AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this).setTitle(Title)
                .setMessage(Message)
                .setPositiveButton(Ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void PopupBox(String Message) {
        AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this).setTitle("提示")
                .setMessage(Message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    //验证号码后点击下一步进行的操作
    private void clickNext(){
        if (et_register_mobile.length() == 0) {
            PopupBox("手机号码不能为空");
        } else if (et_register_mobile.length() != 11) {
            PopupBox("请输入正确的手机号码");
        } else if (et_register_authcode.length() == 0) {
            PopupBox("验证码不能为空");
        } else {
            StringRequest verifysms_register = new StringRequest(Request.Method.POST, Verify_PATH,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            VerifysmsBean bean = new Gson().fromJson(response, VerifysmsBean.class);
                            if ("false".equals(bean.Success)) {
                                PopupBox("验证码不正确");
                            } else {
                                //获取SharedPreferences对象，将Token值存入文件
                                if (bean.Token!=null){
                                    SharedPreferences settings = MyApplications.setInfo;
                                    settings.edit()
                                            .putString("token",bean.Token)
                                            .commit();
                                }
                                if (bean.Name!=null){
                                    startActivity(new Intent(RegisterActivity.this, HomepageActivity.class));
                                }else{
                                    Intent intent = new Intent();
                                    intent.setClass(RegisterActivity.this, SetUserInfoActivity.class);
                                    startActivity(intent);
                                }

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
                    params.put("DeviceId", MyApplications.DeviceId);
                    params.put("Mobile", Mobile);
                    params.put("Code", AuthCode);
                    params.put("InviteCode", "234");
                    return params;
                }
            };
            if (MyApplications.queues != null) {
                MyApplications.queues.cancelAll(this);
            }
            MyApplications.queues.add(verifysms_register);
        }
    }

    //点击获取验证码后进行的操作
    private void clickGetAuth(){
        if (et_register_mobile.length() == 0) {
            PopupBox("手机号码不能为空");
        } else if (et_register_mobile.length() != 11) {
            PopupBox("请输入正确的手机号码");
        } else {
            btn_register_getAuthCode.setClickable(false);

            new Thread(new Runnable() {
                @Override
                public void run() {

                    int i = 60;
                    while (i > 0) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message mess = new Message();
                        mess.obj = i;
                        handler1.sendMessage(mess);
                        i--;
                    }

                }
            }).start();
            //TODO 弹出Toast
            StringRequest sendsms_register = new StringRequest(Request.Method.POST, SendSmsVerify_PATH,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, error.getMessage(), error);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("DeviceId", MyApplications.DeviceId);
                    params.put("Mobile", Mobile);
                    params.put("Mode", Mode+"");
                    return params;
                }
            };
            if (MyApplications.queues != null) {
                MyApplications.queues.cancelAll(this);
            }
            MyApplications.queues.add(sendsms_register);
        }
    }

}
