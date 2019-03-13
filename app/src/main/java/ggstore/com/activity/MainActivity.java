package ggstore.com.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ggstore.com.BuildConfig;
import ggstore.com.R;
import ggstore.com.base.BaseActivity;
import ggstore.com.fragment.AllProductFragment;
import ggstore.com.fragment.BabyChildrenFragment;
import ggstore.com.fragment.BrandFragment;
import ggstore.com.fragment.GravidaMotherFragment;
import ggstore.com.fragment.MyOrderFragment;
import ggstore.com.fragment.NewProductFragment;
import ggstore.com.fragment.OrderNumberFragment;
import ggstore.com.fragment.SearchFragment;
import ggstore.com.fragment.ShopCartFragment;
import ggstore.com.fragment.ShopCartListFragment;
import ggstore.com.fragment.TodayDiscountFragment;
import ggstore.com.fragment.ToyEduFragment;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.ReflectUtils;
import ggstore.com.utils.SPUtils;
import ggstore.com.utils.ShopCartItemManagerUtil;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //TODO 添加购物车功能  badge应该进行BaseActivity封装
    public Toolbar toolbar;
    private SearchView searchView;
    public Badge badge;
    private SearchView.SearchAutoComplete searchViewOfKnowledge;
    private DrawerLayout drawer;
    public NavigationView navigationView;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);       // {@# onCreateOptionsMenu
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                ((TextView) findViewById(R.id.activity_main_header_name)).setText(BuildConfig.date);
            }
        });
        badge = new QBadgeView(this);
    }


    @Override
    protected void initData() {
        String paypal = getIntent().getStringExtra("paypal");
        Intent intent = getIntent();
        String startActivity = intent.getStringExtra("startActivity");

        if (paypal != null && paypal.equals("success")) {
            orderNumberfragment();
        } else if (startActivity != null && startActivity.equals("shopCart")) {
            shopCartFragment();
        } else {
            newProductFragment();

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
//            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_shop);
        View img = item.getActionView().findViewById(R.id.shop_cart);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment shopCartFragment = getSupportFragmentManager().findFragmentByTag(ShopCartFragment.class.getName());
                if (shopCartFragment != null && shopCartFragment.isVisible()) {
                    //购物车页面点击购物车没有用
                } else {
                    shopCartFragment();
//                    ToastUtil.showToast("点击购物车了");
                }
            }
        });
        badge = badge.bindTarget(img).setBadgeNumber(ShopCartItemManagerUtil.getSize()).setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeTextSize(7, true).setBadgePadding(0, true);
        initSearchView(menu.findItem(R.id.action_search));
        return true;
    }

    public void addToShopCart() {
        //TODO 添加到购物车
    }

    public void setShopingCartNumber(int number) {
        if (badge == null) return;
        badge.setBadgeNumber(number);
    }

    @Override
    protected void onResume() {
        super.onResume();
        badge.setBadgeNumber(ShopCartItemManagerUtil.getSize());
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e("start gc");
        ToyEduFragment fragment = (ToyEduFragment) getSupportFragmentManager().findFragmentByTag(ToyEduFragment.class.getName().toString());
        if (fragment != null && fragment.isAdded() && !fragment.isVisible()) {  //ToyEduFragment 容易造成内存泄漏,viewpager不释放Fragment
            ArrayList<Fragment> listfragment = ((ToyEduFragment.MyFragmentPagerAdapter) fragment.viewPager.getAdapter()).listfragment;
            for (int i = 0; i < listfragment.size(); i++) {
                fragment.viewPager.getAdapter().destroyItem(fragment.viewPager, i,listfragment.get(i));
            }
            getSupportFragmentManager().beginTransaction().remove(fragment).commitNowAllowingStateLoss();
            LogUtil.e("start gc" + fragment.getClass().getName());
        }
//        System.gc();    //进行内存回收,保障了内存不会太大
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        LogUtil.e("start gc");
    }

    String title;
    Drawable icon;

    @SuppressLint("RestrictedApi")
    private void initSearchView(final MenuItem item) {  //开始换


        searchView = (SearchView) item.getActionView();

        Drawable icon = ReflectUtils.reflect(searchView).field("mSearchHintIcon").get();
        icon = getResources().getDrawable(R.drawable.search);   //设置icon没用
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint(getString(R.string.search_hint));
        //改变默认的搜索图标
        ((ImageView) searchView.findViewById(R.id.search_button)).setImageResource(R.drawable.search);
        //搜索监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //在输入法按下搜索或者回车时，会调用次方法，在这里可以作保存历史记录的操作，我这里用了 sharepreference 保存
                SPUtils.getSP(MainActivity.this, "knowledgeHistory").edit()
                        .putString(query, query).commit();
                searchView.onActionViewCollapsed();
                searchFragment(query);
                showToolbar();
                return true;   //false 代表关闭键盘
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //输入字符则回调此方法
                //当输入字符为空时，重新设置 item
                if (newText == null || newText.length() == 0) {
                    //由于实现了历史数据的功能，在此重新设置此 item才能实时生效
//                    initSearchView(item);
                    showHistory();
                }
                return false;
            }
        });
        //根据id-search_src_text获取TextView
        searchViewOfKnowledge = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        //改变输入文字的颜色
        searchViewOfKnowledge.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
        searchViewOfKnowledge.setCompoundDrawables(this.getResources().getDrawable(R.drawable.search), null, null, null);
        showHistory();
        //searchview 的关闭监听
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                showToolbar();
                return false;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hiddenToolbar();
                } else {
                    searchView.onActionViewCollapsed();
                    showToolbar();
                }
                LogUtil.e(hasFocus + "");
            }
        });
    }

    private void showHistory() {
        try {
            //取出历史数据，你可以利用其他方式
            final List<String> arr = new ArrayList<>();
            Map<String, ?> map = SPUtils.getSP(MainActivity.this, "knowledgeHistory").getAll();
            for (String key : map.keySet()) {
                arr.add(map.get(key).toString());
            }
            //显示历史数据列表
            searchViewOfKnowledge.setThreshold(0);
            //历史数据列表的 adapter,必须继承 ArrayAdater 或实现 filterable接口
            HistoryAdapter adapter = new HistoryAdapter(MainActivity.this, R.layout.item_history, arr, searchView);
            //设置 adapter
            searchViewOfKnowledge.setAdapter(adapter);
            //如果重写了 Adapter 的 getView 方法，可以不用实现 item 监听（实现了也没用），否则必须实现监听，不然会报错  --  这个实现没用
            searchViewOfKnowledge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    searchView.setQuery(arr.get(position), true);
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void hiddenToolbar() {
        LogUtil.e("隐藏toolbar里面的title和icon");
        title = toolbar.getTitle() == null ? "" : toolbar.getTitle().toString();  //android5.0不会隐藏此title
        icon = toolbar.getNavigationIcon();
        toolbar.setTitle(null);// this.removeView(this.mTitleTextView);  this.mHiddenViews.remove(this.mTitleTextView);
        toolbar.setNavigationIcon(null);
        searchViewOfKnowledge.setCompoundDrawables(this.getResources().getDrawable(R.drawable.search), null, null, null);
        searchViewOfKnowledge.invalidate();
    }


    private void showToolbar() {
        if (TextUtils.isEmpty(toolbar.getTitle())) {
            LogUtil.e("title和icon显示");
            toolbar.setTitle(title);
        } else {
            toolbar.setTitle(toolbar.getTitle());
        }
        if (icon != null) {
            toolbar.setNavigationIcon(icon);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            getActionBar().setDisplayShowHomeEnabled(false);
            return true;
        } else if (id == R.id.action_shop) {

        }
        getActionBar().setDisplayShowHomeEnabled(true);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {    //这里最好使用replaceFragment,因为add 里会出现问题(某部分Fragment不显示)
        int id = item.getItemId();
        showSearchAndShopCart();
        if (id == R.id.new_product) {
            newProductFragment();
        } else if (id == R.id.today_discount) {
            todayDiscount();
        } else if (id == R.id.gravida_mother) {
            gravidaMotherFragment();
        } else if (id == R.id.bady_children) {
            badyChildrenFragment();
        } else if (id == R.id.toy_education) {
            toyEduFragment();
        } else if (id == R.id.all_product) {
            allProductFragment();
        } else if (id == R.id.brand) {
            brandFragment();
        } else if (id == R.id.my_order) {
            myOrderfragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void searchFragment(String searchKeyword) {
        if (TextUtils.isEmpty(searchKeyword)) {
            return;
        }
        title = searchKeyword;
        toolbar.setTitle(searchKeyword);
        replaceFragment(R.id.activity_main_content_frame, new SearchFragment());
    }

    private void brandFragment() {
        toolbar.setTitle(R.string.brand);
        addFragment(R.id.activity_main_content_frame, new BrandFragment());
    }

    private void allProductFragment() {
        toolbar.setTitle(R.string.all_product);
        addFragment(R.id.activity_main_content_frame, new AllProductFragment());
    }

    private void badyChildrenFragment() {
        toolbar.setTitle(R.string.bady_children);
        addFragment(R.id.activity_main_content_frame, new BabyChildrenFragment());
    }

    private void gravidaMotherFragment() {
        toolbar.setTitle(R.string.gravida_mother);
        addFragment(R.id.activity_main_content_frame, new GravidaMotherFragment());
    }

    public void newProductFragment() {
        navigationView.setCheckedItem(R.id.new_product);
        toolbar.setTitle(R.string.new_product);
        addFragment(R.id.activity_main_content_frame, new NewProductFragment());
    }

    private void todayDiscount() {
        toolbar.setTitle(R.string.today_discount);
        addFragment(R.id.activity_main_content_frame, new TodayDiscountFragment());
    }


    public void toyEduFragment() {
        toolbar.setTitle(R.string.toy_education);
        addFragment(R.id.activity_main_content_frame, new ToyEduFragment());
    }

    public void shopCartFragment() {
        toolbar.setTitle(R.string.shop_cart);
        ShopCartFragment shopCartFragment = new ShopCartFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEmptyShopCart", false); //这个参数主要用来规避show,hide Fragment(没参数不会改变,购物车应该时时会变数量)
        shopCartFragment.setArguments(bundle);
        addFragment(R.id.activity_main_content_frame, shopCartFragment);
        setNavigationViewCheckedFalse();
    }

    public void setNavigationViewCheckedFalse() {
        MenuItem checkedItem = navigationView.getCheckedItem();
        if (checkedItem != null) {
            checkedItem.setChecked(false);
        }
    }

    public void emptyShopCartFragment() {
        toolbar.setTitle(R.string.shop_cart);
        ShopCartFragment shopCartFragment = new ShopCartFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isEmptyShopCart", true);
        shopCartFragment.setArguments(bundle);
        addFragment(R.id.activity_main_content_frame, shopCartFragment);
        setNavigationViewCheckedFalse();
    }

    public void shopCartListFragment() {
        toolbar.setTitle(R.string.shoping_list);
        addFragment(R.id.activity_main_content_frame, new ShopCartListFragment());
        setNavigationViewCheckedFalse();
    }


    public void myOrderfragment() {
        addFragment(R.id.activity_main_content_frame, new MyOrderFragment());
        toolbar.setTitle(getString(R.string.my_order));
        hideSearchAndShopCart();
    }

    private void hideSearchAndShopCart() {
        toolbar.getMenu().findItem(R.id.action_search).getActionView().setVisibility(View.INVISIBLE);
        toolbar.getMenu().findItem(R.id.action_shop).getActionView().setVisibility(View.INVISIBLE);
    }

    private void showSearchAndShopCart() {
        toolbar.getMenu().findItem(R.id.action_search).getActionView().setVisibility(View.VISIBLE);
        toolbar.getMenu().findItem(R.id.action_shop).getActionView().setVisibility(View.VISIBLE);
    }

    public void orderNumberfragment() {
        addFragment(R.id.activity_main_content_frame, new OrderNumberFragment());
        toolbar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                toolbar.setTitle(getString(R.string.shop_cart));    // 不显示购物车,显示最新产品
                toolbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    private class HistoryAdapter extends ArrayAdapter {
        Context context;
        List<String> titles;
        int resourceId;
        SearchView searchView;

        public HistoryAdapter(Context context, int resourceId, List<String> titles, SearchView searchView) {
            super(context, resourceId, titles);
            this.context = context;
            this.titles = titles;
            this.resourceId = resourceId;
            this.searchView = searchView;
        }

        @Override
        public int getCount() {
            if (titles == null) {
                return 0;
            } else if (titles.size() > 6) {
                return 6;
            }
            return titles.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(resourceId, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.titleTv)).setText(titles.get(position));
            convertView.findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SPUtils.getSP(MainActivity.this, "knowledgeHistory").edit().remove(titles.get(position)).commit();
                    titles.remove(position);
                    notifyDataSetChanged();
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchView.setQuery(titles.get(position), true);
                }
            });
            return convertView;
        }

        //用于保存原始数据
        private List<String> mOriginalValues;

        @NonNull
        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    titles = (List<String>) results.values; // 得到筛选后的列表结果
                    notifyDataSetChanged();  // 刷新数据
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    List<String> filteredArrList = new ArrayList<String>();
                    if (mOriginalValues == null) {
                        //保存一份未筛选前的完整数据
                        mOriginalValues = new ArrayList<String>(titles);
                    }
                    if (constraint == null || constraint.length() == 0) {                //如果接收到的文字为空，则不作比较，直接返回未筛选前的完整数据
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        //遍历原始数据，与接收到的文字作比较，得到筛选结果
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String data = mOriginalValues.get(i);
                            if (data.toLowerCase().startsWith(constraint.toString())) {
                                filteredArrList.add(data);
                            }
                        }
                        //返回得到的筛选列表
                        results.count = filteredArrList.size();
                        results.values = filteredArrList;
                    }
                    return results;
                }
            };
            return filter;
        }

    }
}
