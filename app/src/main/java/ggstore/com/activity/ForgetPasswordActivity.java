package ggstore.com.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONObject;

import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;
import ggstore.com.constant.Constent;
import ggstore.com.utils.OkHttpManager;
import ggstore.com.utils.ToastUtil;
import okhttp3.Request;

public class ForgetPasswordActivity extends BaseTitleActivity {


    private ImageView submit;
    private EditText email;
    private EditText name;

    @Override
    protected void initData() {
        submit = (ImageView) findViewById(R.id.submit);
        email = (EditText) findViewById(R.id.et_email);
        name = (EditText) findViewById(R.id.et_name);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText())) {
                    ToastUtil.showToast("请输入正确邮箱");
                } else {
                    resetPassword(email.getText().toString());
                }
            }
        });
    }



    private void resetPassword(String email) {
        OkHttpManager.getAsync(Constent.url_forget_password + email, new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                ToastUtil.showToast("network is error,please check your network");
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.optInt("code");
                    if(code==200) {
                        ToastUtil.showToast(jsonObject.optString("msg","mailbox look in new password"));
                        finish();
                        return;
                    }else{
                        ToastUtil.showToast( jsonObject.optString("msg","fall"));
                    }
                } catch (Exception e) {
                    ToastUtil.showToast("获取密码失败,请再一次输入邮箱");
                }
            }
        });
    }

    @Override
    protected CharSequence getContentTitle() {
        return getString(R.string.forget_password);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_forget_password;
    }
}
