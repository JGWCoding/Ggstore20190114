package ggstore.com.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import ggstore.com.App;

/**
 * 权限工具类
 * 使用:
 *
 * @Override protected void requestPermissions() {
 * //两个日历权限和一个数据读写权限
 * String[] permissions = new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_EXTERNAL_STORAGE};
 * PermissionsUtils.showSystemSetting = false;//是否支持显示系统设置权限设置窗口跳转
 * //这里的this不是上下文，是Activity对象！
 * PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
 * <p>
 * }
 * PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
 * @Override public void passPermissons() {
 * Toast.makeText(SplashActivity.this, "权限通过，可以做其他事情!", Toast.LENGTH_SHORT).show();
 * }
 * @Override public void forbitPermissons() {
 * //            finish();
 * Toast.makeText(SplashActivity.this, "权限不通过!", Toast.LENGTH_SHORT).show();
 * }
 * };
 * @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
 * super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 * //就多一个参数this
 * PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
 * }
 */

public class PermissionsUtils {


    private final int mRequestCode = 100;//权限请求码
    public static boolean showSystemSetting = true;

    private PermissionsUtils() {
    }

    private static PermissionsUtils permissionsUtils;
    private IPermissionsResult mPermissionsResult;

    public static PermissionsUtils getInstance() {
        if (permissionsUtils == null) {
            permissionsUtils = new PermissionsUtils();
        }
        return permissionsUtils;
    }

    public void chekPermissions(Activity context, String[] permissions, @NonNull IPermissionsResult permissionsResult) {
        mPermissionsResult = permissionsResult;

        if (Build.VERSION.SDK_INT < 23) {//6.0才用动态权限
            if (mPermissionsResult!=null) {
                permissionsResult.passPermissons();
            }
            return;
        }

        //创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
        List<String> mPermissionList = new ArrayList<>();
        //逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(App.context(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限
            }
        }

        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(context, permissions, mRequestCode);
        } else {
            //说明权限都已经通过，可以做你想做的事情去
            if (mPermissionsResult != null) {
                permissionsResult.passPermissons();
            }
            return;
        }


    }

    //请求权限后回调的方法
    //参数： requestCode  是我们自己定义的权限请求码
    //参数： permissions  是我们请求的权限名称数组
    //参数： grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限

    public void onRequestPermissionsResult(Activity context, int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if (hasPermissionDismiss) {
                if (showSystemSetting) {
                    showSystemPermissionsSettingDialog(context);//跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
                } else {
                    if (mPermissionsResult != null) {
                        mPermissionsResult.forbitPermissons();
                    }
                }
            } else {
                //全部权限通过，可以进行下一步操作。。。
                if (mPermissionsResult != null) {
                    mPermissionsResult.passPermissons();
                }
            }
        }

    }


    /**
     * 不再提示权限时的展示对话框
     */
    AlertDialog mPermissionDialog;

    private void showSystemPermissionsSettingDialog(final Activity context) {
        final String mPackName = context.getPackageName();
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(context)
                    .setMessage("已禁用权限，请手动授予")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();
                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            context.startActivity(intent);
                            context.finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            cancelPermissionDialog();
                            //mContext.finish();
                            if (mPermissionsResult != null) {
                                mPermissionsResult.forbitPermissons();
                            }
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    //关闭对话框
    private void cancelPermissionDialog() {
        if (mPermissionDialog != null) {
            mPermissionDialog.cancel();
            mPermissionDialog = null;
        }

    }


    public interface IPermissionsResult {
        void passPermissons();  //通过权限

        void forbitPermissons();    //禁止权限
    }


}