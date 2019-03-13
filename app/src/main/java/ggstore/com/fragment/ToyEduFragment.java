package ggstore.com.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

import ggstore.com.BaseApplication;
import ggstore.com.R;
import ggstore.com.base.BaseFragment;
import ggstore.com.utils.LogUtil;

public class ToyEduFragment extends BaseFragment {

    public ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_toy_education;
    }

    @Override
    protected void initWidget(View root) {
        TabLayout tabLayout = findView(R.id.main_tab);
        viewPager = findView(R.id.fragment_new_product_viewpager);
        ArrayList<Fragment> list =new ArrayList<Fragment>() ;   //这里会有
        list.add(new EducationToyFragment());
        list.add(new BabyToyFragment());
        list.add(new BookFragment());
        viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), list));//要使用getChild,不然会出现Fragment空白
        tabLayout.setupWithViewPager(viewPager);//这行代码会removeAllTabs,使用Adapter的getPageTitle方法设置tab
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        LogUtil.e("start gc");
    }


    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public int[] titles = {R.string.education_toy, R.string.baby_toy, R.string.book};
//        private FragmentManager fragmetnmanager;  //创建FragmentManager
        public ArrayList<Fragment> listfragment; //创建一个List<Fragment>

        //定义构造带两个参数
        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
//            this.fragmetnmanager = fm;
            this.listfragment = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            return listfragment.get(arg0); //返回第几个fragment
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return BaseApplication.context().getString(titles[position]);
        }

        @Override
        public int getCount() {
            if (listfragment == null) {
                return 0;
            }
            return listfragment.size(); //总共有多少个fragment
        }
    }
}
