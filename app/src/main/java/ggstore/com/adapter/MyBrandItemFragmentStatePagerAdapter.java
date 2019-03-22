package ggstore.com.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import ggstore.com.fragment.BrandItemRecyclerFragment;
import ggstore.com.utils.LogUtil;

public class MyBrandItemFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
        public ArrayList<WeakReference<Fragment>> listfragment; //创建一个List<Fragment>
        public ArrayList<String> titles; //创建一个List<Fragment>
        public ArrayList<String> args;

        //定义构造带两个参数
        public MyBrandItemFragmentStatePagerAdapter(FragmentManager fm, ArrayList<WeakReference<Fragment>> list, ArrayList<String> titles, ArrayList<String> args) {
            super(fm);
            this.listfragment = list;
            this.titles = titles;
            this.args = args;
            if (listfragment==null){
                listfragment = new ArrayList<>();
            }
            if (titles==null){
                this.titles = new ArrayList<>();
            }
        }

        @Override
        public Fragment getItem(int arg0) {
            Fragment fragment = listfragment.get(arg0).get();
            if (fragment==null){
                BrandItemRecyclerFragment fragment1 = new BrandItemRecyclerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", args.get(arg0));
                fragment1.setArguments(bundle);
//                WeakReference<Fragment> weak = new WeakReference<Fragment>(fragment1);
//                listfragment.add(arg0,weak);
                LogUtil.e("fragment被回收,重新创建");
                return fragment1;
            }else{
                return fragment;
            }
//            return listfragment.get(arg0).get(); //返回第几个fragment
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