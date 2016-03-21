package com.codans.paopaoxia.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * 网络相关工具类
 *
 * @author Ht
 */
public class NetUtils {



    /**
     * 对网络连接状态进行判断，
     *
     * @param context
     * @return type 网络类型
     */
    public static String getCurrentNetType(Context context) {
        String type = "";
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            type = "无网络连接";
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            type = "wifi";
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA
                    || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = "2g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS
                    || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type = "3g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                type = "4g";
            }
        }
        return type;
    }

    private void postJson() {
        //首先声明一下Url
        String urlPath = "http://192.168.1.100:8080/test";
        URL url;
        try {
            url = new URL(urlPath);

// 然后我们使用httpPost的方式把lientKey封装成Json数据的形式传递给服务器
// 在这里呢我们要封装的时这样的数据
// {"Person":{"username":"zhangsan","age":"12"}}
// 我们首先使用的是JsonObject封装 {"username":"zhangsan","age":"12"}
            JSONObject ClientKey = new JSONObject();
            ClientKey.put("Latitude", "zhangsan");
            ClientKey.put("Subject", "12");
            ClientKey.put("Longitude", "12");
            ClientKey.put("LifeHours", "12");
            ClientKey.put("TaskId", "12");
            ClientKey.put("Password", "12");
            ClientKey.put("Token", "12");
            ClientKey.put("Reward", "12");
            ClientKey.put("ThreadId", "12");
            ClientKey.put("MoveFlag", "12");
            ClientKey.put("Content", "12");
            ClientKey.put("Visibility", "12");
            ClientKey.put("IsRemote", "12");
// 接着我们使用JsonObject封装{"Person":{"username":"zhangsan","age":"12"}}
            JSONObject Authorization = new JSONObject();
            Authorization.put("Data", ClientKey);
//我们把JSON数据转换成String类型使用输出流向服务器写
            String content = String.valueOf(Authorization);
// 现在呢我们已经封装好了数据,接着呢我们要把封装好的数据传递过去
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
// 设置允许输出
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
// 设置User-Agent: Fiddler
            conn.setRequestProperty("ser-Agent", "Fiddler");
// 设置contentType
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = conn.getOutputStream();
            os.write(content.getBytes());
            os.close();
//服务器返回的响应码
            int code = conn.getResponseCode();
            if (code == 200) {
//// 等于200了,下面呢我们就可以获取服务器的数据了
//// 在这里我们已经连接上了，也获得了服务器的数据了，
//// 那么我们接着就是解析服务器传递过来的数据，
//// 现在我们开始解析服务器传递过来的参数,
////假设服务器返回的是{"Person":{"username":"zhangsan","age":"12"}}
//                InputStream is = conn.getInputStream();
////下面的json就已经是{"Person":{"username":"zhangsan","age":"12"}} //这个形式了,只不过是String类型
//                String json = NetUtils.readString(is);
////然后我们把json转换成JSONObject类型得到{"Person": //{"username":"zhangsan","age":"12"}}
//                JSONObject jsonObject = new JSONObject(json)
////然后下面这一步是得到{{"username":"zhangsan","age":"12"}
//                        .getJSONObject("username");
////下面的Person是一个bean,里面有username,和 age

//上面已经完整地做出了JSON的传递和解析
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
