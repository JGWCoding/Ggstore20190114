package ggstore.com.fragment;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;

import ggstore.com.R;
import ggstore.com.base.BaseFragment;

public class TodayDiscountFragment extends BaseFragment {

    private TabLayout tabLayout;
    public static boolean isSingle = true; //是否为单列

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_today_discount;
    }

    @Override
    protected void initWidget(View root) {
        isSingle = true;
        addFragment(R.id.fragment_new_product_fragment,new TodayDiscountRecyclerFragment());
        final ImageView singleImg = findView(R.id.fragment_new_product_img);
        final ImageView doubleImg = findView(R.id.fragment_new_product_img2);
        singleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSingle=true;
                TodayDiscountRecyclerFragment fragment = (TodayDiscountRecyclerFragment) getChildFragmentManager().findFragmentByTag(TodayDiscountRecyclerFragment.class.getName());
                fragment.setLayoutManager(fragment.getLayoutManager());
                singleImg.setBackgroundResource(R.drawable.single_icon_bule);
                doubleImg.setBackgroundResource(R.drawable.double_icon_white);
            }
        });
        doubleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSingle=false;
                singleImg.setBackgroundResource(R.drawable.single_icon_white);
                doubleImg.setBackgroundResource(R.drawable.double_icon_blue);
                TodayDiscountRecyclerFragment fragment = (TodayDiscountRecyclerFragment) getChildFragmentManager().findFragmentByTag(TodayDiscountRecyclerFragment.class.getName());
                fragment.setLayoutManager(fragment.getLayoutManager());
            }
        });
    }
}
