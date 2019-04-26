package ggstore.com.fragment;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ggstore.com.R;
import ggstore.com.base.BaseRecyclerAdapter;
import ggstore.com.base.BaseRecyclerViewFragment;
import ggstore.com.bean.ShopCartBean;
import ggstore.com.constant.Constant;
import ggstore.com.utils.AppOperator;
import ggstore.com.utils.ImageLoader;
import ggstore.com.utils.OkHttpManager;
import ggstore.com.utils.ShopCartItemManagerUtil;
import ggstore.com.utils.ToastUtil;
import okhttp3.Request;

public class ShopCartListRecycleFragment extends BaseRecyclerViewFragment {
    int page;
    @Override
    protected void requestData(final boolean isRefreshing) { //true 为刷新 false 为加载更多
        if (isRefreshing) {
            page = 1;
            AppOperator.runOnThread(new Runnable() {
                @Override
                public void run() {
                    String url = Constant.base_url + "api_get_coursebook.php?recordperpage=4&page=" + page +
                            "&sortby=&token=" + Constant.token + "&username=&lang=" + getString(R.string.api_lang);
                    OkHttpManager.getAsync(url, new OkHttpManager.DataCallBack() {
                        @Override
                        public void requestFailure(Request request, Exception e) {
                            ToastUtil.showToast(R.string.network_error);
                            onRequestError();
                        }
                        @Override
                        public void requestSuccess(String result) throws Exception {
//                            ArrayList<OrderNumberBean> list = parseData(result);
                            ArrayList<ShopCartBean> list = (ArrayList<ShopCartBean>)ShopCartItemManagerUtil.queryAll();
                            mAdapter.resetItem(list);
                            onRequestSuccess();
                            mRefreshLayout.setEnabled(false);//设置不可刷新,以为购物车一般只加载一次
                            mRefreshLayout.setCanLoadMore(false);   //设置
//                            mAdapter.setStateCustom("運費 : HK$50",Gravity.RIGHT);
                            mAdapter.setStateCustom(getString(R.string.freight,50),Gravity.RIGHT);

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

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 1);
    }

    class CourseAdapter extends BaseRecyclerAdapter<ShopCartBean> {

        public CourseAdapter(Context context, int mode) {
            super(context, mode);
        }

        @Override
        protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
            CourseAdapter.MyViewHolder myViewHolder = new CourseAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.recycler_shop_cart_list_item, parent, false));
            return myViewHolder;
        }


        @Override
        protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final ShopCartBean item, int position) {
            //TODO 绑定视图--->加上数据
            ImageLoader.loadImage(getContext(),((MyViewHolder) holder).img,Constant.base_images_product_url +item.getImage_url());
            ((MyViewHolder) holder).title.setText(item.getName());
            ((MyViewHolder) holder).productNumber.setText(getString(R.string.product_number,item.getProductNumber()));
            ((MyViewHolder) holder).number.setText(getString(R.string.product_number_sum,item.getBuy_number()));
            ((MyViewHolder) holder).price.setText(getString(R.string.product_price_detail,(int)item.getPrice()));
            ((MyViewHolder) holder).totalPrice.setText(getString(R.string.product_price_sum,(int)(item.getPrice()*item.getBuy_number())));
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView img;
            public TextView title;
            public TextView number;
            public TextView price;
            public TextView totalPrice;
            public TextView productNumber;

            public MyViewHolder(View view) {
                super(view);
                img = view.findViewById(R.id.recycler_shopcart_list_item_image);
                title = view.findViewById(R.id.recycler_shopcart_list_item_title);
                number = view.findViewById(R.id.recycler_shopcart_list_item_number);
                price = view.findViewById(R.id.recycler_shopcart_list_item_price);
                totalPrice = view.findViewById(R.id.recycler_shopcart_list_item_total_price);
                productNumber = view.findViewById(R.id.recycler_shopcart_list_item_product_number);
            }
        }
    }
}
