package ggstore.com.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import ggstore.com.R;
import ggstore.com.activity.OrderDetailsActivity;
import ggstore.com.base.BaseRecyclerAdapter;
import ggstore.com.base.BaseRecyclerViewFragment;
import ggstore.com.bean.CourseBookBean;
import ggstore.com.constant.Constent;
import ggstore.com.utils.AppOperator;
import ggstore.com.utils.OkHttpManager;
import ggstore.com.utils.ToastUtil;
import okhttp3.Request;

public class MyOrderRecyclerFragment extends BaseRecyclerViewFragment {

    @Override
    protected void requestData(final boolean isRefreshing) { //true 为刷新 false 为加载更多
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
                                list.add("PO1234567"+i);
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
            ((MyViewHolder)holder).title.setText(item);

        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;
            public TextView productName;
            public TextView date;
            public View root;

            public MyViewHolder(View view) {
                super(view);
                root = view;
                title = view.findViewById(R.id.item_my_order_number);
                productName = view.findViewById(R.id.item_my_order_product_name);
                date = view.findViewById(R.id.item_my_order_date);
            }
        }
    }

}
