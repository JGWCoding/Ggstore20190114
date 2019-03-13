package ggstore.com.fragment;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;

import ggstore.com.R;
import ggstore.com.activity.MainActivity;
import ggstore.com.base.BaseFragment;
import ggstore.com.constant.Constent;
import ggstore.com.utils.AppOperator;
import ggstore.com.utils.OrderNumberManagerUtil;
import ggstore.com.utils.ShopCartItemManagerUtil;
import ggstore.com.utils.ToastUtil;

public class OrderNumberFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_number;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        final String yyyyMMddHHmmss = TimeUtils.date2String(TimeUtils.getDateByNow(0, TimeConstants.HOUR), new SimpleDateFormat("yyyyMMddHHmmss"));
        ((TextView) findView(R.id.fragment_order_number_number)).setText(yyyyMMddHHmmss);
        if (Constent.shopCartList == null) {
            //todo 应该重新获取 --- 这里已经付款了
            ToastUtil.showToast("product list is null");
        } else {
            AppOperator.runOnThreadNoRemove(new Runnable() {    //TODO 这里应该由服务器生成订单
                @Override
                public void run() {
                    if (!OrderNumberManagerUtil.queryOrderId(Long.valueOf(yyyyMMddHHmmss))) {//添加到订单里,删除购物车东西
                        OrderNumberManagerUtil.addOrderNumber(Constent.shopCartList, yyyyMMddHHmmss);
                        ShopCartItemManagerUtil.deleteShopCart(Constent.shopCartList);
                    }else {
                        ToastUtil.showToast("create order number fail");
                    }
                }
            });
        }

        findView(R.id.back_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).newProductFragment();
            }
        });
        findView(R.id.fragment_order_number_my_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).myOrderfragment();
            }
        });
    }
}
