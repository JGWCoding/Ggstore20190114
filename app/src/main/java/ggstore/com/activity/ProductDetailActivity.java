package ggstore.com.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ggstore.com.R;
import ggstore.com.base.BaseActivity;
import ggstore.com.bean.NewProductBean;
import ggstore.com.constant.Constant;
import ggstore.com.utils.KeyboardUtil;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.SpUtil;
import ggstore.com.utils.ShopCartItemManagerUtil;
import ggstore.com.utils.TDevice;
import ggstore.com.utils.ToastUtil;
import ggstore.com.view.MyNestedScrollView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class ProductDetailActivity extends BaseActivity {   //title应该是传过来的
    //TODO 中英文切换功能
    private Badge badge;
    private ArrayList<ImageView> roundList = new ArrayList<ImageView>();
    private int lastPosition;
    public static String product_title = "orderNumber";
    private LinearLayout tips;
    private SearchView searchView;
    String title;
    Drawable icon;
    private SearchView.SearchAutoComplete searchViewOfKnowledge;
    private Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initWidget() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (getIntent() != null) {
            getSupportActionBar().setTitle(getIntent().getStringExtra(product_title));
        }
        toolbar.setContentInsetStartWithNavigation(0);//减少icon到title的距离

        //todo 需要加载newProductBean,因为有的数据过来不完整
        if (Constant.newProductBean==null){
            return;
        }
        if (!TextUtils.isEmpty(Constant.newProductBean.getProductName_cn())) {
            ((TextView) findViewById(R.id.activity_product_detail_title)).setText(Constant.newProductBean.getProductName_cn());
        }
        if (!TextUtils.isEmpty(Constant.newProductBean.getProductCode())) {
            ((TextView) findViewById(R.id.activity_product_detail_number)).setText(getString(R.string.product_number,
                    Constant.newProductBean.getProductCode()));
        }
        if (!TextUtils.isEmpty(Constant.newProductBean.getProductDescription_cn())) {
//            ((WebView) findViewById(R.id.activity_product_detail_description)).
// setText(HtmlCompat.fromHtml(Constant.newProductBean.getProductDescription_cn(), HtmlCompat.FROM_HTML_MODE_COMPACT));
            LogUtil.e(Constant.newProductBean.getProductDescription_cn());
            WebView webView = (WebView) findViewById(R.id.activity_product_detail_description);
            String content = "<head><base href=\"" + Constant.base_url +
                    "\" /><base target=\"_blank\" /><style>body{color:#808080}</style></head>";
            if (Constant.newProductBean.getProductDescription_cn().contains("<img src=\"/shop/images")) {
                //todo 没有改变图片大小
                Constant.newProductBean.setProductDescription_cn(Constant.newProductBean.getProductDescription_cn()
                        .replace("<img src=\"/shop/images", "<img style=\"max-width:100%;height:auto\" src=\"/images"));
            }
            if (Constant.newProductBean.getProductDescription_cn().contains("<img ")) {
                Constant.newProductBean.setProductDescription_cn(Constant.newProductBean.getProductDescription_cn()
                        .replace("<img ","<img width=\"100%\" " ));
            }
            webView.loadDataWithBaseURL(null, content + Constant.newProductBean.getProductDescription_cn(),
                    "text/html", "utf-8", null);
            LogUtil.i(content+Constant.newProductBean.getProductDescription_cn());
        }
        ViewPager viewPager = findViewById(R.id.activity_product_detail_viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                selectorRound(i);
                lastPosition = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        tips = findViewById(R.id.activity_product_detail_tips);
        ArrayList<ImageView> imageViews = new ArrayList<>();
        getImages(imageViews);  //小心imageViews为空
        viewPager.setAdapter(new MyImagePagerAdapter(imageViews, viewPager));
        LogUtil.e(imageViews.size() + "");
        if (imageViews.size() < 1) {
            viewPager.setVisibility(View.GONE);
            tips.setVisibility(View.GONE);
        } else if (imageViews.size() == 1) {
            tips.setVisibility(View.GONE);
        } else {
            addRound(tips, imageViews);
        }
//        TextView price = findViewById(R.id.price);    //不设配android23以下的机型
//        String content_price = "&nbsp<myfont size=\"15\" color=\"gray\"><del>HK$" +Constant.newProductBean.getMarketPrice()+
//                "</del></myfont>&nbsp&nbsp&nbsp&nbsp<myfont color=\"#148BA6\" size=\"25\">HK$" +Constant.newProductBean.getUnitPrice()+
//                "</myfont>";
//        price.setText(Html.fromHtml(content_price, null, new HtmlTagHandler("myfont")));
        if (TextUtils.isEmpty(Constant.newProductBean.getUnitPrice())) {
            ((TextView) findViewById(R.id.activity_product_detail_new_price)).setVisibility(View.GONE);
        } else {
            ((TextView) findViewById(R.id.activity_product_detail_new_price)).setText(getString(R.string.product_price, Constant.newProductBean.getUnitPrice()));
        }
        TextView oldPrice = (TextView) findViewById(R.id.activity_product_detail_old_price);
        if (TextUtils.isEmpty(Constant.newProductBean.getMarketPrice())) {
            oldPrice.setVisibility(View.GONE);
        } else {
            oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);   //加横线效果
            oldPrice.setText(getString(R.string.product_price, Constant.newProductBean.getMarketPrice()));
        }
        findViewById(R.id.activity_product_detail_add_shop_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 应该上传给服务器
                NewProductBean bean = Constant.newProductBean;
                ShopCartItemManagerUtil.addShopCart(bean);
                badge.setBadgeNumber(ShopCartItemManagerUtil.getSize());
            }
        });
        ((MyNestedScrollView)findViewById(R.id.activity_product_detail_scroll)).setOnScrollChangeListener(
                new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (searchView.hasFocus()||searchViewOfKnowledge.hasFocus()){
                    if (KeyboardUtil.isSoftInputVisible(ProductDetailActivity.this)){
                        KeyboardUtil.hideSoftInput(ProductDetailActivity.this);
                    }
                    showToolbar();
                }
            }
        });
    }

    private void addRound(LinearLayout tips, ArrayList<ImageView> imageViews) {
        for (int i = 0; i < imageViews.size(); i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams;
            if (i == 0) {
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) TDevice.dp2px(8),(int) TDevice.dp2px(8));
//                layoutParams.width = (int) (TDevice.dp2px(8));
//                layoutParams.height = (int) (TDevice.dp2px(8));
//                imageView.setLayoutParams(layoutParams);
                layoutParams = new LinearLayout.LayoutParams((int) TDevice.dp2px(8f), (int) TDevice.dp2px(8f));
                imageView.setImageResource(R.drawable.selector_round);
            } else {
                layoutParams = new LinearLayout.LayoutParams((int) TDevice.dp2px(5f), (int) TDevice.dp2px(5f));
                imageView.setImageResource(R.drawable.black_round);
            }
            layoutParams.setMargins(((int) TDevice.dp2px(5f)), ((int) TDevice.dp2px(5f)), ((int) TDevice.dp2px(5f)), ((int) TDevice.dp2px(5f)));
