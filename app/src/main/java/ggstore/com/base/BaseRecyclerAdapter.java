package ggstore.com.base;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ggstore.com.R;
import ggstore.com.utils.ImageLoader;
import ggstore.com.utils.LogUtil;
import ggstore.com.utils.TDevice;

/**
 * the base adapter for RecyclerView
 * Created by huanghaibin on 16-5-3.
 */
@SuppressWarnings("unused")
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {
    protected List<T> mItems;
    protected Context mContext;
    protected LayoutInflater mInflater;

    protected String mSystemTime;

    public static final int STATE_NO_MORE = 1;
    public static final int STATE_LOAD_MORE = 2;
    public static final int STATE_INVALID_NETWORK = 3;
    public static final int STATE_HIDE = 5;
    public static final int STATE_REFRESHING = 6;
    public static final int STATE_LOAD_ERROR = 7;
    public static final int STATE_LOADING = 8;
    public static final int STATE_Custom = 9;   //自定义状态

    public String custom_text;
    public int custom_gravity = Gravity.CENTER;

    public final int BEHAVIOR_MODE;
    protected int mState;

    public static final int NEITHER = 0;
    public static final int ONLY_HEADER = 1;
    public static final int ONLY_FOOTER = 2;
    public static final int BOTH_HEADER_FOOTER = 3;

    public static final int VIEW_TYPE_NORMAL = 0;
    public static final int VIEW_TYPE_HEADER = -1;
    public static final int VIEW_TYPE_FOOTER = -2;

    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    private OnClickListener onClickListener;
    private OnLongClickListener onLongClickListener;

    protected View mHeaderView;
    private RecyclerView mRecyclerView;

    private OnLoadingHeaderCallBack onLoadingHeaderCallBack;

    public BaseRecyclerAdapter(Context context, int mode) { //mode  NEITHER ONLY_HEADER ONLY_FOOTER  BOTH_HEADER_FOOTER
        mItems = new ArrayList<>();
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        BEHAVIOR_MODE = mode;      //控制是否有加载视图和刷新视图
        mState = STATE_HIDE;    //一开始初始化页面为刷新页面
        //mFooterView = mInflater.inflate(R.layout.footer_view, null);
        initListener();
    }

    /**
     * 初始化listener
     */
    private void initListener() {
        onClickListener = new OnClickListener() {
            @Override
            public void onClick(int position, long itemId) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position, itemId);
            }
        };

        onLongClickListener = new OnLongClickListener() {
            @Override
            public boolean onLongClick(int position, long itemId) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onLongClick(position, itemId);
                    return true;
                }
                return false;
            }
        };
    }

    public void setSystemTime(String systemTime) {
        this.mSystemTime = systemTime;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                if (onLoadingHeaderCallBack != null)
                    return onLoadingHeaderCallBack.onCreateHeaderHolder(parent);
                else
                    throw new IllegalArgumentException("you have to impl the interface when using this viewType");
            case VIEW_TYPE_FOOTER:
                return new FooterViewHolder(mInflater.inflate(R.layout.recycler_footer_view, parent, false));
            default:
                final RecyclerView.ViewHolder holder = onCreateDefaultViewHolder(parent, viewType);
                if (holder != null) {
                    holder.itemView.setTag(holder);
                    holder.itemView.setOnLongClickListener(onLongClickListener);
                    holder.itemView.setOnClickListener(onClickListener);
                }
                return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {     //返回itemViewType,给予不同视图
            case VIEW_TYPE_HEADER:
                if (onLoadingHeaderCallBack != null)
                    onLoadingHeaderCallBack.onBindHeaderHolder(holder, position);
                break;
            case VIEW_TYPE_FOOTER:
                showOneLineIsHeaderOrFooter(mRecyclerView); //修复hide和show Fragment(带有RecyclerView)不显示一行
                FooterViewHolder fvh = (FooterViewHolder) holder;
                fvh.itemView.setVisibility(View.VISIBLE);
                switch (mState) {
                    case STATE_INVALID_NETWORK:
                        fvh.tv_footer.setText(mContext.getResources().getString(R.string.state_network_error));
                        fvh.pb_footer.setVisibility(View.GONE);
                        break;
                    case STATE_LOAD_MORE:
                    case STATE_LOADING:
                        fvh.tv_footer.setText(mContext.getResources().getString(R.string.state_loading));
                        fvh.pb_footer.setVisibility(View.VISIBLE);
                        break;
                    case STATE_NO_MORE:
                        fvh.tv_footer.setText(mContext.getResources().getString(R.string.state_not_more));
                        fvh.pb_footer.setVisibility(View.GONE);
                        break;
                    case STATE_REFRESHING:
                        fvh.tv_footer.setText(mContext.getResources().getString(R.string.state_refreshing));
                        fvh.pb_footer.setVisibility(View.GONE);
                        break;
                    case STATE_LOAD_ERROR:
                        fvh.tv_footer.setText(mContext.getResources().getString(R.string.state_load_error));
                        fvh.pb_footer.setVisibility(View.GONE);
                        break;
                    case STATE_HIDE:
                        fvh.itemView.setVisibility(View.GONE);  //隐藏
                    case STATE_Custom:
                        fvh.pb_footer.setVisibility(View.GONE);
                        if (TextUtils.isEmpty(custom_text)) {
                            fvh.itemView.setVisibility(View.GONE);
                        } else {
                            fvh.tv_footer.setText(custom_text);
                        }
                        if (custom_gravity != Gravity.CENTER) {
                            fvh.tv_footer.setTextColor(Color.parseColor("#148BA6"));
                            int width = fvh.tv_footer.getWidth();
                            if (custom_gravity == Gravity.RIGHT) {
                                fvh.tv_footer.setWidth((int) (fvh.itemView.getWidth()));
                                fvh.tv_footer.setGravity(Gravity.RIGHT);
                                fvh.tv_footer.setPadding(0, 0, (int) TDevice.dp2px(20), 0);
                            } else if (custom_gravity == Gravity.LEFT) {
                                fvh.tv_footer.setWidth((int) (fvh.itemView.getWidth()));
                                fvh.tv_footer.setGravity(Gravity.LEFT);
                                fvh.tv_footer.setPadding((int) TDevice.dp2px(20), 0, 0, 0);
                            }
                        }
                        break;
                }
                break;
            default:
                onBindDefaultViewHolder(holder, getItems().get(getIndex(position)), position);
                break;
        }
        LogUtil.i("holder.getItemViewType): " + holder.getItemViewType() + "  mState: " + mState);
    }


    /**
     * 当添加到RecyclerView时获取GridLayoutManager布局管理器，修正header和footer显示整行
     *
     * @param recyclerView the mRecyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        showOneLineIsHeaderOrFooter(recyclerView);
    }

    private void showOneLineIsHeaderOrFooter(RecyclerView recyclerView) {
        if (recyclerView == null) return;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == VIEW_TYPE_HEADER || getItemViewType(position) == VIEW_TYPE_FOOTER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 当RecyclerView在windows活动时获取StaggeredGridLayoutManager布局管理器，修正header和footer显示整行
     *
     * @param holder the RecyclerView.ViewHolder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            if (BEHAVIOR_MODE == ONLY_HEADER) {
                p.setFullSpan(holder.getLayoutPosition() == 0);
            } else if (BEHAVIOR_MODE == ONLY_FOOTER) {
                p.setFullSpan(holder.getLayoutPosition() == mItems.size() + 1);
            } else if (BEHAVIOR_MODE == BOTH_HEADER_FOOTER) {
                if (holder.getLayoutPosition() == 0 || holder.getLayoutPosition() == mItems.size() + 1) {
                    p.setFullSpan(true);
                }
            }
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder.getItemViewType() == VIEW_TYPE_NORMAL) {
            if (holder.itemView instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) holder.itemView;
                recycledImageview(vg);
            } else if (holder.itemView instanceof ImageView) {
                ImageLoader.clear(holder.itemView);
            }
        }
    }

    private void recycledImageview(ViewGroup vg) {
        int childCount = vg.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = vg.getChildAt(i);
            if (child instanceof ViewGroup) {
                recycledImageview((ViewGroup) child);
            } else if (child instanceof ImageView) {
                ImageLoader.clear(child);
            }
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && (BEHAVIOR_MODE == ONLY_HEADER || BEHAVIOR_MODE == BOTH_HEADER_FOOTER))
            return VIEW_TYPE_HEADER;
        if (position + 1 == getItemCount() && (BEHAVIOR_MODE == ONLY_FOOTER || BEHAVIOR_MODE == BOTH_HEADER_FOOTER))
            return VIEW_TYPE_FOOTER;
        else return VIEW_TYPE_NORMAL;
    }

    protected int getIndex(int position) {
        return BEHAVIOR_MODE == ONLY_HEADER || BEHAVIOR_MODE == BOTH_HEADER_FOOTER ? position - 1 : position;
    }

    @Override
    public int getItemCount() {
        if (BEHAVIOR_MODE == ONLY_FOOTER || BEHAVIOR_MODE == ONLY_HEADER) {
            return mItems.size() + 1;
        } else if (BEHAVIOR_MODE == BOTH_HEADER_FOOTER) {
            return mItems.size() + 2;
        } else return mItems.size();
    }

    public int getCount() {     //only return item's size(it's not have footer and header)
        return mItems.size();
    }

    protected abstract RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type);

    protected abstract void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, T item, int position);

    public final View getHeaderView() {
        return this.mHeaderView;
    }

    public final void setHeaderView(View view) {
        this.mHeaderView = view;
    }

    public final List<T> getItems() {
        return mItems;
    }

    public void addAll(List<T> items) {
        if (items != null) {
            if (items.size() == 0) {
                setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
            } else {
                this.mItems.addAll(items);
                notifyItemRangeInserted(this.mItems.size(), items.size());
            }
        }
    }

    public final void addItem(T item) {
        if (item != null) {
            this.mItems.add(item);
            notifyItemChanged(mItems.size());
        }
    }


    public void addItem(int position, T item) {
        if (item != null) {
            this.mItems.add(getIndex(position), item);
            notifyItemInserted(position);
        }
    }

    public void replaceItem(int position, T item) {
        if (item != null) {
            this.mItems.set(getIndex(position), item);
            notifyItemChanged(position);
        }
    }

    public void updateItem(int position) {
        if (mItems.size() <= 0) { //修复没有数据的时候 position=-1 更新视图
            notifyDataSetChanged();
            return;
        }
        LogUtil.e("isUpdate:" + (getItemCount() > position) + " count:" + getItemCount() + " position:" + position + " state:" + getState());
        if (getItemCount() > position) {
            notifyItemChanged(position);
        }
    }


    public final void removeItem(T item) {
        if (this.mItems.contains(item)) {
            int position = mItems.indexOf(item);
            this.mItems.remove(item);
            notifyItemRemoved(position);
        }
    }

    public final void removeItem(int position) {
        if (this.getItemCount() > position) {
            this.mItems.remove(getIndex(position));
            notifyItemRemoved(position);
        }
    }

    public final T getItem(int position) {
        int p = getIndex(position);
        if (p < 0 || p >= mItems.size())
            return null;
        return mItems.get(getIndex(position));
    }

    public final void resetItem(List<T> items) {
        if (items != null) {
            clear();
            addAll(items);
//            this.mItems.clear();
//            setState(STATE_HIDE, false);
//            this.mItems.addAll(items);
//            notifyDataSetChanged();
        } else {
            setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
        }
    }

    private final void clear() {
        this.mItems.clear();
        setState(STATE_HIDE, false);
        notifyDataSetChanged();
    }

    public void setState(int mState, boolean isUpdate) {
        this.mState = mState;
        LogUtil.e("刷新状态为:" + mState + " 更新状态为:" + isUpdate + "  update_position:" + (getItemCount() - 1));
        if (isUpdate) {
            if (mRecyclerView!=null){
                mRecyclerView.post(new Runnable() { //修复如果更新频繁item,有可能后面设置没用(例如:数据少于屏幕可显示数量刷新时不更新footerview/没有更多/)
                    @Override
                    public void run() {
                        updateItem(getItemCount() - 1);
                    }
                });
            }else{
                updateItem(getItemCount() - 1);
            }
        }
    }

    public void setStateCustom(String text, int gravity) {
        this.mState = STATE_Custom;
        this.custom_text = text;
        this.custom_gravity = gravity;
        updateItem(getItemCount() - 1);
    }

    public int getState() {
        return mState;
    }

    /**
     * 添加项点击事件
     *
     * @param onItemClickListener the RecyclerView item click listener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 添加项点长击事件
     *
     * @param onItemLongClickListener the RecyclerView item long click listener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public final void setOnLoadingHeaderCallBack(OnLoadingHeaderCallBack listener) {
        onLoadingHeaderCallBack = listener;
    }


    /**
     * 可以共用同一个listener，相对高效
     */
    public static abstract class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            onClick(holder.getAdapterPosition(), holder.getItemId());
        }

        public abstract void onClick(int position, long itemId);
    }


    /**
     * 可以共用同一个listener，相对高效
     */
    public static abstract class OnLongClickListener implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            return onLongClick(holder.getAdapterPosition(), holder.getItemId());
        }

        public abstract boolean onLongClick(int position, long itemId);
    }


    /**
     *
     */
    public interface OnItemClickListener {
        void onItemClick(int position, long itemId);
    }


    public interface OnItemLongClickListener {
        void onLongClick(int position, long itemId);
    }

    /**
     * for load header view
     */
    public interface OnLoadingHeaderCallBack {
        RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent);

        void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position);
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar pb_footer;
        public TextView tv_footer;

        public FooterViewHolder(View view) {
            super(view);
            pb_footer = (ProgressBar) view.findViewById(R.id.pb_footer);
            tv_footer = (TextView) view.findViewById(R.id.tv_footer);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
