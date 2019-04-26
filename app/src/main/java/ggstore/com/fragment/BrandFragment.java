package ggstore.com.fragment;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ggstore.com.R;
import ggstore.com.activity.BrandActivity;
import ggstore.com.base.BaseRecyclerAdapter;
import ggstore.com.base.BaseRecyclerViewFragment;
import ggstore.com.bean.BrandImageBean;
import ggstore.com.constant.Constant;
import ggstore.com.utils.AppOperator;
import ggstore.com.utils.OkHttpManager;
import ggstore.com.utils.ToastUtil;
import okhttp3.Request;

public class BrandFragment extends BaseRecyclerViewFragment {
    @Override
    protected void requestData(boolean isRefreshing) {
//        mRecyclerView.setPadding(75 - 30 / 2, 30 / 2, 75 - 30 / 2, 30 / 2);
        if (isRefreshing) {
            AppOperator.runOnThread(new Runnable() {
                @Override
                public void run() {
                    String url = Constant.url_brand_image;
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
                            ArrayList<BrandImageBean> list = parseData(result);
                            mAdapter.resetItem(list);
                            onRequestSuccess();
                        }
                    });
                }
            });
        }
    }

    private ArrayList<BrandImageBean> parseData(String result) throws JSONException {
        JSONArray jsonArray = new JSONObject(result).optJSONArray("data");
        ArrayList<BrandImageBean> beans = (ArrayList<BrandImageBean>) JSON.parseArray(jsonArray.toString(), BrandImageBean.class);
        return beans;
    }

    @Override
    public void onItemClick(int position, long itemId) {
        BrandActivity.startActivity(getActivity(), mAdapter.getItems(), position);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        if (mRecyclerView.getItemDecorationCount() > 0) {
            mRecyclerView.removeItemDecoration(mRecyclerView.getItemDecorationAt(0));
        }
        return new GridLayoutManager(getActivity(), 3);
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        return new CourseAdapter(getActivity(), BaseRecyclerAdapter.NEITHER);
    }

    class CourseAdapter extends BaseRecyclerAdapter<BrandImageBean> {

        public CourseAdapter(Context context, int mode) {
            super(context, mode);
        }

        @Override
        protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
            CourseAdapter.MyViewHolder holder;
            holder = new CourseAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.recycler_brand_image_item, parent, false));
            parent.setPadding(15, 30/2, 15, 30/2);
            return holder;
        }


        @Override
        protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final BrandImageBean item, int position) {
            //TODO 绑定视图--->加上数据
            if (position % 3 == 0) {
                GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) (holder).itemView.getLayoutParams();
                layoutParams.setMargins(60, 30 / 2, 0, 30 / 2);
                (holder).itemView.setLayoutParams(layoutParams);
            } else if (position % 3 == 1) {
                GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) (holder).itemView.getLayoutParams();
                layoutParams.setMargins(30, 30 / 2, 30, 30 / 2);
                (holder).itemView.setLayoutParams(layoutParams);
            } else {
                GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) (holder).itemView.getLayoutParams();
                layoutParams.setMargins(0, 30 / 2, 60 , 30 / 2);
                (holder).itemView.setLayoutParams(layoutParams);
            }
            setImageFromNet(((MyViewHolder) holder).img, Constant.base_images_brand_url + item.getBrandImg());
//            ((MyViewHolder)holder).img.setImageResource(R.drawable.product);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView img;

            public MyViewHolder(View view) {
                super(view);
                img = view.findViewById(R.id.recycler_item_brand_image);
            }
        }
    }


    public static boolean isSingle = true; //是否为单列
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.fragment_gravida_mother;
//    }
//
//    @Override
//    protected void initWidget(View root) {
//        isSingle = true;
//        addFragment(R.id.fragment_new_product_fragment,new BrandRecyclerRecyclerFragment());
//        final ImageView singleImg = findView(R.id.fragment_new_product_img);
//        final ImageView doubleImg = findView(R.id.fragment_new_product_img2);
//        singleImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isSingle=true;
//                BrandRecyclerRecyclerFragment fragment = (BrandRecyclerRecyclerFragment) getChildFragmentManager().findFragmentByTag(BrandRecyclerRecyclerFragment.class.getName());
//                fragment.setLayoutManager(fragment.getLayoutManager());
//                singleImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_rectangle_bg_5));
//                doubleImg.setBackgroundDrawable(null);
//            }
//        });
//        doubleImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isSingle=false;
//                singleImg.setBackgroundDrawable(null);
//                doubleImg.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_rectangle_bg_5));
//                BrandRecyclerRecyclerFragment fragment = (BrandRecyclerRecyclerFragment) getChildFragmentManager().findFragmentByTag(BrandRecyclerRecyclerFragment.class.getName());
//                fragment.setLayoutManager(fragment.getLayoutManager());
//            }
//        });
//    }
}
