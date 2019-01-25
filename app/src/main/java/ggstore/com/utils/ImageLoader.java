package ggstore.com.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


/**
 * Glide 图片加载辅助类
 * 适配圆形图片加载情况
 */

public class ImageLoader {
    private ImageLoader() {
    }

    public static void loadImage(Context context, ImageView view, String url) {
        loadImage(Glide.with(context), view, url, 0);
    }

    public static void loadImage(Context context, ImageView view, String url, int placeholder) {
        loadImage(Glide.with(context), view, url, 0, placeholder);
    }

    public static void loadImage(Context context, ImageView view, String url, int placeholder, int error) {
        loadImage(Glide.with(context), view, url, placeholder, error);
    }
    public static void loadImage(Context context, ImageView view, String url, boolean isCircleImageView) {
        loadImage(Glide.with(context), view, url, 0, 0,isCircleImageView);
    }
    public static void loadImage(Context context, ImageView view, String url, int placeholder, int error, boolean isCircleImageView) {
        loadImage(Glide.with(context), view, url, placeholder, error,isCircleImageView);
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
                    .placeholder(placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .error(error);
            if (isCircleImageView) {
                options.centerCrop().circleCrop();
            }
            loader.load(url).apply(options).into(view);
        }
    }
}
