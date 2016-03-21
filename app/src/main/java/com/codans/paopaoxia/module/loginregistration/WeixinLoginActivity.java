package com.codans.paopaoxia.module.loginregistration;

import android.os.Bundle;

import com.codans.paopaoxia.base.BaseActivity;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 项目名称：paopaoxia
 * 类描述：
 * 创建人：胡继春
 * 创建时间：2016/3/11 15:16
 * 修改人：胡继春
 * 修改时间：2016/3/11 15:16
 * 修改备注；
 */
public class WeixinLoginActivity extends BaseActivity{
    public static IWXAPI api;
    @Override
    public void setContentView() {
        WXTextObject textObj = new WXTextObject();
    }

    @Override
    public void getIntentData(Bundle savedInstanceState) {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {
        api = WXAPIFactory.createWXAPI(this,"wx84a51e51fb653572",true);
        api.registerApp("wx84a51e51fb653572");
    }

    @Override
    public void initData() {

    }
}
