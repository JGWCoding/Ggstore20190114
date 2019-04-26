package ggstore.com.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;

import java.util.ArrayList;
import java.util.Arrays;

import ggstore.com.BuildConfig;
import ggstore.com.R;
import ggstore.com.adapter.ListViewAdapter;
import ggstore.com.base.BaseTitleActivity;
import ggstore.com.utils.RegexUtils;
import ggstore.com.utils.TDevice;
import ggstore.com.utils.ToastUtil;
import ggstore.com.view.DemoPopup;

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
        final TextView area = findViewById(R.id.activity_address_area);
        final TextView address = findViewById(R.id.activity_address_address);
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
                    if (TextUtils.isEmpty(address.getText())) {
                        ToastUtil.showToast(R.string.input_address);
                        return;
                    }
                }
                //TODO 需要进行收货资料上传
                startActivity(new Intent(ReceiveAddressActivity.this, ShopTermsActivity.class));
            }
        });
        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup(area, new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.city))), getString(R.string.area));
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
    private void popup(final TextView area, final ArrayList<String> array, String title) {
        final DemoPopup popup = new DemoPopup(this);
        popup.findViewById(R.id.popup_list_ll).setPadding(0, BarUtils.getStatusBarHeight(), 0, 0);
        TextView tv = (TextView) popup.findViewById(R.id.popup_list_title);
        tv.setText(title);
        ListView listView = popup.findViewById(R.id.popup_list);
        if (title.equals(getString(R.string.area))) {
            listView.setDividerHeight((int) TDevice.dp2px(20.f));
        }
        ListViewAdapter adapter = new ListViewAdapter(this, array, R.layout.list_item_text_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) area).setText(array.get(position));
                popup.dismiss();
            }
        });
        popup.showPopupWindow();
    }
}
