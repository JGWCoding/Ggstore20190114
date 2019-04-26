package ggstore.com.activity;

import android.widget.TextView;

import java.util.ArrayList;

import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;
import ggstore.com.bean.OrderNumberBean;
import ggstore.com.constant.Constant;
import ggstore.com.fragment.OrderDetailsRecyclerFragment;
import ggstore.com.utils.AppOperator;
import ggstore.com.utils.OrderNumberManagerUtil;
import ggstore.com.utils.ToastUtil;

public class OrderDetailsActivity extends BaseTitleActivity {

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
        if (Constant.orderNumber == null) {
            ToastUtil.showToast(R.string.order_number_empty);
            return;
        }
        TextView orderNumber = findViewById(R.id.activity_order_detail_number);
        orderNumber.setText(Constant.orderNumber.getOrder_id()+"");
        AppOperator.runOnThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<OrderNumberBean> orderNumberBeans = OrderNumberManagerUtil.queryOrderId(Constant.orderNumber.getOrder_id());
                float total = 0;
                for (OrderNumberBean bean:orderNumberBeans){
                    total+=(bean.getBuy_number()*bean.getPrice());
                }
                final int finalTotal = (int) total;
                AppOperator.runMainThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView)findViewById(R.id.activity_order_detail_total)).setText(getString(R.string.total_price, finalTotal));
                    }
                });
            }
        });
        addFragment(R.id.activity_order_detail_fragment,new OrderDetailsRecyclerFragment());
    }

    @Override
    protected void initData() {

    }
}
