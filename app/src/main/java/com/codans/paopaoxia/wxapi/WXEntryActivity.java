package com.codans.paopaoxia.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codans.paopaoxia.app.MyApplications;
import com.codans.paopaoxia.module.homepage.HomepageActivity;
import com.codans.paopaoxia.utils.UrlUtils;
import com.google.gson.Gson;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：paopaoxia
 * 类描述：微信登录工具类
 * 创建人：胡继春
 * 创建时间：2016/3/12 18:28
 * 修改人：胡继春
 * 修改时间：2016/3/12 18:28
 * 修改备注；
 */
public class WXEntryActivity extends Activity{
    private String TAG = "WXEntryActivity";
    private String CODE;
    private String APPID = "wx84a51e51fb653572";
    private String APPSECRET = "518fdfb80ecda8804f58a0f6d519c223";
    private String OpenId;
    private String UnionId;

    private TokenBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        SendAuth.Resp resp = new SendAuth.Resp(intent.getExtras());
        if (resp.errCode == BaseResp.ErrCode.ERR_OK){
            CODE = resp.code;
            getOpenId(CODE);
            if (bean != null){
                OpenId = bean.openid;
                UnionId = bean.unionid;
                if (OpenId != null&UnionId!=null){
                    WeixinLogin(OpenId, UnionId);
                }
            }
        }
    }

    /**
     * 获取微信登录的返回信息，其中包含openid和unionid
     * @param code
     * @return
     */
    private TokenBean getOpenId(String code){

        String urlStr = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+APPID+"&secret="+APPSECRET+"&code="+code+"&grant_type=authorization_code";
        StringRequest gettoken = new StringRequest(Request.Method.GET, urlStr, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
               bean = new Gson().fromJson(s,TokenBean.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        return null;
    }

    //微信登录方法
    private void WeixinLogin(final String mOpenId, final String mUnionId){
        String Url_ThreadReport = UrlUtils.Url+ UrlUtils.Url_ThreadReport;
        StringRequest weixinInfo = new StringRequest(Request.Method.POST, Url_ThreadReport,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        startActivity(new Intent(WXEntryActivity.this, HomepageActivity.class));
                        Log.i(TAG,response+"");
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
                map.put("OpenId", mOpenId);
                map.put("UnionId", mUnionId);
                return map;
            }
        };
        if (MyApplications.queues != null) {
            MyApplications.queues.cancelAll(this);
        }
        MyApplications.queues.add(weixinInfo);
    }

}
