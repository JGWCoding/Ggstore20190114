package ggstore.com.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ggstore.com.App;
import ggstore.com.R;
import ggstore.com.activity.OrderDetailsProductActivity;
import ggstore.com.base.BaseRecyclerAdapter;
import ggstore.com.base.BaseRecyclerViewFragment;
import ggstore.com.bean.OrderNumberBean;
import ggstore.com.constant.Constant;
import ggstore.com.utils.AppOperator;
import ggstore.com.utils.ImageLoader;
import ggstore.com.utils.OkHttpManager;
import ggstore.com.utils.OrderNumberManagerUtil;
import ggstore.com.utils.TDevice;
import ggstore.com.utils.ToastUtil;
import okhttp3.Request;

public class OrderDetailsRecyclerFragment extends BaseRecyclerViewFragment {

    @Override
    protected void requestData(final boolean isRefreshing) { //true 为刷新 false 为加载更多
        mRecyclerView.setVerticalFadingEdgeEnabled(true);
        mRecyclerView.setFadingEdgeLength((int) TDevice.dp2px(50));
        if (isRefreshing) {
            AppOperator.runOnThread(new Runnable() {
                @Override
                public void run() {
                    String url = Constant.base_url + "api_get_coursebook.php?recordperpage=4&page="+
                            "&sortby=&token=" + Constant.token + "&username=&lang=" + App.context().getString(R.string.api_lang);
                    OkHttpManager.getAsync(url, new OkHttpManager.DataCallBack() {
                        @Override
                        public void requestFailure(Request request, Exception e) {
                            ToastUtil.showToast("网络出错");
                            onRequestError();
                        }

                        @Override
                        public void requestSuccess(String result) throws Exception {
//                            ArrayList<OrderNumberBean> list = parseData(result);
                            ArrayList<OrderNumberBean> list = OrderNumberManagerUtil.queryOrderId(Constant.orderNumber.getOrder_id());
                            mAdapter.resetItem(list);
                            onRequestSuccess();
                            mRefreshLayout.setEnabled(false);    //设置不可以刷新
                            onNoRequest();  //设置不可以加载更多
                            mAdapter.setStateCustom(App.context().getString(R.string.freight,50),Gravity.RIGHT);
                        }
                    });
                }
            });
        }
    }



    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        final CourseAdapter courseAdapter = new CourseAdapter(getActivity(), BaseRecyclerAdapter.ONLY_FOOTER);
        return courseAdapter;
    }

    @Override
    public void onItemClick(int position, long itemId) {
        OrderNumberBean item = (OrderNumberBean) mAdapter.getItem(position);
        Constant.orderNumber = item;
        Intent intent = new Intent(getActivity(),OrderDetailsProductActivity.class);
        startActivity(intent);
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 1);
    }

    class CourseAdapter extends BaseRecyclerAdapter<OrderNumberBean> {

        public CourseAdapter(Context context, int mode) {
            super(context, mode);
        }

        @Override
        protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
            CourseAdapter.MyViewHolder myViewHolder = new CourseAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.recycler_order_details_item, parent, false));
            return myViewHolder;
        }


        @Override
        protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final OrderNumberBean item, int position) {
            //TODO 绑定视图--->加上数据
            ImageLoader.loadImage(App.context(),((MyViewHolder)holder).img,Constant.base_images_product_url +item.getImage_url());
            ((MyViewHolder)holder).title.setText(item.getName()+"");
            ((MyViewHolder)holder).productNumber.setText(item.getBuy_number()+"");
            ((MyViewHolder)holder).price.setText(App.context().getString(R.string.product_price,item.getPrice()+""));
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView img;
            public TextView title;
            public TextView productNumber;
            public TextView price;

            public MyViewHolder(View view) {
                super(view);
                img = view.findViewById(R.id.recycler_order_details_item_img);
                title = view.findViewById(R.id.recycler_order_details_item_title);
                productNumber = view.findViewById(R.id.recycler_order_details_item_number);
                price = view.findViewById(R.id.recycler_order_details_item_price);
            }
        }
    }

}
