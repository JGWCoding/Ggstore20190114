package ggstore.com.fragment;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;

import ggstore.com.R;
import ggstore.com.base.BaseFragment;

public class BookFragment extends BaseFragment {

    private TabLayout tabLayout;
    public static boolean isSingle = true; //是否为单列

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_product_test;
    }

    @Override
    protected void initWidget(View root) {
        addFragment(R.id.fragment_new_product_fragment,new ToyEducationRecyclerFragment3());
        final ImageView singleImg = findView(R.id.fragment_new_product_img);
        final ImageView doubleImg = findView(R.id.fragment_new_product_img2);
        singleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSingle=true;
                ToyEducationRecyclerFragment3 fragment = (ToyEducationRecyclerFragment3) getChildFragmentManager().findFragmentByTag(ToyEducationRecyclerFragment3.class.getName());
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
                ToyEducationRecyclerFragment3 fragment = (ToyEducationRecyclerFragment3) getChildFragmentManager().findFragmentByTag(ToyEducationRecyclerFragment3.class.getName());
                fragment.setLayoutManager(fragment.getLayoutManager());
            }
        });
    }

}
