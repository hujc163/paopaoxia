package com.codans.paopaoxia.utils.cache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.codans.paopaoxia.app.MyApplications;


/**
 * 项目名称：paopaoxia
 * 类描述：实现图片的三级缓存所必需的RequesQueueManger管理类，用来维持请求队列
 * 创建人：胡继春
 * 创建时间：2016/3/8 14:06
 * 修改人：胡继春
 * 修改时间：2016/3/8 14:06
 * 修改备注；
 */
public class RequesQueuetManager {
    public static RequestQueue mRequestQueue = Volley.newRequestQueue(MyApplications.getContext());

    public static void addRequest(Request<?> request,Object object){
        if (object != null){
            request.setTag(object);
        }
        mRequestQueue.add(request);
    }

    /**
     * 取消所有指定标记的请求
     * @param tag
     */
    public static void cancelAll(Object tag){
        mRequestQueue.cancelAll(tag);
    }
}
