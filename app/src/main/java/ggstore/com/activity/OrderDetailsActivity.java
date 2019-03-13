package ggstore.com.activity;

import android.widget.ImageView;
import android.widget.TextView;

import ggstore.com.BaseApplication;
import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;
import ggstore.com.constant.Constent;
import ggstore.com.utils.ToastUtil;

public class OrderDetailsActivity extends BaseTitleActivity {

    private TextView orderNumber;
    private TextView paymentDay;
    private TextView name;
    private ImageView productImg;
    private TextView number;
    private TextView price;
    private TextView state;

    @Override
    protected CharSequence getContentTitle() {
        return getString(R.string.order_details);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        orderNumber = findViewById(R.id.activity_order_detail_order_number);
        paymentDay = findViewById(R.id.activity_order_detail_payment_day);
        name = findViewById(R.id.activity_order_detail_name);
        productImg = findViewById(R.id.activity_order_detail_production_img);
        number = findViewById(R.id.activity_order_detail_number);
        price = findViewById(R.id.activity_order_detail_price);
        state = findViewById(R.id.activity_order_detail_state);
    }

    @Override
    protected void initData() {
        if (Constent.orderNumber == null) {
            ToastUtil.showToast("ordernumber is null");
            return;
        }

        orderNumber.setText(Constent.orderNumber.getOrder_id() + "");
        paymentDay.setText(Constent.orderNumber.getPay_day());
        name.setText(Constent.orderNumber.getName());
        mImageLoader.load(Constent.orderNumber.getImage_url()).into(productImg);
        number.setText(Constent.orderNumber.getBuy_number());
        price.setText(BaseApplication.context().getString(R.string.product_price,Constent.orderNumber.getPrice()+""));
        state.setText(Constent.orderNumber.getOrder_state());
    }
}
