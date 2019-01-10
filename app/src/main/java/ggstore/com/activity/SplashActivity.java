package ggstore.com.activity;

import android.content.Intent;

import ggstore.com.R;
import ggstore.com.base.BaseActivity;
import ggstore.com.utils.AppOperator;

public class SplashActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initWidget() {

    }


    @Override
    protected void initData() {

        AppOperator.runOnThread(new Runnable() { //必须放在子线程执行
            @Override
            public void run() {
                try {
                    Thread.sleep(200);//睡眠一毫秒
                } catch (InterruptedException e) {

                }
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
