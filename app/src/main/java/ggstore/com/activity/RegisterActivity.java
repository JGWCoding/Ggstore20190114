package ggstore.com.activity;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;

public class RegisterActivity extends BaseTitleActivity {

    @Override
    protected CharSequence getContentTitle() {
        return getString(R.string.register);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //todo I need update this font style
        ((TextView)findViewById(R.id.register_details)).setText(Html.fromHtml(getString(R.string.register_details)));
        findViewById(R.id.disagree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.agree).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, RegisterDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
