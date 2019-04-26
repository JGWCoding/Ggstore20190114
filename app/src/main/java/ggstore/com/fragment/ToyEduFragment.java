package ggstore.com.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import ggstore.com.R;
import ggstore.com.adapter.ToyEducationFragmentStatePagerAdapter;
import ggstore.com.base.BaseFragment;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.TDevice;

public class ToyEduFragment extends BaseFragment {

    public ViewPager viewPager;
    public int[] titles = {R.string.education_toy, R.string.baby_toy, R.string.book};
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_toy_education;
    }

    @Override
    protected void initWidget(View root) {
        TabLayout tabLayout = findView(R.id.main_tab);
        viewPager = findView(R.id.fragment_new_product_viewpager);
        ArrayList<WeakReference<Fragment>> list =new ArrayList<>();   //这里会有
        list.add(new WeakReference<Fragment>(new ToyEducationRecyclerFragment()));
        list.add(new WeakReference<Fragment>(new ToyEducationRecyclerFragment2()));
        list.add(new WeakReference<Fragment>(new ToyEducationRecyclerFragment3()));
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < this.titles.length; i++) {
            titles.add(getString(this.titles[i]));
        }
        viewPager.setAdapter(new ToyEducationFragmentStatePagerAdapter(getChildFragmentManager(), list,titles));//要使用getChild,不然会出现Fragment空白
        tabLayout.setupWithViewPager(viewPager);//这行代码会removeAllTabs,使用Adapter的getPageTitle方法设置tab
        final View line = findView(R.id.fragment_toy_education_line);
        line.post(new Runnable() {
            @Override
            public void run() {
                int margin = (int) ((TDevice.getScreenWidth() / 3 - line.getMeasuredWidth()) / 2);
                LogUtil.e(margin+" margin");
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) line.getLayoutParams();
                layoutParams.setMargins(margin,0 ,margin ,0 );
                line.setLayoutParams(layoutParams);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                line.setTranslationX(TDevice.getScreenWidth()/3*i);
//                line.setX(TDevice.getScreenWidth());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    @Override
    protected void finalize() throws Throwable {
        LogUtil.e("start gc");
        super.finalize();
    }
//    public static void setTabWidth(final TabLayout tabLayout, final int padding){
//        tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    //拿到tabLayout的mTabStrip属性
//                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);
//
//
//
//                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
//                        View tabView = mTabStrip.getChildAt(i);
//
//                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
//                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
//                        mTextViewField.setAccessible(true);
//
//                        TextView mTextView = (TextView) mTextViewField.get(tabView);
//
//                        tabView.setPadding(0, 0, 0, 0);
//
//                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
//                        int width = 0;
//                        width = mTextView.getWidth();
//                        if (width == 0) {
//                            mTextView.measure(0, 0);
//                            width = mTextView.getMeasuredWidth();
//                        }
//
//                        //设置tab左右间距 注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
//                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
//                        params.width = width ;
//                        params.leftMargin = padding;
//                        params.rightMargin = padding;
//                        tabView.setLayoutParams(params);
//
//                        tabView.invalidate();
//                    }
//
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//    }
}
