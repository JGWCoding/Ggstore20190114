package ggstore.com.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;
import ggstore.com.utils.ToastUtil;

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
                    ToastUtil.showToast("开始提交");
                    resetPassword(email.getText().toString());
                }
            }
        });
    }

    private void resetPassword(String email) {
//        OkHttpManager.runMainSync(Constent.base_url + "api_get_forgotpassword.php" + "?email=" + email, new OkHttpManager.DataCallBack() {
//            @Override
//            public void requestFailure(Request request, Exception e) {
//                ToastUtil.showToast("重置密码失败,请再一次重置");
//            }
//
//            @Override
//            public void requestSuccess(String result) throws Exception {
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    boolean success = jsonObject.optBoolean("success");
//                    if(success) {
//                        ToastUtil.showToast( "密码已发送到邮箱,请注意查收" + success);
//                        return;
//                    }
//                    String err_msg = jsonObject.optString("err_msg");
//                    ToastUtil.showToast(err_msg+ "测试账号不行" + success);
//                } catch (Exception e) {
//                    ToastUtil.showToast("获取密码失败,请再一次输入邮箱");
//                }
//            }
//        });
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
