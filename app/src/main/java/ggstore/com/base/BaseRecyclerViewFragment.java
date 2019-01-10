package ggstore.com.base;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;

import ggstore.com.BaseApplication;
import ggstore.com.R;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.OkHttpManager;
import ggstore.com.utils.TDevice;
import ggstore.com.utils.ToastUtils;
import ggstore.com.view.RecyclerRefreshLayout;
import ggstore.com.view.SpacesItemDecoration;


/**
 * //Fragment里填充了RecyclerView,并用EmptyLayout处理空,错误等页面,还有控制刷新页面
 * 基本列表类，重写getLayoutId()自定义界面
 * Created by huanghaibin_dev
 * on 2016/4/12.
 */
public abstract class BaseRecyclerViewFragment<T> extends BaseFragment implements
        RecyclerRefreshLayout.SuperRefreshLayoutListener,
        BaseRecyclerAdapter.OnItemClickListener{
    private final String TAG = this.getClass().getSimpleName();
    protected BaseRecyclerAdapter<T> mAdapter;
    protected RecyclerView mRecyclerView;
    protected RecyclerRefreshLayout mRefreshLayout;
    protected boolean isRefreshing;
    protected String CACHE_NAME = getClass().getName();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_recycler_view;
    }

    @Override
    protected void initWidget(View root) {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false); //设置recyclerView 不会闪屏
        mRefreshLayout = (RecyclerRefreshLayout) root.findViewById(R.id.refreshLayout);
    }

    @Override
    public void initData() {
        mAdapter = getRecyclerAdapter();    //得到这个Fragment的Adapter   mAdapter=SubFragment 第一次的时候
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);   //设置刷新状态 设置不去刷新RecyclerView
        mRecyclerView.setAdapter(mAdapter);     //准备填充这个内容进去
        mAdapter.setOnItemClickListener(this);  //设置item点击事件
        mRefreshLayout.setSuperRefreshLayoutListener(this);
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, false);
        mRecyclerView.setLayoutManager(getLayoutManager()); //设置线性布局
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));//设置分割线
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { //滑动监听
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (RecyclerView.SCROLL_STATE_DRAGGING == newState && getActivity() != null
                        && getActivity().getCurrentFocus() != null) {
                    TDevice.hideSoftKeyboard(getActivity().getCurrentFocus());  //拖拉状态直接隐藏键盘
                }
            }
        });
        onRefreshing(); //加载数据
    }


    @Override
    public void onItemClick(int position, long itemId) {

    }

    @Override
    public void onRefreshing() {
        isRefreshing = true;
        mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, true);    //隐藏正文页面,显示刷新页面, 刷新状态不用更新Recycler条目
        mRefreshLayout.setCanLoadMore(true);
        requestData(true);      //在子类中请求数据 (位于主线程中)---> 在本mHandle处理请求的数据
    }

    @Override
    public void onLoadMore() { // TODO 主要这里有不刷新的item情况
        mAdapter.setState(isRefreshing ? BaseRecyclerAdapter.STATE_HIDE : BaseRecyclerAdapter.STATE_LOADING, true);
        requestData(false);
        refreshState(OkHttpManager.NetWorkTimeOut); //注意设置超时时长 ---- 可能由于这里过长 体验差
    }

    private void refreshState(int delayMillis) {
        BaseApplication.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtil.e("刷新控件了" + mAdapter.getState());
                int state = mAdapter.getState();
                switch (state) {
                    case BaseRecyclerAdapter.STATE_LOADING:
                        mAdapter.setState(BaseRecyclerAdapter.STATE_INVALID_NETWORK, true);
                        break;
                }
            }
        }, delayMillis*1000);
    }

    protected abstract void requestData(boolean isRefreshing);


    protected void onComplete() {
        mRefreshLayout.onComplete();
        isRefreshing = false;
    }

    protected void onRequestSuccess() {
        onComplete();
    }

    protected void onNoRequest() {
        onComplete();
        mRefreshLayout.setCanLoadMore(false);
        // 隐藏最后一条
        LogUtil.e(mAdapter.getItemCount() + "===items:" + mAdapter.getCount());
        mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true); //设置没有更多数据
    }

    protected void onRequestError() {
        onComplete();
        if (mAdapter.getItems().size() == 0) {  //一开始是为0的 --->默认为0,不为0就显示以前的数据
            mAdapter.setState(BaseRecyclerAdapter.STATE_LOAD_ERROR, true);  //
        }
        ToastUtils.showToast("请求出错");
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    protected abstract BaseRecyclerAdapter<T> getRecyclerAdapter();

}
