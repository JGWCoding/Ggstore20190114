package ggstore.com.fragment;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;

import ggstore.com.R;
import ggstore.com.base.BaseFragment;

public class EducationToyFragment extends BaseFragment {

    private TabLayout tabLayout;
    public static boolean isSingle = true; //是否为单列

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_product_test;
    }

    @Override
    protected void initWidget(View root) {
        isSingle = true;
        addFragment(R.id.fragment_new_product_fragment,new ToyEducationRecyclerFragment());
        final ImageView singleImg = findView(R.id.fragment_new_product_img);
        final ImageView doubleImg = findView(R.id.fragment_new_product_img2);
        singleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSingle=true;
                ToyEducationRecyclerFragment fragment = (ToyEducationRecyclerFragment) getChildFragmentManager().findFragmentByTag(ToyEducationRecyclerFragment.class.getName());
                fragment.setLayoutManager(fragment.getLayoutManager());
                singleImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_rectangle_bg));
                doubleImg.setBackgroundDrawable(null);
            }
        });
        doubleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSingle=false;
                singleImg.setBackgroundDrawable(null);
                doubleImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_rectangle_bg));
                ToyEducationRecyclerFragment fragment = (ToyEducationRecyclerFragment) getChildFragmentManager().findFragmentByTag(ToyEducationRecyclerFragment.class.getName());
                fragment.setLayoutManager(fragment.getLayoutManager());
            }
        });
    }

}
