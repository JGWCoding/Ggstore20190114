package ggstore.com.fragment;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import ggstore.com.R;
import ggstore.com.base.BaseFragment;
import ggstore.com.utils.LogUtil;

public class EducationToyFragment extends BaseFragment {

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        ToyEducationRecyclerFragment fragment = (ToyEducationRecyclerFragment) getChildFragmentManager().findFragmentByTag(ToyEducationRecyclerFragment.class.getName());
        if (fragment!=null) {
            LogUtil.i("回收Fragment:"+this.getClass().getName());
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment).commitNowAllowingStateLoss();
        }else{
            LogUtil.i("已经回收 Fragment:"+this.getClass().getName());
        }
    }
}
