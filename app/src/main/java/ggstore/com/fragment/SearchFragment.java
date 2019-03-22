package ggstore.com.fragment;

import android.view.View;
import android.widget.ImageView;

import ggstore.com.R;
import ggstore.com.base.BaseFragment;

public class SearchFragment extends BaseFragment {//todo 布局xml文件和填充数据不是本身

    public static boolean isSingle = true; //是否为单列

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gravida_mother;
    }

    @Override
    protected void initWidget(View root) {
        isSingle = true;
        replaceFragment(R.id.fragment_new_product_fragment,new SearchRecyclerRecyclerFragment());
        final ImageView singleImg = findView(R.id.fragment_new_product_img);
        final ImageView doubleImg = findView(R.id.fragment_new_product_img2);
        singleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSingle=true;
                SearchRecyclerRecyclerFragment fragment = (SearchRecyclerRecyclerFragment) getChildFragmentManager().findFragmentByTag(SearchRecyclerRecyclerFragment.class.getName());
                fragment.setLayoutManager(fragment.getLayoutManager());//todo 找不到Fragment
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
                SearchRecyclerRecyclerFragment fragment = (SearchRecyclerRecyclerFragment) getChildFragmentManager().findFragmentByTag(SearchRecyclerRecyclerFragment.class.getName());
                fragment.setLayoutManager(fragment.getLayoutManager());
            }
        });
    }
}
