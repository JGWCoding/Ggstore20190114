package ggstore.com.fragment;

import android.os.Bundle;
import android.view.View;

import ggstore.com.R;
import ggstore.com.activity.MainActivity;
import ggstore.com.base.BaseFragment;

public class ShopCartFragment extends BaseFragment {
    //购物车
    //1.怎么获取购物车的数据  --- 数据库,网络,内存   --- 是否做网络缓存,本地数据库化,离线也可查看
    //2.
    boolean isEmptyShopCart = false;
    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        //bundle 是否有数据
        if (bundle==null){
            isEmptyShopCart = false;
        }else{
            isEmptyShopCart = bundle.getBoolean("isEmptyShopCart");
            if (((MainActivity)getActivity()).badge.getBadgeNumber()<1){
                isEmptyShopCart = true;
            }
        }

    }

    public void emptyShopCart(){
        ShopCartFragment shopCartFragment = new ShopCartFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEmptyShopCart",true);
        shopCartFragment.setArguments(bundle);
        ((MainActivity) getActivity()).emptyShopCartFragment();
    }
    @Override
    protected int getLayoutId() {
//        isEmptyShopCart = false; //手动设置为空
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
                    ((MainActivity) getActivity()).shopCartListFragment();
                }
            });
//
//            findView(R.id.go).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Intent intent = new Intent(getActivity(),ReceiveAddressActivity.class);
////                    startActivity(intent);
//                    addFragment(R.id.fragment_shopcart_content,new ShopCartRecycleFragment());
//                }
//            });
            findView(R.id.go_shopping).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).newProductFragment();
                }
            });




        }
    }
}
