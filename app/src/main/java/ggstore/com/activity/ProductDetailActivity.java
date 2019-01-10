package ggstore.com.activity;

import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ggstore.com.R;
import ggstore.com.base.BaseActivity;
import ggstore.com.utils.ToastUtils;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class ProductDetailActivity extends BaseActivity {

    private Badge badge;

    @Override
    protected int getContentView() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initWidget() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitle(getString(R.string.baby_toy));
    }

    @Override
    protected void initData() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_shop);
        View img = item.getActionView().findViewById(R.id.shop_cart);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("点击购物车了");
            }
        });
        badge = new QBadgeView(this).bindTarget(img).setBadgeNumber(1).setBadgeGravity(Gravity.END | Gravity.TOP)
                .setBadgeTextSize(7, true).setBadgePadding(0, true);
        menu.findItem(R.id.action_search).setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }else if (id==R.id.action_shop){

        }else if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
