package ggstore.com.activity;

import android.content.Intent;
import android.view.View;

import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;

public class ReceiveAddressActivity extends BaseTitleActivity {
    @Override
    protected CharSequence getContentTitle() {
        return getString(R.string.cargo_data);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ReceiveAddressActivity.this,ShopTermsActivity.class));
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