//            imageView.setPadding(((int) TDevice.dp2px(5f)), ((int) TDevice.dp2px(5f)), ((int) TDevice.dp2px(5f)), ((int) TDevice.dp2px(5f)));
            imageView.setLayoutParams(layoutParams);
            roundList.add(imageView);
            tips.addView(imageView);
        }
//        layoutParams.width = (int) TDevice.dp2px(8f);
//        layoutParams.height = (int) TDevice.dp2px(8f);
//        tips.getChildAt(0).setLayoutParams(layoutParams);
//        tips.requestLayout();
    }

    private void selectorRound(int selector) {
        ImageView imageView = roundList.get(lastPosition);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = (int) TDevice.dp2px(5f);
        layoutParams.height = (int) TDevice.dp2px(5f);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.drawable.black_round);
        imageView = roundList.get(selector);
        LogUtil.i(layoutParams.width + "=width:height=" + layoutParams.height);
        layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.width = (int) TDevice.dp2px(8f);
        layoutParams.height = (int) TDevice.dp2px(8f);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.drawable.selector_round);
//        ImageView imageView = roundList.get(lastPosition);
//        imageView.setImageResource(R.drawable.black_round);
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
//        layoutParams.width = (int) TDevice.dp2px(5);
//        layoutParams.height = (int) TDevice.dp2px(5);
//        imageView.setLayoutParams(layoutParams);
//
//        imageView = roundList.get(selector);
//        imageView.setImageResource(R.drawable.red_round);
//         layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
//        layoutParams.width = (int) TDevice.dp2px(8);
//        layoutParams.height = (int) TDevice.dp2px(8);
//        imageView.setLayoutParams(layoutParams);
//        findViewById(R.id.activity_order_detail_product_tips).requestLayout();
    }

    private void getImages(ArrayList<ImageView> imageViews) {
        NewProductBean newProductBean = Constant.newProductBean;
        if (Constant.newProductBean == null) {
            ToastUtil.showToast(R.string.product_bean_empty);
            return;
        }
        if (!TextUtils.isEmpty(newProductBean.getPictureL())) {
            ImageView imageView = new ImageView(this);
            setImageFromNet(imageView, Constant.base_images_product_url + newProductBean.getPictureL());
            imageViews.add(imageView);
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS1())) {
            ImageView imageView = new ImageView(this);
            setImageFromNet(imageView, Constant.base_images_product_url + newProductBean.getPictureS1());
            imageViews.add(imageView);
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS2())) {
            ImageView imageView = new ImageView(this);
            setImageFromNet(imageView, Constant.base_images_product_url + newProductBean.getPictureS2());
            imageViews.add(imageView);
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS3())) {
            ImageView imageView = new ImageView(this);
            setImageFromNet(imageView, Constant.base_images_product_url + newProductBean.getPictureS3());
            imageViews.add(imageView);
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS4())) {
            ImageView imageView = new ImageView(this);
            setImageFromNet(imageView, Constant.base_images_product_url + newProductBean.getPictureS4());
            imageViews.add(imageView);
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS5())) {
            ImageView imageView = new ImageView(this);
            setImageFromNet(imageView, Constant.base_images_product_url + newProductBean.getPictureS5());
            imageViews.add(imageView);
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS6())) {
            ImageView imageView = new ImageView(this);
            setImageFromNet(imageView, Constant.base_images_product_url + newProductBean.getPictureS6());
            imageViews.add(imageView);
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS7())) {
            ImageView imageView = new ImageView(this);
            setImageFromNet(imageView, Constant.base_images_product_url + newProductBean.getPictureS7());
            imageViews.add(imageView);
        }
        if (!TextUtils.isEmpty((CharSequence) newProductBean.getPictureS8())) {
            ImageView imageView = new ImageView(this);
            setImageFromNet(imageView, Constant.base_images_product_url + newProductBean.getPictureS8());
            imageViews.add(imageView);
        }
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
                Intent intent = new Intent(ProductDetailActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.start_activity_shop_cart, "shopCart");
                startActivity(intent);
