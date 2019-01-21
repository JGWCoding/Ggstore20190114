package ggstore.com.activity;

import android.view.View;
import android.widget.TextView;

import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;

public class RegisterDetailActivity extends BaseTitleActivity { //TODO 需要点击事件
    @Override
    protected CharSequence getContentTitle() {
        return getString(R.string.register);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_register_detail;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        final TextView textSir = findViewById(R.id.activity_register_detail_sir);
        final TextView textLady = findViewById(R.id.activity_register_detail_lady);
        textSir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSir.setBackgroundColor(getResources().getColor(R.color.bg));
                textLady.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        textLady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSir.setBackgroundColor(getResources().getColor(R.color.white));
                textLady.setBackgroundColor(getResources().getColor(R.color.bg));
            }
        });
        final TextView badyHaved = findViewById(R.id.activity_register_detail_bady_haved);
        final TextView badyHaving = findViewById(R.id.activity_register_detail_bady_having);
        final TextView badyNo = findViewById(R.id.activity_register_detail_bady_no);

        badyHaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                badyHaved.setBackgroundColor(getResources().getColor(R.color.bg));
                badyHaving.setBackgroundColor(getResources().getColor(R.color.white));
                badyNo.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        badyHaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                badyHaving.setBackgroundColor(getResources().getColor(R.color.bg));
                badyHaved.setBackgroundColor(getResources().getColor(R.color.white));
                badyNo.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        badyNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                badyNo.setBackgroundColor(getResources().getColor(R.color.bg));
                badyHaving.setBackgroundColor(getResources().getColor(R.color.white));
                badyHaved.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

    }

    @Override
    protected void initData() {

    }
}
