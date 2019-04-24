package ggstore.com.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import ggstore.com.R;

/**
 * Glide 图片加载辅助类
 * 适配圆形图片加载情况
 */

public class ImageLoader {
    private static int count;

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

    public static void loadImage(final RequestManager loader, final ImageView view, final String url, int placeholder, final int error, boolean isCircleImageView) {
        if (TextUtils.isEmpty(url)) {
            view.setImageResource(error == 0 ? R.drawable.load_error : error);
        } else {
            final RequestOptions options = new RequestOptions()
//                    .placeholder(placeholder == 0 ? R.drawable.img_loading : placeholder)
                    .skipMemoryCache(false)  //用内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存所有图片(原图,转换图)
                    .fitCenter()   //fitCenter 缩放图片充满ImageView CenterInside大缩小原(图) CenterCrop大裁小扩充满ImageView  Center大裁(中间)小原
                    .error(error == 0 ? R.drawable.load_error : error);
            if (isCircleImageView) {
                options.centerCrop().circleCrop();
            }
//            loader.asGif().load(url).apply(options).into(view);
//            Glide.with(view).asGif().load(placeholder == 0 ? R.drawable.img_loading : placeholder).apply(new RequestOptions().placeholder(placeholder == 0 ? R.drawable.img_loading : placeholder)).into(view);
            loader.load(url).apply(options).thumbnail(Glide.with(view).load(placeholder == 0 ? R.drawable.img_loading : placeholder)).into(view);  //
//            loader.asBitmap().load(url).apply(options).into(view);  //
//            loader.asGif().load(url).apply(options).into(view);
        }
//        if ((count++)%100==0){
//            clearAll(App.context());
//        }
    }

    public static void clear(View view) {
        LogUtil.i(view + " :view is recycler");
        Glide.with(view).clear(view);   //会判断是否是自己缓存的view,不是就不作处理
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
