package com.codans.paopaoxia.utils.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.codans.paopaoxia.app.MyApplications;

/**
 * 项目名称：paopaoxia
 * 类描述：
 * 创建人：胡继春
 * 创建时间：2016/3/8 15:41
 * 修改人：胡继春
 * 修改时间：2016/3/8 15:41
 * 修改备注；
 */
public class ImageCacheManger {

    //取运行内存的1/8作为图片缓存
    private static final int MEM_CACHE_SIZE = 1024*1024*((ActivityManager)MyApplications.getContext().getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass()/8;
    private static ImageLruCache mImageLruCache = new ImageLruCache(MEM_CACHE_SIZE,"images",10*1024*1024);
    public static ImageLoader mImageLoder = new ImageLoader(RequesQueuetManager.mRequestQueue,mImageLruCache);

    public static ImageLoader.ImageListener getImageLinseter(final ImageView view,final Bitmap defaultImageBitmap,final Bitmap errorImageBitmap){
        return new ImageLoader.ImageListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (errorImageBitmap !=null){
                    view.setImageBitmap(errorImageBitmap);
                }
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer.getBitmap() != null){
                    view.setImageBitmap(imageContainer.getBitmap());
                }else if (defaultImageBitmap != null){
                    view.setImageBitmap(defaultImageBitmap);
                }
            }
        };
    }

    //只有控件的方法
    public static ImageLoader.ImageListener getImageLinseter(final ImageView view){
        return new ImageLoader.ImageListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer.getBitmap() != null){
                    view.setImageBitmap(Utils.toRoundCorner(imageContainer.getBitmap()));
                }
            }
        };
    }

    //只是为了加入缓存的方法
    public static ImageLoader.ImageListener getImageLinseter(){
        return new ImageLoader.ImageListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                if (imageContainer.getBitmap() != null){
                    imageContainer.getBitmap();
                }
            }
        };
    }

//    public static ImageLoader.ImageContainer loadImage(String requestUrl,ImageLoader.ImageListener imageListener){
//        return  loadImage(requestUrl,imageListener,0,0);
//    }
//
//    public static ImageLoader.ImageContainer loadImage(String url,ImageLoader.ImageListener listener.int maxWidth,int maxHight){
//        return mImageLoder.get(url, listener, maxWidth, maxHeight);
//    }
    public static ImageLoader.ImageContainer loadImage(String requestUrl, ImageLoader.ImageListener imageListener) {
    return loadImage(requestUrl, imageListener, 0, 0);
}

    public static ImageLoader.ImageContainer loadImage(String url,ImageLoader.ImageListener listener, int maxWidth, int maxHeight){
        return mImageLoder.get(url, listener, maxWidth, maxHeight);
    }

    /**
     * 外部调用此方法即可完成将url处图片现在view上，并自动实现内存和硬盘双缓存。
     * @param url 远程url地址
     * @param view 待现实图片的view
     * @param defaultImageBitmap 默认显示的图片
     * @param errorImageBitmap 网络出错时显示的图片
     */
    public static ImageLoader.ImageContainer loadImage(final String url, final ImageView view,
                                                       final Bitmap defaultImageBitmap, final Bitmap errorImageBitmap){
        return loadImage(url, getImageLinseter(view, defaultImageBitmap,
                errorImageBitmap));
    }

    /**
     * 外部调用此方法即可完成将url处图片现在view上，并自动实现内存和硬盘双缓存。
     * @param url 远程url地址
     * @param view 待现实图片的view
     * @param defaultImageBitmap 默认显示的图片
     * @param errorImageBitmap 网络出错时显示的图片
     * @param maxWidtn
     * @param maxHeight
     */
    public static ImageLoader.ImageContainer loadImage(final String url, final ImageView view,
                                                       final Bitmap defaultImageBitmap, final Bitmap errorImageBitmap, int maxWidtn, int maxHeight){
        return loadImage(url, getImageLinseter(view,defaultImageBitmap,
                errorImageBitmap),maxWidtn,maxHeight);
    }

    /**
     * 外部调用此接口，实现内存和硬盘双缓存
     * @param url
     * @param maxWidtn
     * @param maxHeight
     * @return
     */
    public static ImageLoader.ImageContainer loadImage(final String url,final ImageView view,int maxWidtn, int maxHeight){
        return loadImage(url,getImageLinseter(view),maxWidtn,maxHeight);
    }

    /**
     * 外部调用此接口，实现图片缓存
     * @param url
     * @param maxWidtn
     * @param maxHeight
     * @return
     */
    public static ImageLoader.ImageContainer loadImage(final String url,int maxWidtn, int maxHeight){
        return loadImage(url,getImageLinseter(),maxWidtn,maxHeight);
    }
}
