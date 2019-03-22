package ggstore.com.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ggstore.com.App;
import ggstore.com.R;
import ggstore.com.activity.MainActivity;
import ggstore.com.base.BaseRecyclerAdapter;
import ggstore.com.bean.NewProductBean;
import ggstore.com.constant.Constant;
import ggstore.com.utils.ImageLoader;
import ggstore.com.utils.ShopCartItemManagerUtil;

public class ProductAdapter extends BaseRecyclerAdapter<NewProductBean> {
    static boolean isSingle;
    Context context;
    public ProductAdapter(Context context, int mode,boolean isSingle) {
        super(context, mode);
        this.isSingle = isSingle;
        this.context = context;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        MyViewHolder myViewHolder;
        if (isSingle) {
            myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.recycler_new_product_item, parent, false));
        } else {
            myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).
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
        ((MyViewHolder) holder).oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);   //加横线效果
        if (TextUtils.isEmpty(item.getMarketPrice())) {
            ((MyViewHolder) holder).oldPrice.setText(null);
        } else {
            ((MyViewHolder) holder).oldPrice.setText(App.context().getString(R.string.product_price, item.getMarketPrice()));
        }
        if (TextUtils.isEmpty(item.getUnitPrice())) {
            ((MyViewHolder) holder).newPrice.setText(null);
        } else {
            ((MyViewHolder) holder).newPrice.setText(App.context().getString(R.string.product_price,item.getUnitPrice()));
        }
        ImageLoader.loadImage(App.context(), ((MyViewHolder) holder).imgDetail, Constant.base_images_product_url + item.getPictureL());
        ((MyViewHolder) holder).addShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 应该上传给服务器
                ShopCartItemManagerUtil.addShopCart(item);
                ((MainActivity) context).badge.setBadgeNumber(ShopCartItemManagerUtil.getSize());
            }
        });

    }

   static class MyViewHolder extends RecyclerView.ViewHolder {
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
