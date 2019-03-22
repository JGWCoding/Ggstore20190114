package ggstore.com.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import ggstore.com.App;
import ggstore.com.R;
import ggstore.com.adapter.ToyEducationFragmentStatePagerAdapter;
import ggstore.com.base.BaseFragment;
import ggstore.com.utils.LogUtil;

public class ToyEduFragment extends BaseFragment {

    public ViewPager viewPager;
    public int[] titles = {R.string.education_toy, R.string.baby_toy, R.string.book};
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_toy_education;
    }

    @Override
    protected void initWidget(View root) {
        TabLayout tabLayout = findView(R.id.main_tab);
        viewPager = findView(R.id.fragment_new_product_viewpager);
        ArrayList<WeakReference<Fragment>> list =new ArrayList<>();   //这里会有
        list.add(new WeakReference<Fragment>(new ToyEducationRecyclerFragment()));
        list.add(new WeakReference<Fragment>(new ToyEducationRecyclerFragment2()));
        list.add(new WeakReference<Fragment>(new ToyEducationRecyclerFragment3()));
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < this.titles.length; i++) {
            titles.add(App.context().getString(this.titles[i]));
        }
        viewPager.setAdapter(new ToyEducationFragmentStatePagerAdapter(getChildFragmentManager(), list,titles));//要使用getChild,不然会出现Fragment空白
        tabLayout.setupWithViewPager(viewPager);//这行代码会removeAllTabs,使用Adapter的getPageTitle方法设置tab
    }


    @Override
    protected void finalize() throws Throwable {
        LogUtil.e("start gc");
        super.finalize();
    }

}
