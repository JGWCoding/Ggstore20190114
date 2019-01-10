package ggstore.com.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import ggstore.com.R;
import ggstore.com.base.BaseActivity;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.PermissionsUtils;
import ggstore.com.utils.ToastUtils;

public class LoginActivity extends BaseActivity {

    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initWidget() {
        ImageView im_login = findViewById(R.id.login);
        im_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.forget_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.facebook_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));//获取公共形象
            }
        });
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        ToastUtils.showToast("facebook登录成功");
                    }

                    @Override
                    public void onCancel() {
                        ToastUtils.showToast("facebook登录取消");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        ToastUtils.showToast("facebook登录错误");
                    }
                });

        //  loginFacebook();
    }

    private void loginFacebook() {
        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));    //获取email权限
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                ToastUtils.showToast("fackbook登录成功");
                LogUtil.e("token:" + loginResult.getAccessToken() + " Granted:"
                        + loginResult.getRecentlyGrantedPermissions() + " Denied:" +
                        loginResult.getRecentlyDeniedPermissions());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        // LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));//获取公共形象

    }

    public boolean isLoginFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        return isLoggedIn;
    }

    @Override
    protected void initData() {
        requestPermissions();
        // App code
        profileTracker = new ProfileTracker() { //公共资料跟踪
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                if (currentProfile != null) {
                    LogUtil.e(currentProfile.getId());
                    LogUtil.e(currentProfile.getFirstName());
                    LogUtil.e(currentProfile.getLastName());
                    LogUtil.e(currentProfile.getMiddleName());
                    LogUtil.e(currentProfile.getName());            //这个为你的全名
                    LogUtil.e(currentProfile.getLinkUri().toString());  //空
                    LogUtil.e(currentProfile.getProfilePictureUri(400,400).toString());//
                }
            }
        };
    }

    protected void requestPermissions() {
        //两个日历权限和一个数据读写权限
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        PermissionsUtils.showSystemSetting = false;//是否支持显示系统设置权限设置窗口跳转
        //这里的this不是上下文，是Activity对象！
        PermissionsUtils.getInstance().chekPermissions(this, permissions, permissionsResult);
    }

    PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
        @Override
        public void passPermissons() {
            ToastUtils.showToast(R.string.permission_pass);
        }

        @Override
        public void forbitPermissons() {
            ToastUtils.showToast(R.string.permission_no_pass);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }
}
