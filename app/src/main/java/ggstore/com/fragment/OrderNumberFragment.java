package ggstore.com.fragment;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.List;

import ggstore.com.R;
import ggstore.com.activity.MainActivity;
import ggstore.com.base.BaseFragment;
import ggstore.com.bean.ShopCartBean;
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
        final List<ShopCartBean> shopCartBeans = ShopCartItemManagerUtil.queryAll();
        if (shopCartBeans == null || shopCartBeans.size() == 0) {
            //todo 应该重新获取 --- 这里已经付款了
            ToastUtil.showToast(R.string.product_list_empty);
            return ; //相当于出bug了
        } else {
            AppOperator.runOnThreadNoRemove(new Runnable() {    //TODO 这里应该由服务器生成订单
                @Override
                public void run() {
                    if (!OrderNumberManagerUtil.queryOrderIdIsExist(Long.valueOf(yyyyMMddHHmmss))) {//添加到订单里,删除购物车东西
                        OrderNumberManagerUtil.addOrderNumber(shopCartBeans, yyyyMMddHHmmss);
                        ShopCartItemManagerUtil.deleteShopCart(shopCartBeans);
                    }else {
                        ToastUtil.showToast(R.string.create_order_number_fail);
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
