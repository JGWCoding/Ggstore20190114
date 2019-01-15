package ggstore.com.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ggstore.com.R;
import ggstore.com.base.BaseFragment;

public class NewProductFragment extends BaseFragment {

    private TabLayout tabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_product;
    }

    @Override
    protected void initWidget(View root) {
        tabLayout = findView(R.id.main_tab);
        ViewPager viewPager = findView(R.id.main_viewpager);

        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new NewProductRecyclerFragment());
        list.add(new NewProductRecyclerFragment()); //使当前的viewPager.Adapter为空,不调用到 removeAllTabs
        tabLayout.setupWithViewPager(viewPager);//这行代码会removeAllTabs,使用Adapter的getPageTitle方法设置tab
        viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), list));//必须使用getChild
//        for (int i = 0; i < tabLayout.getTabCount(); i++) { //我手动设置tab会造成问题
//            TabLayout.Tab tabAt = tabLayout.getTabAt(i);
//            tabAt.setIcon(R.mipmap.ic_launcher);
    //        } //
    }

    @Override
    public void onStart() {
        super.onStart();
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);    //TODO 是否修复android5.0显示问题
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher);
    }

    class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        private FragmentManager fragmetnmanager;  //创建FragmentManager
        private List<Fragment> listfragment; //创建一个List<Fragment>

        //定义构造带两个参数
        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.fragmetnmanager = fm;
            this.listfragment = list;
        }

        @Override
        public Fragment getItem(int arg0) {
            tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);    //TODO 是否修复android5.0显示问题
            tabLayout.getTabAt(1).setIcon(R.mipmap.ic_launcher);    //hide和show Fragment不显示

            return listfragment.get(arg0); //返回第几个fragment
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
