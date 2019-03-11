package ggstore.com.activity;

import android.content.Intent;
import android.view.View;

import ggstore.com.R;
import ggstore.com.base.BaseActivity;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.PayPalHelper;
import ggstore.com.utils.ToastUtil;

public class PaypalActivity extends BaseActivity {


    private static final int REQUEST_CODE = 0x001;

    @Override
    protected int getContentView() {
        return R.layout.activity_paypal;
    }

    @Override
    protected void initWidget() {
        PayPalHelper.getInstance().startPayPalService(PaypalActivity.this);
        findViewById(R.id.paypal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(PaypalActivity.this, MainActivity.class);
//                intent.putExtra("paypal","success");
//                startActivity(intent);
//                onBraintreeSubmit(v);

                PayPalHelper.getInstance().doPayPalPay(PaypalActivity.this);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        PayPalHelper.getInstance().confirmPayResult(PaypalActivity.this, requestCode, resultCode, data, new PayPalHelper.DoResult() {
            @Override
            public void confirmSuccess(String id) {
                ToastUtil.showToast("paypal is success");
                LogUtil.e("支付成功" + id);
                Intent intent = new Intent(PaypalActivity.this, MainActivity.class);
                intent.putExtra("paypal","success");
                startActivity(intent);
            }

            @Override
            public void confirmNetWorkError() {
                LogUtil.e("支付失败");
            }

            @Override
            public void customerCanceled() {
                LogUtil.e("支付取消");
            }

            @Override
            public void confirmFuturePayment() {

            }

            @Override
            public void invalidPaymentConfiguration() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PayPalHelper.getInstance().stopPayPalService(this);
    }

    @Override
    protected void initData() {

    }
//
//// 初始化并得到服务器的token,最好常刷新,至少应用重启时刷新token
////    AsyncHttpClient client = new AsyncHttpClient();
////    client.get("https://your-server/client_token",new
////
////    TextHttpResponseHandler() {
////        @Override
////        public void onSuccess ( int statusCode, Header[] headers, String clientToken){
////            this.clientToken = clientToken;
////        }
////    });
//
//    //假定token获取,开始跳转到paypal付款页面
//    public void onBraintreeSubmit(View v) {
//        DropInRequest dropInRequest = new DropInRequest()
//                .clientToken("eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiIwMjFkN2IxMWNiNmFmODQ3ODdmNTk5YTYyYjYyN2UwNGQ1NTQyZTA2MWY3YzMzY2EwMGNiNTJhMTQzYWY3NGRmfGNyZWF0ZWRfYXQ9MjAxOS0wMS0zMFQwMjo1MDo1MC4yMTY5OTUzOTkrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJncmFwaFFMIjp7InVybCI6Imh0dHBzOi8vcGF5bWVudHMuc2FuZGJveC5icmFpbnRyZWUtYXBpLmNvbS9ncmFwaHFsIiwiZGF0ZSI6IjIwMTgtMDUtMDgifSwiY2hhbGxlbmdlcyI6W10sImVudmlyb25tZW50Ijoic2FuZGJveCIsImNsaWVudEFwaVVybCI6Imh0dHBzOi8vYXBpLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb206NDQzL21lcmNoYW50cy8zNDhwazljZ2YzYmd5dzJiL2NsaWVudF9hcGkiLCJhc3NldHNVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImF1dGhVcmwiOiJodHRwczovL2F1dGgudmVubW8uc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbSIsImFuYWx5dGljcyI6eyJ1cmwiOiJodHRwczovL29yaWdpbi1hbmFseXRpY3Mtc2FuZC5zYW5kYm94LmJyYWludHJlZS1hcGkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=");
//        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//                // use the result to update your UI and send the payment method nonce to your server
//                ToastUtil.showToast("paypal information send to server");
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                // the user canceled
//                ToastUtil.showToast("paypal is canceled");
//            } else {
//                // handle errors here, an exception may be available in
//                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
//                ToastUtil.showToast(error.getMessage());
//            }
//        }
//    }

//    void postNonceToServer(String nonce) {
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.put("payment_method_nonce", nonce);
//        client.post("http://your-server/checkout", params,
//                new AsyncHttpResponseHandler() {
//                    // Your implementation here
//                }
//        );
//    }

}
