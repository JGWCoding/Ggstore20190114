package ggstore.com.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xml.sax.XMLReader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ggstore.com.R;
import ggstore.com.base.BaseActivity;
import ggstore.com.bean.ShopCartBean;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.ShopCartItemManagerUtil;
import ggstore.com.utils.ToastUtil;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class ProductDetailActivity extends BaseActivity {   //title应该是传过来的

    private Badge badge;

    @Override
    protected int getContentView() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initWidget() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        if (getIntent()!=null) {
            getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        }
//        addFragment(R.id.activity_product_detail_content_frame,new ProductDetailFragment());
        ViewPager viewPager = findViewById(R.id.act_pro_detail_viewpager);
        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackground(getResources().getDrawable(R.drawable.product));
            imageViews.add(imageView);
        }
        viewPager.setAdapter(new MyImagePagerAdapter(imageViews, viewPager));
        TextView price = findViewById(R.id.price);
        String content_price = "&nbsp<myfont size=\"15\" color=\"gray\"><del>HK$120</del></myfont>&nbsp&nbsp&nbsp&nbsp<myfont color=\"#148BA6\" size=\"25\">HK$100</myfont>";
        price.setText(Html.fromHtml(content_price,null,new HtmlTagHandler("myfont")));
        findViewById(R.id.activity_product_detail_add_shop_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 应该上传给服务器
                badge.setBadgeNumber(ShopCartItemManagerUtil.getSize()+1);
                ShopCartItemManagerUtil.insertShopCart(new ShopCartBean());
            }
        });
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
                Intent intent = new Intent(ProductDetailActivity.this,MainActivity.class);
                intent.putExtra("startActivity","shopCart");
                startActivity(intent);
                ToastUtil.showToast("点击购物车了");
            }
        });
        badge = new QBadgeView(this).bindTarget(img).setBadgeNumber(ShopCartItemManagerUtil.getSize()).setBadgeGravity(Gravity.END | Gravity.TOP)
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


    class HtmlTagHandler implements Html.TagHandler {
        // 自定义标签名称
        private String tagName; // 标签开始索引
        private int startIndex = 0; // 标签结束索引
        private int endIndex = 0; // 存放标签所有属性键值对
        final HashMap<String, String> attributes = new HashMap<>();

        public HtmlTagHandler(String tagName) {
            this.tagName = tagName;
        }

        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {  // 判断是否是当前需要的tag
            if (tag.equalsIgnoreCase(tagName)) {
                //解析所有属性值
                parseAttributes(xmlReader);
                if (opening) {
                    startHandleTag(tag, output, xmlReader);
                } else {
                    endEndHandleTag(tag, output, xmlReader);
                }
            }
        }

        public void startHandleTag(String tag, Editable output, XMLReader xmlReader) {
            startIndex = output.length();
        }

        public void endEndHandleTag(String tag, Editable output, XMLReader xmlReader) {
            endIndex = output.length(); // 获取对应的属性值
            String color = attributes.get("color");
            String size = attributes.get("size");
            LogUtil.e(size+color);
//            size = size.split("px")[0];
            if (!TextUtils.isEmpty(color)) {// 设置颜色
                output.setSpan(new ForegroundColorSpan(Color.parseColor(color)), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } // 设置字体大小
            if (!TextUtils.isEmpty(size)) {
                output.setSpan(new AbsoluteSizeSpan(Integer.parseInt(size),true), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        /**
         * 解析所有属性值
         * @param xmlReader
         */
        private void parseAttributes(final XMLReader xmlReader) {
            try {
                Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
                elementField.setAccessible(true);
                Object element = elementField.get(xmlReader);
                Field attsField = element.getClass().getDeclaredField("theAtts");
                attsField.setAccessible(true);
                Object atts = attsField.get(element);
                Field dataField = atts.getClass().getDeclaredField("data");
                dataField.setAccessible(true);
                String[] data = (String[]) dataField.get(atts);
                Field lengthField = atts.getClass().getDeclaredField("length");
                lengthField.setAccessible(true);
                int len = (Integer) lengthField.get(atts);
                for (int i = 0; i < len; i++) {
                    attributes.put(data[i * 5 + 1], data[i * 5 + 4]);
                }
            } catch (Exception e) {
            }
        }
    }
}
