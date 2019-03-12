package ggstore.com.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import ggstore.com.BuildConfig;
import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;
import ggstore.com.utils.RegexUtils;
import ggstore.com.utils.ToastUtil;

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
        final EditText fristName = findViewById(R.id.activity_address_frist_name);
        final   EditText lastName = findViewById(R.id.activity_address_last_name);
        final   EditText phone = findViewById(R.id.activity_address_phone);
        final   EditText area = findViewById(R.id.activity_address_area);
        findViewById(R.id.activity_address_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!BuildConfig.DEBUG) {
                    if (TextUtils.isEmpty(fristName.getText())) {
                        ToastUtil.showToast(R.string.frist_name_empty);
                        return;
                    }
                    if (TextUtils.isEmpty(lastName.getText())) {
                        ToastUtil.showToast(R.string.last_name_empty);
                        return;
                    }
                    if (TextUtils.isEmpty(phone.getText())) {
                        ToastUtil.showToast(R.string.phone_empty);
                        return;
                    }else if(!RegexUtils.isMobileSimple_8(phone.getText().toString())){
                        ToastUtil.showToast(R.string.phone_error);
                        return;
                    }
                    if (TextUtils.isEmpty(area.getText())) {
                        ToastUtil.showToast(R.string.area_empty);
                        return;
                    }
                }
                //TODO 需要进行收货资料上传
                startActivity(new Intent(ReceiveAddressActivity.this, ShopTermsActivity.class));
            }
        });
        findViewById(R.id.activity_address_back).setOnClickListener(new View.OnClickListener() {
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
