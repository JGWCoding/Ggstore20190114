package ggstore.com.view;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import ggstore.com.R;
import razerdp.basepopup.BasePopupWindow;

public class DemoPopup extends BasePopupWindow {
    public DemoPopup(Context context) {
        super(context);
    }

    // 必须实现，这里返回您的contentView
    // 为了让库更加准确的做出适配，强烈建议使用createPopupById()进行inflate
    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.popup_list);
    }
    
    // 以下为可选代码（非必须实现）
    // 返回作用于PopupWindow的show和dismiss动画，本库提供了默认的几款动画，这里可以自由实现
    @Override
    protected Animation onCreateShowAnimation() {
        return getDefaultScaleAnimation(true);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getDefaultScaleAnimation(false);
    }
}