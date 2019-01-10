package ggstore.com;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import ggstore.com.utils.LogUtil;
import ggstore.com.utils.ToastUtils;


/**
 * Created by Administrator on 2016/12/21.
 */

public class BaseApplication extends Application {

    public static Handler mHandler;
    static Context _context;
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this,true); //初始化
        LogUtil.init(true);
        mHandler = new Handler();
        _context = this;
//        CrashHandler.getInstance().init(this);  //处理程序异常 --> 没进行研究
       Bugly.init(getApplicationContext(), "0e5dda5fec", false); //true为debug阶段

        FacebookSdk.sdkInitialize(getApplicationContext());//facebook
        AppEventsLogger.activateApp(this);
    }

    public static Context context() {
        return _context;
    }
    public static Handler getHandler() {
        return mHandler;
    }
}
