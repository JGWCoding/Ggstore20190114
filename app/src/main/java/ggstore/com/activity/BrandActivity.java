package ggstore.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import ggstore.com.App;
import ggstore.com.R;
import ggstore.com.adapter.MyBrandItemFragmentStatePagerAdapter;
import ggstore.com.base.BaseActivity;
import ggstore.com.bean.BrandImageBean;
import ggstore.com.fragment.BrandItemRecyclerFragment;
import ggstore.com.utils.ShopCartItemManagerUtil;
import ggstore.com.utils.ToastUtil;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class BrandActivity extends BaseActivity {
    public Badge badge;
    private static ArrayList<BrandImageBean> brands;
    private static int position;
    private ViewPager viewPager;

    public static void startActivity(Context context, List<BrandImageBean> items, int position) {
        if (items==null||items.size()==0){
            ToastUtil.showToast("BrandActivity data is null");
            return;
        }
        if (brands==null) {
            brands = new ArrayList<>();
        }
        brands.addAll(items);
        BrandActivity.position = position;
        Intent intent = new Intent(context, BrandActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_brand;
    }

    @Override
    protected void initWidget() {
        if (brands == null) {  //todo 应该显示错误页面
            ToastUtil.showToast(this.getClass().getName() + "is error");
            return;
        }
        Toolbar toolbar = findViewById(R.id.activity_brand_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.brand));

        //获取数据成功
        TabLayout tabLayout = findViewById(R.id.activity_brand_main_tab);
        viewPager = findViewById(R.id.activity_brand_viewpager);
        ArrayList<WeakReference<Fragment>> list = new ArrayList<WeakReference<Fragment>>();   //这里会有
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> args = new ArrayList<>();

        for (BrandImageBean bean : brands) {
            BrandItemRecyclerFragment fragment = new BrandItemRecyclerFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url", bean.getBrandID());
            fragment.setArguments(bundle);
            WeakReference<Fragment> weak = new WeakReference<Fragment>(fragment);
            list.add(weak);
//            list.add(fragment);
            titles.add(bean.getBrandName());
            args.add(bean.getBrandID());
        }
        viewPager.setAdapter(new MyBrandItemFragmentStatePagerAdapter(getSupportFragmentManager(), list, titles,args));//要使用getChild,不然会出现Fragment空白
        tabLayout.setupWithViewPager(viewPager);//这行代码会removeAllTabs,使用Adapter的getPageTitle方法设置tab
        viewPager.setCurrentItem(position);
    }

    @Override
    protected void initData() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_shop);
        View img = item.getActionView().findViewById(R.id.shop_cart);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrandActivity.this, MainActivity.class);
                intent.putExtra("startActivity", "shopCart");
                startActivity(intent);
                ToastUtil.showToast("点击购物车了");
            }
        });
        badge = new QBadgeView(App.context()).bindTarget(img).setBadgeNumber(ShopCartItemManagerUtil.getSize()).setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeTextSize(7, true).setBadgePadding(0, true);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        } else if (id == R.id.action_shop) {

        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onDestroy() {
//        ArrayList<Fragment> fragments = ((MyFragmentPagerAdapter) viewPager.getAdapter()).listfragment;
//        for (int i = 0; i < fragments.size(); i++) {
//            viewPager.getAdapter().destroyItem(viewPager, i, fragments.get(i));
//            viewPager.getAdapter().finishUpdate(viewPager);
//        }
        super.onDestroy();
        brands.clear();
        brands = null;
    }

}
