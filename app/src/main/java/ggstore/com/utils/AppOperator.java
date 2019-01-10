package ggstore.com.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ggstore.com.BaseApplication;

/**
 * Created by JuQiu
 * on 16/6/24.
 */
public final class AppOperator {
    private static ExecutorService EXECUTORS_INSTANCE;      //线程池 --> 6个线程

    public static Executor getExecutor() {
        if (EXECUTORS_INSTANCE == null) {
            synchronized (AppOperator.class) {
                if (EXECUTORS_INSTANCE == null) {
                    EXECUTORS_INSTANCE = Executors.newFixedThreadPool(6);
                }
            }
        }
        return EXECUTORS_INSTANCE;
    }

    public static void runOnThread(Runnable runnable) {
        getExecutor().execute(runnable);
    }
    public static void runMain(Runnable runnable) {
        BaseApplication.mHandler.post(runnable);
    }
}
