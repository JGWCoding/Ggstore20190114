package ggstore.com.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import ggstore.com.R;

public class DialogUtil {
    private static ProgressDialog progressDialog;
    private static AlertDialog alertDialog;
    private static Dialog dialog;

    public static void alertDialog(Context context, String title, String message) {
        alertDialog(context,null,null,title,message,true,null);
    }
    public static void alertDialog(Context context, String title, String message,boolean isCancel, final Runnable taskPositive) {
        alertDialog(context,"确认","取消",title,message,isCancel,taskPositive);
    }
    public static void dismiss(){
        if (progressDialog !=null&& progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        if (alertDialog!=null&&alertDialog.isShowing()){
            alertDialog.dismiss();
        }
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }
    public static void alertDialog(Context context, String positiveText,String negativeText,String title, String message,boolean isCancel, final Runnable taskPositive) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);
        if (!TextUtils.isEmpty(title))
            builder.setMessage(message);
        builder.setCancelable(isCancel);
        if (!TextUtils.isEmpty(positiveText)) {
            builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (taskPositive != null) {
                        taskPositive.run();
                    }
                    dialog.dismiss();
                }
            });
        }
        if (!TextUtils.isEmpty(negativeText)) {
            builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
        }
        alertDialog = builder.create();
        alertDialog.show();
    }

    public static void loading(Context context,boolean isCancel) {

        ProgressBar progressBar = new ProgressBar(context);
        dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.transparent_bg);
        Window window = dialog.getWindow();
        window.setAttributes( window.getAttributes());
        dialog.setContentView(progressBar);
        dialog.show();
    }

    public static void customView(Context context,View view,Runnable runTask) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        if (runTask!=null){
            runTask.run();
        }
        alertDialog = builder.create();
        alertDialog.show();
    }

    public static void customView(Context context, @LayoutRes int view,Runnable runTask) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        if (runTask!=null){
            runTask.run();
        }
        alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 圆形进度转圈
     *
     * @param context
     * @param title          标题 这个可为null
     * @param message        内容
     * @param cancelable     点击外部是否消失 一般为true可以点击消失
     * @param cancelListener dialog取消监听器
     */
    public static void showLoadingDialog(Context context, String title, String message, Boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        progressDialog = ProgressDialog.show(context, title, message, false,
                cancelable, cancelListener);
    }

    public static void showLoadingDialog(Context context, String title, String message, Boolean cancelable) {
        progressDialog = ProgressDialog.show(context, title, message, false,
                cancelable);
    }

    /**
     * 水平进度条
     *
     * @param context
     * @param title
     * @param message
     * @param cancelable
     */
    public static void showHorizhontalLoadingDialog(Context context, String title, String message, Boolean cancelable) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        progressDialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        progressDialog.setIcon(null);// 设置提示的title的图标，默认是没有的
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setMax(100);
        progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        progressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "中立",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        progressDialog.show();
        AppOperator.runOnThread(new Runnable() {

            @Override
            public void run() {
                int i = 0;
                while (i < 100) {
                    try {
                        Thread.sleep(200);
                        // 更新进度条的进度,可以在子线程中更新进度条进度
                        progressDialog.incrementProgressBy(1);
                        // progressDialog.incrementSecondaryProgressBy(10)//二级进度条更新方式
                        i++;
                    } catch (Exception e) {
                    }
                }
                // 在进度条走完时删除Dialog
                progressDialog.dismiss();
            }
        });
    }

}