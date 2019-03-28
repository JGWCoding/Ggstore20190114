package ggstore.com.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import ggstore.com.App;
import ggstore.com.R;

/**
 * Glide 图片加载辅助类
 * 适配圆形图片加载情况
 */

public class ImageLoader {
    private static int count ;
    private ImageLoader() {
    }

    public static void loadImage(Context context, ImageView view, String url) {
        loadImage(Glide.with(view), view, url, 0);
    }

    public static void loadImage(Context context, ImageView view, String url, int placeholder) {
        loadImage(Glide.with(view), view, url, 0, placeholder);
    }

    public static void loadImage(Context context, ImageView view, String url, int placeholder, int error) {
        loadImage(Glide.with(view), view, url, placeholder, error);
    }

    public static void loadImage(Context context, ImageView view, String url, boolean isCircleImageView) {
        loadImage(Glide.with(view), view, url, 0, 0, isCircleImageView);
    }

    public static void loadImage(Context context, ImageView view, String url, int placeholder, int error, boolean isCircleImageView) {
        loadImage(Glide.with(view), view, url, placeholder, error, isCircleImageView);
    }

    public static void loadImage(RequestManager loader, ImageView view, String url) {
        loadImage(loader, view, url, 0);
    }

    public static void loadImage(RequestManager loader, ImageView view, String url, int placeholder) {
        loadImage(loader, view, url, placeholder, placeholder);
    }

    public static void loadImage(RequestManager loader, ImageView view, String url, int placeholder, int error) {
        loadImage(loader, view, url, placeholder, error, false);
    }

    public static void loadImage(RequestManager loader, ImageView view, String url, int placeholder, int error, boolean isCircleImageView) {
        if (TextUtils.isEmpty(url)) {
            view.setImageResource(placeholder);
        } else {
            RequestOptions options = new RequestOptions()
                    .placeholder(placeholder == 0 ? R.drawable.loading : placeholder)
                    .skipMemoryCache(false)  //用内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .error(error == 0 ? R.drawable.load_error : error);
            if (isCircleImageView) {
                options.centerCrop().circleCrop();
            }
            loader.asBitmap().load(url).apply(options).into(view);
        }
        if ((count++)/30==0){
            clearAll(App.context());
        }
    }

    public static void clear(Context context, View view) {
        Glide.with(view).clear(view);
    }

    public static void clearAll(final Context context) {
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();    //清除磁盘缓存
            }
        });
        AppOperator.runMainThread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearMemory();   //清除内存(可能可以的)缓存
            }
        });
    }
}
