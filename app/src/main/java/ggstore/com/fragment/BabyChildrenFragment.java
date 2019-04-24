package ggstore.com.fragment;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;

import ggstore.com.R;
import ggstore.com.base.BaseFragment;

public class BabyChildrenFragment extends BaseFragment {   //todo 布局xml文件和填充数据不是本身

    private TabLayout tabLayout;
    public static boolean isSingle = true; //是否为单列

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gravida_mother;
    }

    @Override
    protected void initWidget(View root) {
        isSingle = true;
        addFragment(R.id.fragment_new_product_fragment,new BabyChildrenRecyclerRecyclerFragment());
        final ImageView singleImg = findView(R.id.fragment_new_product_img);
        final ImageView doubleImg = findView(R.id.fragment_new_product_img2);
        singleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSingle=true;
                BabyChildrenRecyclerRecyclerFragment fragment = (BabyChildrenRecyclerRecyclerFragment) getChildFragmentManager().findFragmentByTag(BabyChildrenRecyclerRecyclerFragment.class.getName());
                fragment.setLayoutManager(fragment.getLayoutManager());
                singleImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_rectangle_bg_5));
                doubleImg.setBackgroundDrawable(null);
            }
        });
        doubleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSingle=false;
                singleImg.setBackgroundDrawable(null);
                doubleImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_rectangle_bg_5));
                BabyChildrenRecyclerRecyclerFragment fragment = (BabyChildrenRecyclerRecyclerFragment) getChildFragmentManager().findFragmentByTag(BabyChildrenRecyclerRecyclerFragment.class.getName());
                fragment.setLayoutManager(fragment.getLayoutManager());
            }
        });
    }
}
