package ggstore.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ggstore.com.R;
import ggstore.com.activity.MainActivity;
import ggstore.com.activity.ReceiveAddressActivity;
import ggstore.com.base.BaseFragment;

public class ShopCartFragment extends BaseFragment {
    boolean isEmptyShopCart;
    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        //bundle 是否有数据
        if (bundle==null){
            isEmptyShopCart = true;
        }
        if (((MainActivity)getActivity()).badge.getBadgeNumber()<1){
            isEmptyShopCart = true;
        }
    }

    @Override
    protected int getLayoutId() {
        isEmptyShopCart = false; //手动设置为空
        if (isEmptyShopCart) return  R.layout.fragment_shop_cart_empty;
        return R.layout.fragment_shop_cart;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        if (!isEmptyShopCart) { //一定要getChildFragmentManager
           addFragment(R.id.fragment_shopcart_content,new ShopCartRecycleFragment());
            findView(R.id.go).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   addFragment(R.id.fragment_shopcart_content,new ShopCartListRecycleFragment());
                }
            });

            findView(R.id.go).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),ReceiveAddressActivity.class);
                    startActivity(intent);
                }
            });

        }
    }
}
