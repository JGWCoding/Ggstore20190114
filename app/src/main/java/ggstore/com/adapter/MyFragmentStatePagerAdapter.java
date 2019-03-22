package ggstore.com.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    public ArrayList<Fragment> listfragment; //创建一个List<Fragment>
    public ArrayList<String> titles; //创建一个List<Fragment>

    //定义构造带两个参数
    public MyFragmentStatePagerAdapter(FragmentManager fm, ArrayList<Fragment> list, ArrayList<String> titles) {
        super(fm);
        this.listfragment = list;
        this.titles = titles;
        if (listfragment == null) {
            listfragment = new ArrayList<>();
        }
        if (titles == null) {
            this.titles = new ArrayList<>();
        }
    }

    @Override
    public Fragment getItem(int arg0) {
        return listfragment.get(arg0); //返回第几个fragment
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        if (listfragment == null) {
            return 0;
        }
        return listfragment.size(); //总共有多少个fragment
    }
}