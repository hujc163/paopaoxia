package com.codans.paopaoxia.app;

import android.annotation.SuppressLint;

import com.codans.paopaoxia.utils.SdCardUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 全局变量存储类
 *
 */
public class G {

    /**
     * 应用程序名
     */
    public static final String APPNAME = "paopaoxia";

    /**
     * 文件根目录
     */
    public static final String STORAGEPATH = SdCardUtil.getNormalSDCardPath() + "/" + APPNAME + "/";

    /**
     * 自动更新文件下载路径
     */
    public static final String UPDATE_APP_SAVE_PATH = STORAGEPATH + APPNAME + ".apk";
    /**
     * 系统图片
     */
    public static final String APPIMAGE = STORAGEPATH + "img/";

    /**
     * 调用拍照request code
     */
    public static final int ACTION_CAMERA = 0x01;
    /**
     * 调用相册request code
     */
    public static final int ACTION_ALBUM = 0x02;

    /**
     * 打开录音request code
     */
    public static final int ACTION_RECORDER = 0x03;


    @SuppressLint("SimpleDateFormat")
    public static String getPhoneCurrentTime() {
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss");
        return date.format(Calendar.getInstance().getTime());
    }

}
