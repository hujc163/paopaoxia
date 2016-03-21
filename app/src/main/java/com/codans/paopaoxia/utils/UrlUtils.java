package com.codans.paopaoxia.utils;

/**
 * 项目名称：paopaoxia
 * 类描述：Url路径类
 * 创建人：胡继春
 * 创建时间：2016/3/9 11:19
 * 修改人：胡继春
 * 修改时间：2016/3/9 11:19
 * 修改备注；
 */
public class UrlUtils {
    //基地址
    public final static String Url = "http://api.renlafun.com/api/";

    //发送短信验证码
    public final static String Url_SendSmsVerify ="Member/SendSmsVerify";

    //验证短信验证码
    public final static String Url_VerifySms="Member/VerifySms";

    //首次打开app上传设备信息
    public final static String Url_Report = "Device/Report";

    //首次打开app上传设备信息
    public final static String Url_ListThreads = "Member/ListThreads";

    //修改昵称(上传用户信息)
    public final static String Url_AppendInfo = "Member/AppendInfo";

    //微信登录
    public final static String Url_ThreadReport = "Thread/Report";

    //加载个人信息
    public final static String Url_MemberLoad = "Member/Load";

    //创建泡泡
    public final static String Url_CreatThread = "Member/CreatThread";
}
