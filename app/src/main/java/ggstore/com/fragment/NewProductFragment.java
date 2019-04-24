package ggstore.com.fragment;

import android.view.View;
import android.widget.ImageView;

import ggstore.com.R;
import ggstore.com.base.BaseFragment;

public class NewProductFragment extends BaseFragment {

    public static boolean isSingle = true; //是否为单列

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_product_test;
    }

    @Override
    protected void initWidget(View root) {
        isSingle = true;
        addFragment(R.id.fragment_new_product_fragment,new NewProductRecyclerFragmentTest());
        final ImageView singleImg = findView(R.id.fragment_new_product_img);
        final ImageView doubleImg = findView(R.id.fragment_new_product_img2);
        singleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSingle=true;
                NewProductRecyclerFragmentTest fragment = (NewProductRecyclerFragmentTest) getChildFragmentManager().findFragmentByTag(NewProductRecyclerFragmentTest.class.getName());
                fragment.setLayoutManager(fragment.getLayoutManager());
//                singleImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_rectangle_bg_5));
//                doubleImg.setBackgroundDrawable(null);
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
//                singleImg.setBackgroundDrawable(null);
//                doubleImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_rectangle_bg_5));
                NewProductRecyclerFragmentTest fragment = (NewProductRecyclerFragmentTest) getChildFragmentManager().findFragmentByTag(NewProductRecyclerFragmentTest.class.getName());
                fragment.setLayoutManager(fragment.getLayoutManager());
            }
        });
    }

}
