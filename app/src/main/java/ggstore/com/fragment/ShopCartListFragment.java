package ggstore.com.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import ggstore.com.R;
import ggstore.com.activity.MainActivity;
import ggstore.com.activity.ReceiveAddressActivity;
import ggstore.com.base.BaseFragment;
import ggstore.com.bean.ShopCartBean;
import ggstore.com.constant.Constent;
import ggstore.com.utils.ShopCartItemManagerUtil;

public class ShopCartListFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_cart;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);//一定要getChildFragmentManager
        addFragment(R.id.fragment_shopcart_content,new ShopCartListRecycleFragment());
        TextView pay = findView(R.id.fragment_shopcart_pay);
        pay.setText(R.string.go);
        pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),ReceiveAddressActivity.class);
                    startActivity(intent);
                }
            });
        TextView cancel = findView(R.id.go_shopping);
        cancel.setText(R.string.back);
        cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).shopCartFragment();
                }
            });

        setPriceSum();
    }
    public void setPriceSum(){
        List<ShopCartBean> shopCartBeans = ShopCartItemManagerUtil.queryAll();
        Constent.shopCartList = shopCartBeans;
        int priceSum = 50;
        for (ShopCartBean bean:shopCartBeans){
            priceSum += bean.getPrice()*bean.getBuy_number();
        }
        ((TextView)findView(R.id.fragment_shopcart_price)).setText(getString(R.string.total_price,priceSum));
    }
}