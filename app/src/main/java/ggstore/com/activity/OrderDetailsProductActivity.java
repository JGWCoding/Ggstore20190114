package ggstore.com.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ggstore.com.R;
import ggstore.com.base.BaseTitleActivity;
import ggstore.com.constant.Constant;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.TDevice;
import ggstore.com.utils.ToastUtil;

public class OrderDetailsProductActivity extends BaseTitleActivity {
    private ArrayList<ImageView> roundList = new ArrayList<ImageView>();
    private int lastPosition;
    @Override
    protected CharSequence getContentTitle() {
        return getString(R.string.product_details);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_order_details_product;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        if (Constant.orderNumber == null) {
            ToastUtil.showToast(R.string.order_number_empty);
            return;
        }
        ((TextView) findViewById(R.id.activity_order_detail_product_title)).setText(Constant.orderNumber.getName());
        ((TextView) findViewById(R.id.activity_order_detail_product_product_number)).setText(getString(R.string.product_number,Constant.orderNumber.getProduct_number()));
        ((TextView) findViewById(R.id.activity_order_detail_product_price)).setText(getString(R.string.product_price,Constant.orderNumber.getPrice() + ""));
        ((TextView) findViewById(R.id.activity_order_detail_product_number)).setText(Constant.orderNumber.getBuy_number()+"");
        ((TextView) findViewById(R.id.activity_order_detail_product_total)).setText(getString(R.string.product_price,(Constant.orderNumber.getBuy_number() * Constant.orderNumber.getPrice()) + ""));
        ViewPager viewPager = findViewById(R.id.activity_order_detail_product_viewpager);
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
        LinearLayout tips = findViewById(R.id.activity_order_detail_product_tips);
        ArrayList<ImageView> imageViews = new ArrayList<>();
        getImages(imageViews);  //todo 需要图片地址
        viewPager.setAdapter(new MyImagePagerAdapter(imageViews, viewPager));
        LogUtil.e(imageViews.size() + "");
        if (imageViews.size() < 1) {
            viewPager.setVisibility(View.GONE);
            tips.setVisibility(View.GONE);
        } else if (imageViews.size() == 1) {
            tips.setVisibility(View.GONE);
        } else {
//            for (int i = 0; i < imageViews.size(); i++) {
//                ImageView imageView = new ImageView(this);
//                imageView.setPadding(((int) TDevice.dp2px(5f)), ((int) TDevice.dp2px(5f)), ((int) TDevice.dp2px(5f)), ((int) TDevice.dp2px(5f)));
//                roundList.add(imageView);
//                if (i == 0) {
//                    imageView.setImageResource(R.drawable.red_round);
//                } else {
//                    imageView.setImageResource(R.drawable.black_round);
//                }
//                tips.addView(imageView);
//            }
            addRound(tips,imageViews);
        }
    }
    private void addRound(LinearLayout tips, ArrayList<ImageView> imageViews) {
        for (int i = 0; i < imageViews.size(); i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams layoutParams;
            if (i == 0) {
                layoutParams = new LinearLayout.LayoutParams((int) TDevice.dp2px(8f), (int) TDevice.dp2px(8f));
                imageView.setImageResource(R.drawable.selector_round);
            } else {
                layoutParams = new LinearLayout.LayoutParams((int) TDevice.dp2px(5f), (int) TDevice.dp2px(5f));
                imageView.setImageResource(R.drawable.black_round);
            }
            layoutParams.setMargins(((int) TDevice.dp2px(5f)), ((int) TDevice.dp2px(5f)), ((int) TDevice.dp2px(5f)), ((int) TDevice.dp2px(5f)));
            imageView.setLayoutParams(layoutParams);
            roundList.add(imageView);
            tips.addView(imageView);
        }
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
    }
    private void getImages(ArrayList<ImageView> imageViews) {
        String[] split = Constant.orderNumber.getImages().split(";");
        if (split != null && split.length >= 1) {
            for (int i = 0; i < split.length; i++) {
                if (!TextUtils.isEmpty(split[i])) {
                    ImageView imageView = new ImageView(this);
                    setImageFromNet(imageView, Constant.base_images_product_url +split[i]);
                    imageViews.add(imageView);
                }
            }
        }
    }

    @Override
    protected void initData() {
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
}
