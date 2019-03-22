package ggstore.com.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import ggstore.com.fragment.ToyEducationRecyclerFragment;
import ggstore.com.fragment.ToyEducationRecyclerFragment2;
import ggstore.com.fragment.ToyEducationRecyclerFragment3;
import ggstore.com.utils.LogUtil;

public class ToyEducationFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    public ArrayList<WeakReference<Fragment>> listfragment; //创建一个List<Fragment>
    public ArrayList<String> titles; //创建一个List<Fragment>

    //定义构造带两个参数
    public ToyEducationFragmentStatePagerAdapter(FragmentManager fm, ArrayList<WeakReference<Fragment>> list, ArrayList<String> titles) {
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
        Fragment fragment = listfragment.get(arg0).get();
        if (fragment != null) {
            return fragment;
        }
        LogUtil.e("fragment被回收,重新创建");
        switch (arg0) {
            case 0:
                fragment = new ToyEducationRecyclerFragment();
                break;
            case 1:
                fragment = new ToyEducationRecyclerFragment2();
                break;
            case 2:
                fragment = new ToyEducationRecyclerFragment3();
                break;
            default:
                fragment = new ToyEducationRecyclerFragment();
                break;
        }
        return fragment; //返回第几个fragment
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