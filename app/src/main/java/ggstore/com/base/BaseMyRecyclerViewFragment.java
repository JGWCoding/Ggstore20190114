package ggstore.com.base;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import ggstore.com.R;


/**
 * //Fragment里填充了RecyclerView,并用EmptyLayout处理空,错误等页面,还有控制刷新页面
 * 基本列表类，重写getLayoutId()自定义界面
 * Created by huanghaibin_dev
 * on 2016/4/12.
 */
public abstract class BaseMyRecyclerViewFragment<T> extends BaseRecyclerViewFragment<T> {

    public boolean isSingle = true; //是否为单列

    @Override
    public int getLayoutId() {
        return R.layout.fragment_base_my_recycler_view;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        final ImageView singleImg = findView(R.id.fragment_my_item_img);
        final ImageView doubleImg = findView(R.id.fragment_my_item_img2);
        singleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSingle){
                    return;
                }
                isSingle=true;
                setLayoutManager(new GridLayoutManager(getContext(), 1));
                singleImg.setBackgroundResource(R.drawable.single_icon_bule);
                doubleImg.setBackgroundResource(R.drawable.double_icon_white);
            }
        });
        doubleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSingle){
                    return;
                }
                isSingle=false;
                singleImg.setBackgroundResource(R.drawable.single_icon_white);
                doubleImg.setBackgroundResource(R.drawable.double_icon_blue);
                setLayoutManager(new GridLayoutManager(getContext(), 2));
            }
        });
    }

}
