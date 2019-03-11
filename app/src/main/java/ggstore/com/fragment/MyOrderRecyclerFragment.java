package ggstore.com.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ggstore.com.R;
import ggstore.com.activity.OrderDetailsActivity;
import ggstore.com.base.BaseRecyclerAdapter;
import ggstore.com.base.BaseRecyclerViewFragment;
import ggstore.com.constant.Constent;
import ggstore.com.bean.CourseBookBean;
import ggstore.com.utils.AppOperator;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.OkHttpManager;
import ggstore.com.utils.ToastUtil;
import okhttp3.Request;

public class MyOrderRecyclerFragment extends BaseRecyclerViewFragment {

    @Override
    protected void requestData(final boolean isRefreshing) { //true 为刷新 false 为加载更多
        // todo
            LogUtil.e("刷新了"+isRefreshing);
        if (isRefreshing) {
            page = 1;
            AppOperator.runOnThread(new Runnable() {
                @Override
                public void run() {
                    String url = Constent.base_url + "api_get_coursebook.php?recordperpage=4&page=" + page +
                            "&sortby=&token=" + Constent.token + "&username=&lang=" + getString(R.string.api_lang);
                    OkHttpManager.getAsync(url, new OkHttpManager.DataCallBack() {
                        @Override
                        public void requestFailure(Request request, Exception e) {
                            ToastUtil.showToast("网络出错");
                            onRequestError();
                        }

                        @Override
                        public void requestSuccess(String result) throws Exception {
//                            ArrayList<CourseBookBean> list = parseData(result);
                            ArrayList<String> list = new ArrayList<>();
                            for (int i = 0; i < 10; i++) {
                                list.add("1");
                            }
                            mAdapter.resetItem(list);
                            onRequestSuccess();
                            mRefreshLayout.setEnabled(false);    //设置不可以刷新
                            onNoRequest();  //设置不可以加载更多
                        }
                    });
                }
            });
        }
    }

    int page = 1;
    int maxPage = 10;

    private ArrayList<CourseBookBean> parseData(String result) throws JSONException {
        ArrayList<CourseBookBean> courseBooList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(result);
        JSONObject jsonObject1 = jsonArray.optJSONArray(0).optJSONObject(0);
        int total_page = jsonObject1.optInt("total_page");
        int prev_page = jsonObject1.optInt("prev_page");
        int next_page = jsonObject1.optInt("next_page");
        maxPage = total_page;
        int i = 1;
        while (jsonArray.optJSONArray(i) != null) {
            CourseBookBean courseBookBean = new CourseBookBean();
            courseBookBean.setTotal_page(total_page);
            courseBookBean.setPrev_page(prev_page);
            courseBookBean.setNext_page(next_page);
            LogUtil.e("i=" + i);

            JSONObject jsonObject = jsonArray.getJSONArray(i).optJSONObject(0);
            courseBookBean.setId(jsonObject.optString("id"));
            courseBookBean.setName(jsonObject.optString("name"));
            courseBookBean.setName2(jsonObject.optString("name2"));
            courseBookBean.setDetail(jsonObject.optString("detail"));
            courseBookBean.setDate(jsonObject.optString("date"));
            courseBookBean.setIs_new(jsonObject.optString("is_new"));
            courseBookBean.setAllow_preview(jsonObject.optString("allow_preview"));
            courseBookBean.setCoin(jsonObject.optString("coin"));
            courseBookBean.setBought(jsonObject.optBoolean("bought"));
            courseBookBean.setRead(jsonObject.optBoolean("read"));

            JSONArray photo_list = jsonObject.getJSONArray("photo_list");
            if (photo_list != null && !TextUtils.isEmpty(photo_list.getString(0))) {
                ArrayList<String> thumb_list_list = new ArrayList<>();
                for (int j = 0; j < photo_list.length(); j++) {
                    thumb_list_list.add(photo_list.getString(j));
                }
                courseBookBean.setPhoto_list(thumb_list_list);
            }
            JSONArray thumb_list = jsonObject.getJSONArray("thumb_list");
            if (thumb_list != null && !TextUtils.isEmpty(thumb_list.getString(0))) {
                ArrayList<String> thumb_list_list = new ArrayList<>();
                for (int j = 0; j < thumb_list.length(); j++) {
                    thumb_list_list.add(photo_list.getString(j));
                }
                courseBookBean.setThumb_list(thumb_list_list);
            }
            i++;
            courseBooList.add(courseBookBean);
        }
        return courseBooList;
    }

    @Override
    protected BaseRecyclerAdapter getRecyclerAdapter() {
        final CourseAdapter courseAdapter = new CourseAdapter(getActivity(), BaseRecyclerAdapter.ONLY_FOOTER);
        return courseAdapter;
    }

    @Override
    public void onItemClick(int position, long itemId) {
        String item = (String) getRecyclerAdapter().getItem(position);
        Intent intent = new Intent(getActivity(),OrderDetailsActivity.class);
        startActivity(intent);
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 1);
    }

    class CourseAdapter extends BaseRecyclerAdapter<String> {

        public CourseAdapter(Context context, int mode) {
            super(context, mode);
        }

        @Override
        protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
            CourseAdapter.MyViewHolder myViewHolder = new CourseAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.recycler_my_order_item, parent, false));
            return myViewHolder;
        }


        @Override
        protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, final String item, int position) {
            //TODO 绑定视图--->加上数据
//            ((MyViewHolder)holder).oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);   //加横线效果

//            setImageFromNet(((CourseAdapter.MyViewHolder) holder).book,Constent.base_url + item.getPhoto_list().get(0));
//            ((CourseAdapter.MyViewHolder) holder).bookIcon.setImageResource(R.drawable.newitem);
//            ((CourseAdapter.MyViewHolder) holder).titleBar.setText(item.getName());
//            ((CourseAdapter.MyViewHolder) holder).date.setText(item.getDate());
//            ((MyViewHolder) holder).root.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
//                    intent.putExtra("CourseBookBean",item);
//                    startActivity(intent);
//                }
//            });

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
                title = view.findViewById(R.id.title);
                addShop = view.findViewById(R.id.add_shop);
                oldPrice = view.findViewById(R.id.old_price);
                newPrice = view.findViewById(R.id.new_price);
                imgDetail = view.findViewById(R.id.img_detail);
                detail1 = view.findViewById(R.id.detail1);
                detail2 = view.findViewById(R.id.detail2);
            }
        }
    }

}
