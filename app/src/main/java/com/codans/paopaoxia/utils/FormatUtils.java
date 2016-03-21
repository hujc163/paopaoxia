package com.codans.paopaoxia.utils;

import java.text.SimpleDateFormat;

/**
 * 项目名称：paopaoxia
 * 类描述：格式化工具类
 * 创建人：胡继春
 * 创建时间：2016/3/9 9:56
 * 修改人：胡继春
 * 修改时间：2016/3/9 9:56
 * 修改备注；
 */
public class FormatUtils {
    private static final String TAG = "FormatUtils";

    //格式化时间
    public static final class TimeFormat{
        public static final long SECONE = 1000;
        public static final long MINUTE = 60 *1000;
        public static final long HOUR = 60 * MINUTE;
        public static final long DAY = 24 * HOUR;

        public static  String absolute(Long time){
            SimpleDateFormat df = new SimpleDateFormat("MM-dd");
            return  df.format(time);
        }
    }
}
