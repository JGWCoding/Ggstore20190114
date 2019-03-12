package ggstore.com.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import ggstore.com.BuildConfig;
import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.OkHttpManager;
import ggstore.com.utils.PayPalHelper;
import ggstore.com.utils.ToastUtil;
import okhttp3.Request;

public class ShopTermsActivity extends BaseTitleActivity {

    private static final int REQUEST_CODE = 1;

    @Override
    protected CharSequence getContentTitle() {
        return getString(R.string.shop_terms);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_shop_terms;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        String text = "1. 本網上訂購服務，只供Googoogaga會員專享，如未成為會員可立即登記，費用全免。\n" +
                "2. 所有於產品頁內顯示的價目，並不包括送貨費用。有關詳情請參考「付款及送貨方式」。\n" +
                "3. 當確認訂單，我們會於核對貨品後，盡快處理閣下的訂單及發出<訂購確認通知書>的電郵，物流公司將會致電客人安排有關貨品之送貨日期及時段。\n" +
                "4. 為保障各會員之利益，各會員於結帳前，請再三確認閣下所購買貨品的名稱、數量以及閣下的地址、電話、收件人等資料，避免日後因訂購產品而產生任何糾紛，並影響到閣下收取產品的時間。\n" +
                "5. 此等使用條款將會不時作出修改。我們可自行決定在任何時間修改這些服務條款和政策，閣下同意遵守這些修改或修訂。任何在這些服務條款應被視為賦予任何第三方權利或利益。\n" +
                "6. 貨品圖片有少量顏色偏差，會員須承擔此風險。\n" +
                "7. 所有送出之產品均不接受退貨及退款，供應商對產品質量及是否給予換貨/退款持有最終的決定權。\n" +
                "8. 購買各類服務或Playgroup等課堂者，請先詳閱該優惠內的使用細則條款，並預先聯絡優惠商戶預約留位。所有預約經確定後恕不接受改期，如未能出席即當作放棄，本公司不會因缺席而作出改期、延期、賠償或退款。\n" +
                "9. 本網站並非產品或服務的供應商戶，故不承擔任何有關產品或服務質素及售後服務之責任，並不設更換或退款。產品如有任何問題或損壞，客戶必須於售後7日內自行聯絡該產品之供應商跟進。\n" +
                "10. 本網站庫存數量非即時數據，因此訂單在出貨時有可能會出現缺貨情況，敬請留意。\n" +
                "11. 如因個人問題或本網站系統故障、伺服器問題導致Googoogaga會員未能購買貨品，受影響之Googoogaga會員將不獲補償，不便之處，敬請原諒。\n" +
                "12. Googoogaga保留最終決定權。\n\n\n";
        ((TextView) findViewById(R.id.shop_terms_details)).setText(text);
        PayPalHelper.getInstance().startPayPalService(this);//todo  old paypal
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AsyncHttpClient client = new AsyncHttpClient();
//                client.get("https://your-server/client_token", new TextHttpResponseHandler() {
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, String clientToken) {
//                        this.clientToken = clientToken;
//                    }
//                });
                //获取token  --- 从服务器
                OkHttpManager.getAsync("https://your-server/client_token", new OkHttpManager.DataCallBack() {
                    @Override
                    public void requestFailure(Request request, Exception e) {

                    }

                    @Override
                    public void requestSuccess(String result) throws Exception {
                        token = result;
//                        onBraintreeSubmit(null);
                    }
                });
                if (!BuildConfig.DEBUG) {
                    PayPalHelper.getInstance().doPayPalPay(ShopTermsActivity.this); //todo  old paypal
                }
                if (BuildConfig.DEBUG) {
                    startActivity(new Intent(ShopTermsActivity.this, PaypalActivity.class));
                }

            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    private String token;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//todo  old paypal
        PayPalHelper.getInstance().confirmPayResult(this, requestCode, resultCode, data, new PayPalHelper.DoResult() {
            @Override
            public void confirmSuccess(String id) {
                ToastUtil.showToast("paypal is success");
                LogUtil.e("支付成功" + id);
                Intent intent = new Intent(ShopTermsActivity.this, MainActivity.class);
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
    protected void onDestroy() {//todo  old paypal
        super.onDestroy();
        PayPalHelper.getInstance().stopPayPalService(this);
    }

//    public void onBraintreeSubmit(View v) {
//        DropInRequest dropInRequest = new DropInRequest()
//                .clientToken("eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiI3ZTg1YTg0N2RlODMyOGMzYzJlMzdlNTI5OTY2MTliOTc1Y2I0NGIxZWYxOTlhOGUzY2Q4ZmQyMDk2NGFmMzQzfGNyZWF0ZWRfYXQ9MjAxOS0wMS0wM1QwODo1MToyOS4yOTM1ODA3MjArMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJncmFwaFFMIjp7InVybCI6Imh0dHBzOi8vcGF5bWVudHMuc2FuZGJveC5icmFpbnRyZWUtYXBpLmNvbS9ncmFwaHFsIiwiZGF0ZSI6IjIwMTgtMDUtMDgifSwiY2hhbGxlbmdlcyI6W10sImVudmlyb25tZW50Ijoic2FuZGJveCIsImNsaWVudEFwaVVybCI6Imh0dHBzOi8vYXBpLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb206NDQzL21lcmNoYW50cy8zNDhwazljZ2YzYmd5dzJiL2NsaWVudF9hcGkiLCJhc3NldHNVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImF1dGhVcmwiOiJodHRwczovL2F1dGgudmVubW8uc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbSIsImFuYWx5dGljcyI6eyJ1cmwiOiJodHRwczovL29yaWdpbi1hbmFseXRpY3Mtc2FuZC5zYW5kYm94LmJyYWludHJlZS1hcGkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=");
//        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//                // use the result to update your UI and send the payment method nonce to your server
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                // the user canceled
//            } else {
//                // handle errors here, an exception may be available in
//                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
//            }
//        }
//    }
}
