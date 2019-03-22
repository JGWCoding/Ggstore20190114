package ggstore.com.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.io.Serializable;

import ggstore.com.activity.LoginActivity;
import ggstore.com.utils.AppOperator;
import ggstore.com.utils.ImageLoader;
import ggstore.com.utils.KeyboardUtil;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.ToastUtil;

/**
 * Fragment基础类
 */

@SuppressWarnings("WeakerAccess")   //禁止“Access can be private”的警告
public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected View mRoot;
    protected Bundle mBundle;
    private RequestManager mImgLoader;
    protected LayoutInflater mInflater;
    private Fragment mFragment;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initBundle(mBundle);    //拿到传过来的数据参数
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRoot != null) {        //复用自己本身的View,如果没有就进行重新加载
            ViewGroup parent = (ViewGroup) mRoot.getParent();
            if (parent != null)
                parent.removeView(mRoot);
        } else {
            mRoot = inflater.inflate(getLayoutId(), container, false);
            mInflater = inflater;
            // Do something
            onBindViewBefore(mRoot);
            // Bind view
            // Get savedInstanceState
            if (savedInstanceState != null)
                onRestartInstance(savedInstanceState);
            // Init  --> 初始化
            initWidget(mRoot);
            initData();
        }
        return mRoot;
    }

    //控制Fragment显示和隐藏
    public void addFragment(int frameLayoutId, Fragment fragment) {  //应该对Fragment进行管理,使用一个实例Fragment

        if (fragment != null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            //已经有过该fragment(没有带参数的),
            if (fragment.getArguments() == null && getChildFragmentManager().findFragmentByTag(fragment.getClass().getName()) != null) {
                if (mFragment != null && mFragment.getClass().getName().equals(fragment.getClass().getName())) { //添加的是同一个Fragment(不同对象) 不改变
                    LogUtil.i("添加了同一个Fragment");
                    return; // hide和add 为同一个Fragment不同对象
                }
                if (mFragment != null) {
                    Fragment fragmentByTag = getChildFragmentManager().findFragmentByTag(fragment.getClass().getName());
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
            mFragment = fragment;
            transaction.commitAllowingStateLoss();
        }
    }
//    //控制Fragment显示和隐藏
//    public void addFragment(int frameLayoutId,Fragment fragment) {  //应该对Fragment进行管理,使用一个实例Fragment
//        if (fragment != null) {
//            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//            if (fragment.isAdded()) {
//                if (mFragment != null) {    //如果是同一个类不同对象 说明显示的是同一个Fragment
//                    if (mFragment == fragment || mFragment.getClass().getName().equals(fragment.getClass().getName())) {
//                        return;
//                    }else {
//                        transaction.hide(mFragment).show(fragment);
//                    }
//                } else {
//                    transaction.show(fragment);
//                }
//            } else {
//                if (mFragment!=null&&mFragment.getClass().getName().equals(fragment.getClass().getName())) { //添加的是同一个Fragment(不同对象) 不改变
//                    return; //因为这里会出现空白页面不知道什么原因, hide和add 为同一个Fragment不同对象
//                }
//                if (mFragment != null) {
//                    transaction.hide(mFragment).add(frameLayoutId, fragment);
//                } else {
//                    transaction.add(frameLayoutId, fragment,fragment.getClass().getName());
//                }
//            }
//            mFragment = fragment;
//            transaction.commit();
//        }
//    }

    public void replaceFragment(int frameLayoutId, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(frameLayoutId, fragment,fragment.getClass().getName());
            transaction.commit();
            mFragment = fragment;
        }
    }
    protected void onBindViewBefore(View root) {
        // 在View生成前就进行一系列设置
    }

    public void startLoginActivity() {
       startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Override
    public void onPause() {
        super.onPause();
        KeyboardUtil.hideSoftInput(getActivity());
        ToastUtil.closeToast();
    }

    @Override
    public void onDestroy() {
        AppOperator.removeAll();
        super.onDestroy();
        mImgLoader = null;
        mBundle = null;
    }

    protected abstract int getLayoutId(); //强制子类实现 给予布局

    protected void initBundle(Bundle bundle) {

    }

    protected void initWidget(View root) {

    }

    protected void initData() {

    }

    protected <T extends View> T findView(int viewId) {
        return (T) mRoot.findViewById(viewId);
    }

    protected <T extends Serializable> T getBundleSerializable(String key) {
        if (mBundle == null) {
            return null;
        }
        return (T) mBundle.getSerializable(key);
    }

    /**
     * 获取一个图片加载管理器
     *
     * @return RequestManager
     */
    private synchronized RequestManager getImgLoader() {
        if (mImgLoader == null)
            mImgLoader = Glide.with(this);
        return mImgLoader;
    }

    /***
     * 从网络中加载数据
     *
     * @param viewId   view的id
     * @param imageUrl 图片地址
     */
    protected void setImageFromNet(int viewId, String imageUrl) {
        setImageFromNet(viewId, imageUrl, 0);
    }

    /***
     * 从网络中加载数据
     *
     * @param viewId      view的id
     * @param imageUrl    图片地址
     * @param placeholder 图片地址为空时的资源
     */
    protected void setImageFromNet(int viewId, String imageUrl, int placeholder) {
        ImageView imageView = findView(viewId);
        setImageFromNet(imageView, imageUrl, placeholder);
    }

    /***
     * 从网络中加载数据
     *
     * @param imageView imageView
     * @param imageUrl  图片地址
     */
    protected void setImageFromNet(ImageView imageView, String imageUrl) {
        setImageFromNet(imageView, imageUrl, 0);
    }

    /***
     * 从网络中加载数据
     *
     * @param imageView   imageView
     * @param imageUrl    图片地址
     * @param placeholder 图片地址为空时的资源
     */
    protected void setImageFromNet(ImageView imageView, String imageUrl, int placeholder) {
        ImageLoader.loadImage(getImgLoader(), imageView, imageUrl, placeholder);
    }


    protected void setText(int viewId, String text) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            return;
        }
        textView.setText(text);
    }

    protected void setText(int viewId, String text, String emptyTip) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            textView.setText(emptyTip);
            return;
        }
        textView.setText(text);
    }

    protected void setTextEmptyGone(int viewId, String text) {
        TextView textView = findView(viewId);
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
            return;
        }
        textView.setText(text);
    }

    protected <T extends View> T setGone(int id) {
        T view = findView(id);
        view.setVisibility(View.GONE);
        return view;
    }

    protected <T extends View> T setVisibility(int id) {
        T view = findView(id);
        view.setVisibility(View.VISIBLE);
        return view;
    }

    protected void setInVisibility(int id) {
        findView(id).setVisibility(View.INVISIBLE);
    }

    protected void onRestartInstance(Bundle bundle) {

    }
}
