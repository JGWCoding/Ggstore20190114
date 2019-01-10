package ggstore.com.activity;

import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;

public class RegisterDetailActivity extends BaseTitleActivity {
    @Override
    protected CharSequence getContentTitle() {
        return getString(R.string.register);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_register_detail;
    }

    @Override
    protected void initData() {

    }
}