//                ToastUtil.showToast("点击购物车了");
            }
        });
        badge = new QBadgeView(this).bindTarget(img).setBadgeNumber(
                ShopCartItemManagerUtil.getSize()).setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeTextSize(12, true).setBadgePadding(0, true);
        initSearchView(menu.findItem(R.id.action_search));
//        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }

    @SuppressLint("RestrictedApi")
    private void initSearchView(final MenuItem item) {  //开始换
        searchView = (SearchView) item.getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint(getString(R.string.search_hint));
        //改变默认的搜索图标
        ((ImageView) searchView.findViewById(R.id.search_button)).setImageResource(R.drawable.search);
        //搜索监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    return false;
                }
                //在输入法按下搜索或者回车时，会调用次方法，在这里可以作保存历史记录的操作，我这里用了 sharepreference 保存
                SpUtil.getSP(ProductDetailActivity.this, SpUtil.knowledge_history).edit().putString(query, query).commit();
                showToolbar();
                searchFragment(query);
                return true;
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
        searchViewOfKnowledge.setTextColor(ContextCompat.getColor(this, R.color.white));
        searchViewOfKnowledge.setEllipsize(TextUtils.TruncateAt.END);
        searchViewOfKnowledge.setCompoundDrawables(getResources().getDrawable(R.drawable.search), null, null, null);
        showHistory();
        //searchview 的关闭监听
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                showToolbar();
                return false;
            }
        });
        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hiddenToolbar();
                } else {
                    showToolbar();
                }
                LogUtil.e(hasFocus + "");
            }
        };
        searchView.setOnQueryTextFocusChangeListener(onFocusChangeListener);
        searchViewOfKnowledge.setOnFocusChangeListener(onFocusChangeListener);
    }

    private void searchFragment(String query) {
        Intent intent = new Intent(ProductDetailActivity.this,MainActivity.class);
        intent.putExtra(MainActivity.start_activity_search_fragment,query );
        startActivity(intent);
    }

    @SuppressLint("RestrictedApi")
    private void showHistory() {
        try {
            //取出历史数据，你可以利用其他方式
            final List<String> arr = new ArrayList<>();
            Map<String, ?> map = SpUtil.getSP(ProductDetailActivity.this, SpUtil.knowledge_history).getAll();
            for (String key : map.keySet()) {
                arr.add(map.get(key).toString());
            }
            //显示历史数据列表
            searchViewOfKnowledge.setThreshold(0);
            //历史数据列表的 adapter,必须继承 ArrayAdater 或实现 filterable接口
            HistoryAdapter adapter = new HistoryAdapter(this, R.layout.item_history, arr, searchView);
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
        searchViewOfKnowledge.setCompoundDrawables(getResources().getDrawable(R.drawable.search), null, null, null);
        searchViewOfKnowledge.invalidate();
    }


    private void showToolbar() {
        searchView.onActionViewCollapsed();
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

        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        getActionBar().setDisplayShowHomeEnabled(true);
        return super.onOptionsItemSelected(item);
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
                    SpUtil.getSP(ProductDetailActivity.this, SpUtil.knowledge_history).edit().
                            remove(titles.get(position)).commit();
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
                        //如果接收到的文字为空，则不作比较，直接返回未筛选前的完整数据
                    if (constraint == null || constraint.length() == 0) {
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
    class MyImagePagerAdapter extends PagerAdapter {
        private List<ImageView> imageList;
        private ViewPager viewPager;

        public MyImagePagerAdapter(List<ImageView> imageList, ViewPager viewPager) {
            this.imageList = imageList;
            this.viewPager = viewPager;
        }

        /**
         * 返回的int的值, 会作为ViewPager的总长度来使用.
         */
        @Override
        public int getCount() {
            if (imageList == null) {
                return 0;
            }
            return imageList.size();//Integer.MAX_VALUE伪无限循环
        }

        /**
         * 判断是否使用缓存, 如果返回的是true, 使用缓存. 不去调用instantiateItem方法创建一个新的对象
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 初始化一个条目
         * position 就是当前需要加载条目的索引
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 把position对应位置的ImageView添加到ViewPager中
            ImageView iv = imageList.get(position % imageList.size());
            viewPager.addView(iv);
            // 把当前添加ImageView返回回去.
            return iv;
        }

        /**
         * 销毁一个条目
         * position 就是当前需要被销毁的条目的索引
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // 把ImageView从ViewPager中移除掉
            viewPager.removeView(imageList.get(position % imageList.size()));
        }
    }


//    class HtmlTagHandler implements Html.TagHandler {
//        // 自定义标签名称
//        private String tagName; // 标签开始索引
//        private int startIndex = 0; // 标签结束索引
//        private int endIndex = 0; // 存放标签所有属性键值对
//        final HashMap<String, String> attributes = new HashMap<>();
//
//        public HtmlTagHandler(String tagName) {
//            this.tagName = tagName;
//        }
//
//        @Override
//        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {  // 判断是否是当前需要的tag
//            if (tag.equalsIgnoreCase(tagName)) {
//                //解析所有属性值
//                parseAttributes(xmlReader);
//                if (opening) {
//                    startHandleTag(tag, output, xmlReader);
//                } else {
//                    endEndHandleTag(tag, output, xmlReader);
//                }
//            }
//        }
//
//        public void startHandleTag(String tag, Editable output, XMLReader xmlReader) {
//            startIndex = output.length();
//        }
//
//        public void endEndHandleTag(String tag, Editable output, XMLReader xmlReader) {
//            endIndex = output.length(); // 获取对应的属性值
//            String color = attributes.get("color");
//            String size = attributes.get("size");
//            LogUtil.e(size + color);
////            size = size.split("px")[0];
//            if (!TextUtils.isEmpty(color)) {// 设置颜色
//                output.setSpan(new ForegroundColorSpan(Color.parseColor(color)), startIndex, endIndex,
// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            } // 设置字体大小
//            if (!TextUtils.isEmpty(size)) {
//                output.setSpan(new AbsoluteSizeSpan(Integer.parseInt(size), true), startIndex, endIndex,
// Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//        }
//
//        /**
//         * 解析所有属性值
//         *
//         * @param xmlReader
//         */
//        private void parseAttributes(final XMLReader xmlReader) {
//            try {
//                Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
//                elementField.setAccessible(true);
//                Object element = elementField.get(xmlReader);
//                Field attsField = element.getClass().getDeclaredField("theAtts");
//                attsField.setAccessible(true);
//                Object atts = attsField.get(element);
//                Field dataField = atts.getClass().getDeclaredField("data");
//                dataField.setAccessible(true);
//                String[] data = (String[]) dataField.get(atts);
//                Field lengthField = atts.getClass().getDeclaredField("length");
//                lengthField.setAccessible(true);
//                int len = (Integer) lengthField.get(atts);
//                for (int i = 0; i < len; i++) {
//                    attributes.put(data[i * 5 + 1], data[i * 5 + 4]);
//                }
//            } catch (Exception e) {
//            }
//        }
//    }
}
