package ggstore.com.fragment;

import android.view.View;

import ggstore.com.R;
import ggstore.com.activity.MainActivity;
import ggstore.com.base.BaseFragment;

public class OrderNumberFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_number;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        findView(R.id.back_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).newProductFragment();
            }
        });
        findView(R.id.my_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).myOrderfragment();
            }
        });
    }
}
