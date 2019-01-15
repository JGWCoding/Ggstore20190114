package ggstore.com.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import ggstore.com.R;
import ggstore.com.activity.MainActivity;
import ggstore.com.activity.ReceiveAddressActivity;
import ggstore.com.base.BaseFragment;

public class ShopCartListFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_cart;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);//一定要getChildFragmentManager
        addFragment(R.id.fragment_shopcart_content,new ShopCartListRecycleFragment());
        TextView pay = findView(R.id.go);
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
    }
}