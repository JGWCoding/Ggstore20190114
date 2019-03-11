package ggstore.com.fragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import ggstore.com.BaseApplication;
import ggstore.com.R;
import ggstore.com.base.BaseFragment;

public class ToyEduFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_toy_education;
    }

    @Override
    protected void initWidget(View root) {
        TabLayout tabLayout = findView(R.id.main_tab);
        ViewPager viewPager = findView(R.id.fragment_new_product_viewpager);
        ArrayList<WeakReference<Fragment>> list = new ArrayList<>();   //这里会有
        list.add(new WeakReference<Fragment>(new EducationToyFragment()));
        list.add(new WeakReference<Fragment>(new BabyToyFragment()));
        list.add(new WeakReference<Fragment>(new BookFragment()));
        ArrayList<WeakReference<Fragment>> weakList = new ArrayList<WeakReference<Fragment>>(list);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), weakList));//要使用getChild,不然会出现Fragment空白
        tabLayout.setupWithViewPager(viewPager);//这行代码会removeAllTabs,使用Adapter的getPageTitle方法设置tab
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
       public int[] titles = {R.string.education_toy,R.string.baby_toy,R.string.book};
        private FragmentManager fragmetnmanager;  //创建FragmentManager
        private ArrayList<WeakReference<Fragment>> listfragment; //创建一个List<Fragment>

        //定义构造带两个参数
        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<WeakReference<Fragment>> list) {
            super(fm);
            this.fragmetnmanager = fm;
            this.listfragment = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            if (listfragment.get(arg0).get()==null){
                switch (arg0){

                }
            }else{

            }
            return listfragment.get(arg0).get(); //返回第几个fragment
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return BaseApplication.context().getString(titles[position]);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
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
