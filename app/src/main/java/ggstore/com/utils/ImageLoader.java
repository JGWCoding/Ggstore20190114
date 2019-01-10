package ggstore.com.utils;

import android.text.TextUtils;
import android.widget.ImageView;

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

    public static void loadImage(RequestManager loader, ImageView view, String url) {
        loadImage(loader, view, url, 0);
    }

    public static void loadImage(RequestManager loader, ImageView view, String url, int placeholder) {
        loadImage(loader, view, url, placeholder, placeholder);
    }

    public static void loadImage(RequestManager loader, ImageView view, String url, int placeholder, int error) {
        boolean isCenterCrop = false;
        // if (view instanceof CircleImageView)
        isCenterCrop = true;
        loadImage(loader, view, url, placeholder, error, isCenterCrop);
    }

    public static void loadImage(RequestManager loader, ImageView view, String url, int placeholder, int error, boolean isCircleImageView) {
        if (TextUtils.isEmpty(url)) {
            view.setImageResource(placeholder);
        } else {
            RequestOptions options = new RequestOptions()
                    .placeholder(placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(error);
            if (isCircleImageView) {
                options.centerCrop().circleCrop();
            }
                loader.load(url).apply(options).into(view);
        }
    }
}
