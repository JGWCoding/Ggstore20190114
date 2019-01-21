package ggstore.com.fragment;

import android.view.View;

import ggstore.com.R;
import ggstore.com.base.BaseFragment;

public class ProductDetailFragment extends BaseFragment {   //没用到
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_detail;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
//        addFragment(R.id.fragment_myorder_content,new MyOrderRecyclerFragment());
    }
}
