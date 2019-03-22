package ggstore.com.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import ggstore.com.App;
import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;
import ggstore.com.constant.Constant;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.OkHttpManager;
import ggstore.com.utils.RegexUtils;
import ggstore.com.utils.ToastUtil;
import ggstore.com.view.DemoPopup;
import okhttp3.Request;
import razerdp.basepopup.BasePopupWindow;

public class RegisterDetailActivity extends BaseTitleActivity {
    private TextView spYear;
    private TextView spMonth;
    private TextView spDay;

    @Override
    protected CharSequence getContentTitle() {
        return getString(R.string.register);
    }

    private ArrayList<String> dataYear = new ArrayList<String>();
    private ArrayList<String> dataMonth = new ArrayList<String>();
    private ArrayList<String> dataDay = new ArrayList<String>();
    private boolean isSir = true;
    private int babyState = 0;//0代表有 1怀孕中 2没有

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
                isSir = true;
                textSir.setBackgroundColor(getResources().getColor(R.color.bg));
                textLady.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        textLady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSir = false;
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
                babyState = 0;
                badyHaved.setBackgroundColor(getResources().getColor(R.color.bg));
                badyHaving.setBackgroundColor(getResources().getColor(R.color.white));
                badyNo.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        badyHaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                babyState = 1;
                badyHaving.setBackgroundColor(getResources().getColor(R.color.bg));
                badyHaved.setBackgroundColor(getResources().getColor(R.color.white));
                badyNo.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        badyNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                babyState = 2;
                badyNo.setBackgroundColor(getResources().getColor(R.color.bg));
                badyHaving.setBackgroundColor(getResources().getColor(R.color.white));
                badyHaved.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        spYear = findViewById(R.id.activity_register_detail_year);
        // 年份设定为当年的前后20年
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String date = dff.format(new Date());
        String[] dateArr = date.split("-");
        if (dateArr.length == 3) {
            for (int i = 0; i < 100; i++) {
                dataYear.add(Integer.valueOf(dateArr[0]) - i + "");
            }
            for (int i = 0; i < 12; i++) {
                dataMonth.add(i + 1 + "");
            }
            spMonth = findViewById(R.id.activity_register_detail_month);
            for (int i = 0; i < 31; i++) {
                dataDay.add(i + 1 + "");
            }
            spDay = findViewById(R.id.activity_register_detail_day);
        }
        spYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(spYear, dataYear, App.context().getString(R.string.year));
            }
        });
        spMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(spMonth, dataMonth, App.context().getString(R.string.month));
            }
        });
        spDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(spDay, dataDay, getString(R.string.day));
            }
        });
        findViewById(R.id.activity_register_detail_create_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateAccount();
            }
        });
        final TextView area = (TextView) findViewById(R.id.activity_register_detail_area);
        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(area, new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.city))), getString(R.string.area));
            }
        });
    }

    private void popup(final TextView area, final ArrayList<String> array, String title) {
        final DemoPopup popup = new DemoPopup(RegisterDetailActivity.this);
        popup.findViewById(R.id.popup_list_ll).setPadding(0, BarUtils.getStatusBarHeight(), 0, 0);
        TextView tv = (TextView) popup.findViewById(R.id.popup_list_title);
        tv.setText(title);
        ListView listView = popup.findViewById(R.id.popup_list);
        listView.setAdapter(new ArrayAdapter<String>(RegisterDetailActivity.this, android.R.layout.simple_list_item_1, array));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) area).setText(array.get(position));
                popup.dismiss();
            }
        });
        popup.setOnDismissListener(new BasePopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (area == spYear) {
                    popup(spMonth, dataMonth, getString(R.string.month));
                } else if (area == spMonth) {
                    popup(spDay, dataDay, getString(R.string.day));
                }
            }
        });
        popup.showPopupWindow();
    }

    private void onCreateAccount() {
        EditText view_email = (EditText) findViewById(R.id.activity_register_detail_email);
        if (TextUtils.isEmpty(view_email.getText().toString())) {
            ToastUtil.showToast("please input your email");
            return;
        } else if (!RegexUtils.isEmail(view_email.getText())) {
            ToastUtil.showToast("your email is error");
        }
        EditText view_loginName = (EditText) findViewById(R.id.activity_register_detail_login_name);
        if (TextUtils.isEmpty(view_loginName.getText().toString())) {
            ToastUtil.showToast("please input your login name");
            return;
        }
        EditText view_setPassword = findViewById(R.id.activity_register_detail_set_password);
        if (TextUtils.isEmpty(view_setPassword.getText().toString())) {
            ToastUtil.showToast("password is empty");
            return;
        } else if (view_setPassword.getText().toString().length() < 8) {
            ToastUtil.showToast("Please set an 8-digit password");
            return;
        }
        EditText view_confirmPassword = findViewById(R.id.activity_register_detail_confirm_password);
        if (TextUtils.isEmpty(view_confirmPassword.getText().toString())) {
            ToastUtil.showToast("confirm password is error");
            return;
        }
        if (!view_setPassword.getText().toString().equals(view_confirmPassword.getText().toString())) {
            ToastUtil.showToast("confirm password please reset");
            return;
        }
        EditText view_name = findViewById(R.id.activity_register_detail_name);
        if (TextUtils.isEmpty(view_name.getText().toString())) {
            ToastUtil.showToast("your name is empty");
            return;
        }


        EditText view_tel = findViewById(R.id.activity_register_detail_tel);
        if (TextUtils.isEmpty(view_tel.getText().toString())) {
            ToastUtil.showToast("your telephone is empty");
            return;
        } else if (!RegexUtils.isMobileSimple_8(view_tel.getText().toString())) {
            ToastUtil.showToast("your telephone is error");
            return;
        }
        EditText view_address = findViewById(R.id.activity_register_detail_address);
        if (TextUtils.isEmpty(view_address.getText().toString())) {
            ToastUtil.showToast("your address is empty");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("email", view_email.getText().toString());
        map.put("name", view_name.getText().toString());
        map.put("set_password", view_setPassword.getText().toString());
        map.put("confirm", view_confirmPassword.getText().toString());
        map.put("login_name", view_loginName.getText().toString());
        map.put("tel", view_tel.getText().toString());
        map.put("address", view_address.getText().toString());
        map.put("gender", isSir ? "sir" : "lady");
        map.put("have_bady", babyState == 0 ? "have" : babyState == 1 ? "pregnant" : "no");//pregnant 怀孕中
        map.put("birthday", spYear.getText() + "-" + spMonth.getText() + "-" + spDay.getText());

        OkHttpManager.postAsync(this, Constant.url_register, map, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                ToastUtil.showToast("network is error,please check your network");
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                int code = new JSONObject(result).optInt("code");
                LogUtil.e(result);
                if (code == 200) {  //TODO 这里登录跳转主页面需要改变登录者信息
                    startActivity(new Intent(RegisterDetailActivity.this, MainActivity.class));
                } else {
                    ToastUtil.showToast("register is error");
                }
            }
        });
    }

    @Override
    protected void initData() {

    }
}
