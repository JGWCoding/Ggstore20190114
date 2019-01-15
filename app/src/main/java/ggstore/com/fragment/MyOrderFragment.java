package ggstore.com.fragment;

import android.view.View;

import ggstore.com.R;
import ggstore.com.base.BaseFragment;

public class MyOrderFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_order;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        addFragment(R.id.fragment_myorder_content,new MyOrderRecyclerFragment());
    }
}
