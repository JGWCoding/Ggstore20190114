package ggstore.com;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.Bugly;

import ggstore.com.bean.DaoMaster;
import ggstore.com.bean.DaoSession;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.ToastUtil;
import ggstore.com.utils.Utils;


/**
 * Created by Administrator on 2016/12/21.
 */

public class BaseApplication extends Application {

    public static Handler mHandler;
    static Context _context;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(this,true); //初始化

        LogUtil.init(BuildConfig.DEBUG);
        mHandler = new Handler();
        _context = this;
//        CrashHandler.getInstance().init(this);  //处理程序异常 --> 没进行研究
       Bugly.init(getApplicationContext(), "0e5dda5fec", false); //true为debug阶段

        FacebookSdk.sdkInitialize(getApplicationContext());//facebook
        AppEventsLogger.activateApp(this);
        Utils.init(this);   //工具类初始化

        setupDatabase();    //greenDao数据库设置

        if(BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
            // Normal app init code...
        }
        Utils.init(this);
    }
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shopCart.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
    public static Context context() {
        return _context;
    }
    public static Handler getHandler() {
        return mHandler;
    }
}
