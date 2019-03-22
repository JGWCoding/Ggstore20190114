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

import ggstore.com.App;
import ggstore.com.R;
import ggstore.com.activity.MainActivity;
import ggstore.com.base.BaseRecyclerAdapter;
import ggstore.com.base.BaseRecyclerViewFragment;
import ggstore.com.bean.ShopCartBean;
import ggstore.com.constant.Constant;
import ggstore.com.utils.AppOperator;
import ggstore.com.utils.ImageLoader;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.OkHttpManager;
import ggstore.com.utils.ShopCartItemManagerUtil;
import ggstore.com.utils.ToastUtil;
import okhttp3.Request;

public class ShopCartRecycleFragment extends BaseRecyclerViewFragment {
    int page;

    @Override
    protected void requestData(final boolean isRefreshing) { //true 为刷新 false 为加载更多
        if (isRefreshing) {
            page = 1;
            AppOperator.runOnThread(new Runnable() {
                @Override
                public void run() {
                    String url = Constant.base_url + "api_get_coursebook.php?recordperpage=4&page=" + page +
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
                            ArrayList<ShopCartBean> list = (ArrayList<ShopCartBean>) ShopCartItemManagerUtil.queryAll();
                            mAdapter.resetItem(list);
                            onRequestSuccess();
                            mRefreshLayout.setEnabled(false);//设置不可刷新,因为购物车一般只加载一次
                            mRefreshLayout.setCanLoadMore(false);   //设置不可加载更多
                            mAdapter.setStateCustom(App.context().getString(R.string.shop_cart_discount), Gravity.CENTER);
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
                    inflate(R.layout.recycler_shop_cart_item, parent, false));
            return myViewHolder;
        }


        @Override
        protected void onBindDefaultViewHolder(final RecyclerView.ViewHolder holder, final ShopCartBean item, final int position) {
            //TODO 绑定视图--->加上数据
            ((MyViewHolder) holder).title.setText(item.getName());
            ((MyViewHolder) holder).cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getCount() == 1) {
                        ShopCartItemManagerUtil.deleteShopCart(item.getId());
                        ((ShopCartFragment) getParentFragment()).emptyShopCart();
                    } else {
                        LogUtil.e(position + " = " + item);
//                        removeItem(position); //position不确定性的,有时候不是对应的item position
                        removeItem(item);
                        ((MainActivity) getActivity()).badge.setBadgeNumber(ShopCartItemManagerUtil.getSize() - 1);
                        ShopCartItemManagerUtil.deleteShopCart(item.getId());
                        ((ShopCartFragment) getParentFragment()).setPriceSum();
                    }
                }
            });
            ((MyViewHolder) holder).sum.setText(item.getBuy_number() + "");
            ImageLoader.loadImage(App.context(), ((MyViewHolder) holder).img, Constant.base_images_product_url + item.getImage_url());
            ((MyViewHolder) holder).add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = ((MyViewHolder) holder).sum.getText().toString();
                    if (Integer.valueOf(s) >= item.getLimit_number()) {
                        ToastUtil.showToast("Exceeding the limit");
                        ((MyViewHolder) holder).sum.setText(item.getLimit_number() + "");
                        item.setBuy_number(item.getLimit_number());
                    } else {
                        ((MyViewHolder) holder).sum.setText(item.getBuy_number() + 1 + "");
                        item.setBuy_number(item.getBuy_number() + 1);
                    }
                    ShopCartItemManagerUtil.updateShopCart(item);
                        ((ShopCartFragment) getParentFragment()).setPriceSum();
                }
            });
            ((MyViewHolder) holder).del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = ((MyViewHolder) holder).sum.getText().toString();
                    if (Integer.valueOf(s) <= 0) {
                        ((MyViewHolder) holder).sum.setText(0 + "");
                        item.setBuy_number(0);
                        ((ShopCartFragment) getParentFragment()).setPriceSum();
                    } else if (Integer.valueOf(s) <= 1) {
                        ((MyViewHolder) holder).sum.setText(1 + "");
                        item.setBuy_number(1);
                    } else {
                        ((MyViewHolder) holder).sum.setText(item.getBuy_number() - 1 + "");
                        item.setBuy_number(item.getBuy_number() - 1);
                    }
                    ShopCartItemManagerUtil.updateShopCart(item);
                    ((ShopCartFragment) getParentFragment()).setPriceSum();
                }
            });
            ((MyViewHolder) holder).price.setText(App.context().getString(R.string.product_price, (int) item.getPrice() + ""));
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public ImageView img;
            public TextView cancel;
            public TextView sum;
            public TextView add;
            public TextView del;
            public TextView price;
            public TextView detail1;
            public TextView detail2;

            public MyViewHolder(View view) {
                super(view);
                cancel = view.findViewById(R.id.fragment_shopcart_item_cancel);
                add = view.findViewById(R.id.fragment_shopcart_item_add);
                del = view.findViewById(R.id.fragment_shopcart_item_del);
                title = view.findViewById(R.id.fragment_shopcart_item_title);
                img = view.findViewById(R.id.fragment_shopcart_item_image);
                price = view.findViewById(R.id.fragment_shopcart_item_price);
                sum = view.findViewById(R.id.fragment_shopcart_item_sum);
//                detail1 = view.findViewById(R.id.detail1);
//                detail2 = view.findViewById(R.id.detail2);
            }
        }
    }
}
