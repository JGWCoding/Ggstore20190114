package ggstore.com.activity;

import android.content.Intent;
import android.view.View;

import ggstore.com.R;
import ggstore.com.base.BaseActivity;

public class PaypalActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_paypal;
    }

    @Override
    protected void initWidget() {
        findViewById(R.id.paypal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaypalActivity.this, MainActivity.class);
                intent.putExtra("paypal","success");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
