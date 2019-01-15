package ggstore.com.base;

import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;

import ggstore.com.R;
import ggstore.com.view.TitleBar;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created by Administrator on 2017/11/17.
 */

public abstract class BaseTitleActivity extends BaseActivity {
    protected TitleBar titleBar;
    private Badge badge;

    @Override
    protected int getContentView() {
        return R.layout.actionbar_top;
    }

    @Override
    protected void initWidget() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setIconOnClickListener(getBackIconOnClickListener()); //设置左边箭头的点击事件
        titleBar.setTitle(getContentTitle()); // 设置标题
        ViewStub stub = (ViewStub) findViewById(R.id.lay_content);
        stub.setLayoutResource(getContentLayoutId());
        stub.inflate();
        setBarSearchAndShopCart();
        badge = new QBadgeView(this).bindTarget(titleBar.getShopCart()).setBadgeNumber(0).setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeTextSize(7, true).setBadgePadding(0, true);
    }



    protected View.OnClickListener getBackIconOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
    }

    public void setBadgeNumber(int number) {
        badge.setBadgeNumber(number);
    }
    public int getBadgeNumber() {
        return badge.getBadgeNumber();
    }
    public void setBarSearchAndShopCart() {
        titleBar.setSearchVisibility(false);
        titleBar.setShopCartVisibility(false);
    }
    protected abstract CharSequence getContentTitle(); //设置标题

    @LayoutRes
    protected abstract int getContentLayoutId(); //填充除标题外的内容
}
