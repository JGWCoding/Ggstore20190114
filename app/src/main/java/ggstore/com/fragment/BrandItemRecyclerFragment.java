package ggstore.com.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ggstore.com.R;
import ggstore.com.activity.BrandActivity;
import ggstore.com.activity.ProductDetailActivity;
import ggstore.com.base.BaseMyRecyclerViewFragment;
import ggstore.com.base.BaseRecyclerAdapter;
import ggstore.com.bean.NewProductBean;
import ggstore.com.constant.Constant;
import ggstore.com.utils.AppOperator;
import ggstore.com.utils.ImageLoader;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.OkHttpManager;
import ggstore.com.utils.ShopCartItemManagerUtil;
import ggstore.com.utils.ToastUtil;
import okhttp3.Request;

public class BrandItemRecyclerFragment extends BaseMyRecyclerViewFragment {
    int page = 1;
    int maxPage = 10;
    @Override
    protected void requestData(boolean isRefreshing) {
        if (isRefreshing) {
            page = 1;
            AppOperator.runOnThread(new Runnable() {
                @Override
                public void run() {
                    String url = Constant.url_brand_cate+getArguments().getString("url") + "&p=" + page;
                    OkHttpManager.getAsync(url, new OkHttpManager.DataCallBack() {
                        @Override
                        public void requestFailure(Request request, Exception e) {
                            ToastUtil.showToast(R.string.network_error);
                            onRequestError();
                        }

                        @Override
                        public void requestSuccess(String result) throws Exception {
                            if (TextUtils.isEmpty(result)) {
                                requestFailure(null, null);
                                return;
                            }
                            String productCount = new JSONObject(result).optJSONObject("data").optString("productCount");
                            maxPage = (int) Math.ceil(Float.valueOf(productCount) / 20);
                            LogUtil.e(maxPage + "");
                            ArrayList<NewProductBean> list = parseData(result);
                            mAdapter.resetItem(list);
                            onRequestSuccess();
                        }
                    });
                }
            });

        } else {
            page++;
            if (page > maxPage) {
                onNoRequest();
                return;
            }
            AppOperator.runOnThread(new Runnable() {
                @Override
                public void run() {
                    String url = Constant.url_brand_cate +getArguments().getString("url")+ "&p=" + page;
                    OkHttpManager.getAsync(url, new OkHttpManager.DataCallBack() {
                        @Override
                        public void requestFailure(Request request, Exception e) {
                            ToastUtil.showToast(R.string.network_error);
                            onRequestError();
                        }

                        @Override
                        public void requestSuccess(String result) throws Exception {
                            ArrayList<NewProductBean> list = parseData(result);
                            mAdapter.addAll(list);
                            onRequestSuccess();
                        }
                    });
                }
            });

        }
    }
    private ArrayList<NewProductBean> parseData(String result) throws JSONException {
        JSONArray jsonArray = new JSONObject(result).getJSONObject("data").getJSONArray("productList");
        ArrayList<NewProductBean> newProductBeans = (ArrayList<NewProductBean>) JSON.parseArray(jsonArray.toString(), NewProductBean.class);
        return newProductBeans;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        final CourseAdapter courseAdapter = new CourseAdapter(getContext(),BaseRecyclerAdapter.ONLY_FOOTER);
        return courseAdapter;
    }

    @Override
    public void onItemClick(int position, long itemId) {
        NewProductBean item = (NewProductBean) mAdapter.getItem(position);
        Constant.newProductBean = item;
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra(ProductDetailActivity.product_title, getContext().getString(R.string.brand)); //携带信息
        startActivity(intent);
    }



    class CourseAdapter extends BaseRecyclerAdapter<NewProductBean> {

        public CourseAdapter(Context context, int mode) {
            super(context, mode);
        }

        @Override
        protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
            CourseAdapter.MyViewHolder myViewHolder;
            if (isSingle) {
                myViewHolder = new CourseAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.recycler_new_product_item, parent, false));
            } else {
                myViewHolder = new CourseAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.recycler_new_product_item_double, parent, false));
            }
            return myViewHolder;
        }


        @Override
        protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final NewProductBean item, int position) {
            //TODO 绑定视图--->加上数据
            if (isSingle) {
            } else {
            }
            ((MyViewHolder) holder).title.setText(item.getProductName_cn());
            ((CourseAdapter.MyViewHolder) holder).oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);   //加横线效果
            if (TextUtils.isEmpty(item.getMarketPrice())) {
                ((MyViewHolder) holder).oldPrice.setText(null);
            } else {
                ((MyViewHolder) holder).oldPrice.setText(getContext().getString(R.string.product_price, item.getMarketPrice()));
            }
            if (TextUtils.isEmpty(item.getUnitPrice())) {
                ((MyViewHolder) holder).newPrice.setText(null);
            } else {
                ((MyViewHolder) holder).newPrice.setText(getContext().getString(R.string.product_price,item.getUnitPrice()));
            }
            ImageLoader.loadImage(getContext(), ((CourseAdapter.MyViewHolder) holder).imgDetail, Constant.base_images_product_url + item.getPictureL());
            ((CourseAdapter.MyViewHolder) holder).addShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 应该上传给服务器
                    ShopCartItemManagerUtil.addShopCart(item);
                    ((BrandActivity) getActivity()).badge.setBadgeNumber(ShopCartItemManagerUtil.getSize());
                }
            });

        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView addShop;
            public ImageView imgDetail;
            public TextView title;
            public TextView oldPrice;
            public TextView newPrice;
            public TextView detail1;
            public TextView detail2;
            public View root;

            public MyViewHolder(View view) {
                super(view);
                root = view;
                if (isSingle) {
                    detail1 = view.findViewById(R.id.detail1);
                    detail2 = view.findViewById(R.id.detail2);
                } else {

                }
                title = view.findViewById(R.id.recycler_new_product_title);
                addShop = view.findViewById(R.id.add_shop);
                oldPrice = view.findViewById(R.id.old_price);
                newPrice = view.findViewById(R.id.new_price);
                imgDetail = view.findViewById(R.id.img_detail);
            }
        }
    }
}