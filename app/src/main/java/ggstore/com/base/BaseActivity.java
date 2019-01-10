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

public abstract class BaseActivity extends AppCompatActivity {
    protected RequestManager mImageLoader;
    private boolean mIsDestroy;  //暂时没什么用
    private final String mPackageName = this.getClass().getName();
    private Fragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(getContentView());//强制交给子类实现布局
            initWidget();  //空实现,交给子类去实现自己的东西
            initData();   //空实现,交给子类
    }

    //控制Fragment显示和隐藏
    public void addFragment(int frameLayoutId,Fragment fragment) {  //应该对Fragment进行管理,使用一个实例Fragment
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (fragment.isAdded()) {
                if (mFragment != null) {    //如果是同一个类不同对象 说明显示的是同一个Fragment
                    if (mFragment == fragment || mFragment.getClass().getName().equals(fragment.getClass().getName())) {
                        return;
                    }else {
                        transaction.hide(mFragment).show(fragment);
                    }
                } else {
                    transaction.show(fragment);
                }
            } else {
                if (mFragment!=null&&mFragment.getClass().getName().equals(fragment.getClass().getName())) { //添加的是同一个Fragment(不同对象) 不改变
                    return; //因为这里会出现空白页面不知道什么原因, hide和add 为同一个Fragment不同对象
                }
                if (mFragment != null) {
                    transaction.hide(mFragment).add(frameLayoutId, fragment);
                } else {
                    transaction.add(frameLayoutId, fragment);
                }
            }
            mFragment = fragment;
            transaction.commit();
        }
    }

    public void replaceFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            if (mFragment.getClass().getName().equals(fragment.getClass().getName())){  //替换的为同一个Fragment
                return;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(frameLayoutId, fragment);
            transaction.commit();
            mFragment = fragment;
        }
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
