package ggstore.com.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import ggstore.com.utils.ImageLoader;
import ggstore.com.utils.KeyboardUtil;

public abstract class BaseActivity extends AppCompatActivity {
    protected RequestManager mImageLoader;
    private boolean mIsDestroy;  //暂时没什么用
    private Fragment mFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());//强制交给子类实现布局
        initWidget();  //空实现,交给子类去实现自己的东西
        initData();   //空实现,交给子类
    }

    //控制Fragment显示和隐藏
    public void addFragment(int frameLayoutId, Fragment fragment) {  //应该对Fragment进行管理,使用一个实例Fragment

        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //已经有过该fragment(没有带参数的),
            if (fragment.getArguments() == null && getSupportFragmentManager().findFragmentByTag(fragment.getClass().getName()) != null) {
                if (mFragment != null && mFragment.getClass().getName().equals(fragment.getClass().getName())) { //添加的是同一个Fragment(不同对象) 不改变
                    return; // hide和add 为同一个Fragment不同对象
                }
                if (mFragment != null) {
                    Fragment fragmentByTag = getSupportFragmentManager().findFragmentByTag(fragment.getClass().getName());
                    fragment = fragmentByTag;   //必须进行赋值,不然以后隐藏 mFragment 会进行出错
                    transaction.hide(mFragment).show(fragmentByTag);
                } else {
                    transaction.add(frameLayoutId, fragment, fragment.getClass().getName());
                }
            } else {    //有参数的fragment不管有没有添加过相同的显示新的fragment
                if (mFragment != null) {
                    if (mFragment.getClass().getName().equals(fragment.getClass().getName())) { //添加的为同一个fragment
                        transaction.remove(mFragment);
                        transaction.add(frameLayoutId,fragment,fragment.getClass().getName());
                    } else {
                        transaction.hide(mFragment).add(frameLayoutId, fragment,fragment.getClass().getName());
                    }
                } else {
                    transaction.add(frameLayoutId, fragment, fragment.getClass().getName());
                }
            }
//            if (fragment.isAdded()) {
//                if (mFragment != null) {    //如果是同一个类不同对象 说明显示的是同一个Fragment
//                    transaction.hide(mFragment).show(fragment);
//                } else {
//                    transaction.show(fragment);
//                }
//                //已经有过该fragment(没有带参数的),
//            } else if (fragment.getArguments() == null && getSupportFragmentManager().findFragmentByTag(fragment.getClass().getName()) != null) {
//                if (mFragment != null) {
//                    transaction.hide(mFragment).show(getSupportFragmentManager().findFragmentByTag(fragment.getClass().getName()));
//                } else {
//                    transaction.add(frameLayoutId, fragment, fragment.getClass().getName());
//                }
//            } else {    //没有添加过的Fragment,或者是已经添加过的Fragment(有带参数了)
//
//            }
            mFragment = fragment;
            transaction.commitAllowingStateLoss();
        }
    }


    public void replaceFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
//            if (mFragment.getClass().getName().equals(fragment.getClass().getName())) {  //替换的为同一个Fragment
//                return;
//            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayoutId, fragment);
            transaction.commit();
            mFragment = fragment;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        KeyboardUtil.hideSoftInput(this);
    }

    protected abstract int getContentView();

    protected abstract void initWidget();

    protected abstract void initData();

    private synchronized RequestManager getImageLoader() {
        if (mImageLoader == null)
            mImageLoader = Glide.with(this);
        return mImageLoader;
    }

    public void setImageFromNet(ImageView imageView, String imageUrl) {
        ImageLoader.loadImage(getImageLoader(), imageView, imageUrl, 0);
    }

    /***
     * 从网络中加载数据
     *
     * @param imageView   imageView
     * @param imageUrl    图片地址
     * @param placeholder 图片地址为空时的资源
     */
    public void setImageFromNet(ImageView imageView, String imageUrl, int placeholder) {
        ImageLoader.loadImage(getImageLoader(), imageView, imageUrl, placeholder);
    }

    @Override
    protected void onDestroy() {
        mIsDestroy = true;
        super.onDestroy();
    }

    public boolean isDestroy() {
        return mIsDestroy;
    }
}
