package com.codans.paopaoxia.utils;

import android.util.Log;

import com.codans.paopaoxia.app.MyApplications;

/**
 * 项目名称：paopaoxia
 * 类描述：日志工具类
 * 创建人：胡继春
 * 创建时间：2016/3/5 11:32
 * 修改人：胡继春
 * 修改时间：2016/3/5 11:32
 * 修改备注；
 */
public class LogUtils {
    private static final String TAG = "Logger";

    public static void d(String sMessage) {
        if (MyApplications.debugOn) {
            d(TAG, sMessage);
        }
    }

    public static void d(String sTag, String sMessage) {
        if (MyApplications.debugOn) {
            if (null != sMessage) {
                Log.d(sTag, sMessage);
            }
        }
    }

    // Warning Info
    public static void w(String sTag, String sMessage) {
        if (MyApplications.debugOn) {
            if (null != sMessage) {
                Log.w(sTag, sMessage);
            }
        }
    }

    // Error Info
    public static void e(String sMessage) {
        if (MyApplications.debugOn) {
            if (null != sMessage) {
                e(TAG, sMessage);
            }
        }
    }

    public static void e(String sTag, String sMessage) {
        if (MyApplications.debugOn) {
            if (null != sMessage) {
                Log.e(sTag, sMessage);
            }
        }
    }
}
