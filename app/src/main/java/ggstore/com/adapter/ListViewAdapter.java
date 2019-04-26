package ggstore.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ggstore.com.R;

public class ListViewAdapter extends BaseAdapter {
    private List<String> dataList;
    private Context context;
    private int resource;

    /**
     * 有参构造
     *
     * @param context
     *            界面
     * @param dataList
     *            数据
     * @param resource
     *            列表项资源文件
     */
    public ListViewAdapter(Context context, List<String> dataList,
                     int resource) {
        this.context = context;
        this.dataList = dataList;
        this.resource = resource;

    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int index) {

        return dataList.get(index);
    }

    @Override
    public long getItemId(int index) {

        return index;
    }

    @Override
    public View getView(int index, View view, ViewGroup arg2) {
        // 声明内部类
        Util util = null;
        // 中间变量
        final int flag = index;
        /**
         * 根据listView工作原理，列表项的个数只创建屏幕第一次显示的个数。
         * 之后就不会再创建列表项xml文件的对象，以及xml内部的组件，优化内存，性能效率
         */
        if (view == null) {
            util = new Util();
            // 给xml布局文件创建java对象
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
            util.titleTextView = (TextView) view.findViewById(R.id.list_item_text_view_tv);
            // 增加额外变量
            view.setTag(util);
        } else {
            util = (Util) view.getTag();
        }
        util.titleTextView.setText(dataList.get(index));
        return view;
    }
    class Util {
        TextView titleTextView;
    }

}


