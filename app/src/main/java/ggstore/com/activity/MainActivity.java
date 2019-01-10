package ggstore.com.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ggstore.com.R;
import ggstore.com.base.BaseActivity;
import ggstore.com.fragment.NewProductFragment;
import ggstore.com.fragment.ShopCartFragment;
import ggstore.com.fragment.ToyEduFragment;
import ggstore.com.utils.KeyboardUtil;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.SPUtils;
import ggstore.com.utils.ToastUtils;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private SearchView searchView;
    public Badge badge;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.new_product);
        addFragment(R.id.content_frame,new NewProductFragment());
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
                gotoShopCart();
                ToastUtils.showToast("点击购物车了");
            }
        });
        badge = new QBadgeView(this).bindTarget(img).setBadgeNumber(1).setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeTextSize(7, true).setBadgePadding(0, true);
        initSearchView(menu.findItem(R.id.action_search));
        return true;
    }

    public void gotoShopCart() {
        toolbar.setTitle(getString(R.string.shop_cart));
        addFragment(R.id.content_frame,new ShopCartFragment());
    }

    public void setShopingCartNumber(int number) {
        if (badge==null) return;
        badge.setBadgeNumber(number);
    }

    @SuppressLint("RestrictedApi")
    private void initSearchView(final MenuItem item) {
        searchView = (SearchView) item.getActionView();
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
                //todo 搜索结果
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //输入字符则回调此方法
                //当输入字符为空时，重新设置 item
                if (newText == null || newText.length() == 0) {
                    //由于实现了历史数据的功能，在此重新设置此 item才能实时生效
                    //initSearchView(item);
                }
                return false;
            }
        });
        //根据id-search_src_text获取TextView
        final SearchView.SearchAutoComplete searchViewOfKnowledge = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        //改变输入文字的颜色
        searchViewOfKnowledge.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
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
            //如果重写了 Adapter 的 getView 方法，可以不用实现 item 监听（实现了也没用），否则必须实现监听，不然会报错
            searchViewOfKnowledge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    searchView.setQuery(arr.get(position), true);
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        //searchview 的关闭监听
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
        KeyboardUtil.registerSoftInputChangedListener(this, new KeyboardUtil.OnSoftInputChangedListener() {
            @Override
            public void onSoftInputChanged(int height) {
                boolean visible = KeyboardUtil.isSoftInputVisible(MainActivity.this);
                LogUtil.e("keyboard is change, visible : "+visible);
                if (!visible){
                    searchView.onActionViewCollapsed();
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            getActionBar().setDisplayShowHomeEnabled(false);
            return true;
        }else if (id==R.id.action_shop){

        }
        getActionBar().setDisplayShowHomeEnabled(true);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {    //这里最好使用replaceFragment,因为add 里会出现问题(某部分Fragment不显示)
        int id = item.getItemId();
        if (id == R.id.new_product) {
            toolbar.setTitle(R.string.new_product);
            addFragment(R.id.content_frame,new NewProductFragment());
        } else if (id == R.id.today_discount) {

        } else if (id == R.id.mather_gravida) {

        } else if (id == R.id.bady_chdren) {

        } else if (id == R.id.toy_education) {
            toolbar.setTitle(R.string.toy_education);
            addFragment(R.id.content_frame,new ToyEduFragment());
        } else if (id == R.id.all_product) {

        } else if (id == R.id.brand) {

        } else if (id == R.id.order) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            }else if(titles.size()>6) {return 6;}
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
                    searchView.setQuery(titles.get(position),true);
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
