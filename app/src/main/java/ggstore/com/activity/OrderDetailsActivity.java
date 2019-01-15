package ggstore.com.activity;

import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;

public class OrderDetailsActivity extends BaseTitleActivity {
    @Override
    protected CharSequence getContentTitle() {
        return getString(R.string.order_details);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initData() {

    }
}
