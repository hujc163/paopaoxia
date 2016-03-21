package com.codans.paopaoxia.bean;

/**
 * 项目名称：paopaoxia
 * 类描述：
 * 创建人：胡继春
 * 创建时间：2016/3/10 15:16
 * 修改人：胡继春
 * 修改时间：2016/3/10 15:16
 * 修改备注；
 */
public class AuthCodeParameBean {

    public String DeviceId;
    public String Mobile;
    int Mode;

    private AuthCodeParameBean() {
    }

    private AuthCodeParameBean(String Mobile,int Mode) {
        DeviceId = "274026fc-ef78-4cf1-bc09-00e23dd5202e";
        this.Mobile = Mobile;
        this.Mode = Mode;
    }

}
