package ggstore.com.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import ggstore.com.R;
import ggstore.com.utils.TDevice;
import ggstore.com.utils.UiUtil;


/**
 * Created by JuQiu
 * on 16/9/5.
 */
public class TitleBar extends FrameLayout {
    private static int EXT_PADDING_TOP;
    private TextView mTitle;
    private ImageView mIcon;
    private TextView mLogin;


    public TitleBar(Context context) {
        super(context);
        init(null, 0, 0);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Context context = getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.lay_title_bar, this, true);

        mTitle = (TextView) findViewById(R.id.tv_title);
        mLogin = (TextView) findViewById(R.id.tv_login);
        mIcon = (ImageView) findViewById(R.id.iv_icon);


        if (attrs != null) {
            // Load attributes
            final TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.TitleBar, defStyleAttr, defStyleRes);

            String title = a.getString(R.styleable.TitleBar_aTitle);
//            Drawable drawable = a.getDrawable(R.styleable.TitleBar_aIcon);
            a.recycle();

            mTitle.setText(title);
//            mIcon.setImageDrawable(drawable);
        } else {
            mIcon.setVisibility(GONE);
        }

        // Set Background
        setBackgroundColor(getResources().getColor(R.color.bg));
//        setBackgroundColor(Color.parseColor("##eee3c3"));
        // Init padding
//        setPadding(getLeft(), getTop() + UiUtil.getStatusBarHeight(getContext()), getRight(), getBottom());
        setPadding(getLeft(), getTop(), getRight(), getBottom());
    }

    public void setTitle(@StringRes int titleRes) {
        mTitle.setText(titleRes);
    }
    public void setTitle(CharSequence titleRes) {
        mTitle.setText(titleRes);
    }
    public void setLogin(CharSequence titleRes) {
        mLogin.setText(titleRes);
    }
    public void setIcon(int iconRes) {
        if (iconRes <= 0) {
            mIcon.setVisibility(GONE);
            return;
        }
        mIcon.setImageResource(iconRes);
        mIcon.setVisibility(VISIBLE);
    }
    public void setLoginGone(boolean isGone) {
         mLogin.setVisibility(isGone?View.GONE:View.VISIBLE);
    }
    public void setIconOnClickListener(OnClickListener listener) {
        mIcon.setOnClickListener(listener);
    }
    public void setLoginOnClickListener(OnClickListener listener) {
        mLogin.setOnClickListener(listener);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float d = getResources().getDisplayMetrics().density;
        int minH = (int) (d * 36 + UiUtil.getStatusBarHeight(getContext()));

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(minH, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public static int getExtPaddingTop(Resources resources) {
        if (EXT_PADDING_TOP > 0)
            return EXT_PADDING_TOP;

        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            EXT_PADDING_TOP = resources.getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
            return (int) TDevice.dp2px(25);
        }
        return EXT_PADDING_TOP;
    }
}
