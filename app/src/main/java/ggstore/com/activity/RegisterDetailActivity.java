package ggstore.com.activity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;

public class RegisterDetailActivity extends BaseTitleActivity { //TODO 需要点击事件
    @Override
    protected CharSequence getContentTitle() {
        return getString(R.string.register);
    }

    private ArrayList<String> dataYear = new ArrayList<String>();
    private ArrayList<String> dataMonth = new ArrayList<String>();
    private ArrayList<String> dataDay = new ArrayList<String>();

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

        Spinner spYear = (Spinner) findViewById(R.id.activity_register_detail_year);
        // 年份设定为当年的前后20年
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String date = dff.format(new Date());
        String[] dateArr = date.split("-");
        if (dateArr.length == 3) {
            for (int i = 0; i < 100; i++) {
                dataYear.add(Integer.valueOf(dateArr[0]) - 99 + i + "");
            }

            ArrayAdapter adapterSpYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataYear);
            spYear.setAdapter(adapterSpYear);
            spYear.setSelection(99);// 默认选中今年

            for (int i = 0; i < 12; i++) {
                dataMonth.add(i+1+"");
            }
            Spinner spMonth = (Spinner) findViewById(R.id.activity_register_detail_month);
            ArrayAdapter adapterSpMonth = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataMonth);
            spMonth.setAdapter(adapterSpMonth);
            spMonth.setSelection(Integer.valueOf(dateArr[1])-1);

            for (int i = 0; i < 31; i++) {
                dataDay.add(i+1+"");
            }
            Spinner spDay = (Spinner) findViewById(R.id.activity_register_detail_day);
            ArrayAdapter spDayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dataDay);
            spDay.setAdapter(spDayAdapter);
            spDay.setSelection(Integer.valueOf(dateArr[2])-1);
        }
    }

    @Override
    protected void initData() {

    }
}
