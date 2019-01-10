package ggstore.com.base;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewStub;
import ggstore.com.R;
import ggstore.com.view.TitleBar;

/**
 * Created by Administrator on 2017/11/17.
 */

public abstract class BaseTitleActivity extends BaseActivity {
    protected TitleBar title;

    @Override
    protected int getContentView() {
        return R.layout.actionbar_top;
    }

    @Override
    protected void initWidget() {
        title = (TitleBar) findViewById(R.id.title_bar);
        title.setIconOnClickListener(getBackIconOnClickListener()); //设置左边箭头的点击事件
        title.setTitle(getContentTitle()); // 设置标题
        title.setLoginGone(true); //设置右边的title不显示
        ViewStub stub = (ViewStub) findViewById(R.id.lay_content);
        stub.setLayoutResource(getContentLayoutId());
        stub.inflate();
    }

    protected View.OnClickListener getBackIconOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
    }

    protected abstract CharSequence getContentTitle(); //设置标题

    @LayoutRes
    protected abstract int getContentLayoutId(); //填充除标题外的内容
}
