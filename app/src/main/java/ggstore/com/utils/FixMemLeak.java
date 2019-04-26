package ggstore.com.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * 解决 华为 InputMethodManager.mLastSrvView 内存泄漏问题  一般可不用理会
 */
public class FixMemLeak {

    private static Field field;
    private static boolean hasField = true;
    public   static void fixLeakCanary696(Context context) { //这个也是一种解决方案
        if (!RomUtil.isEmui()) {
            LogUtil.e("not emui");
            return;
        }
        try {
            Class clazz = Class.forName("android.gestureboost.GestureBoostManager");
            LogUtil.e("clazz " + clazz);

            Field _sGestureBoostManager = clazz.getDeclaredField("sGestureBoostManager");
            _sGestureBoostManager.setAccessible(true);
            Field _mContext = clazz.getDeclaredField("mContext");
            _mContext.setAccessible(true);

            Object sGestureBoostManager = _sGestureBoostManager.get(null);
            if (sGestureBoostManager != null) {
                _mContext.set(sGestureBoostManager, context);
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }



    public static void fixLeak(Context context) {
        if (!hasField) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mLastSrvView"};
        for (String param : arr) {
            try {
                if (field == null) {
                    field = imm.getClass().getDeclaredField(param);
                }
                if (field == null) {
                    hasField = false;
                }
                if (field != null) {
                    field.setAccessible(true);
                    field.set(imm, null);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}