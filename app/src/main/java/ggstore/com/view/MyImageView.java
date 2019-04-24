package ggstore.com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;

import ggstore.com.utils.TDevice;

public class MyImageView extends android.support.v7.widget.AppCompatImageView {

    private Paint paint;
    private float width = TDevice.dp2px(4);
    int[] colors = { 0xFFe6e6e6,0xFFe6e6e6,0xFFFFFFFF};  //不要用R.colors.xxx
    float[] position = {0.0f,0.2f,1f};
    private LinearGradient shader;

    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw Right gradient
        shader = new LinearGradient(getMeasuredWidth() - width, 0, getMeasuredWidth(), 0, colors, position, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRect(getMeasuredWidth() - width, 0, getMeasuredWidth(), getMeasuredHeight() - width, paint);
        //draw bottom gradient
        shader = new LinearGradient(0, getMeasuredHeight() - width, 0, getMeasuredHeight(), colors, position, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRect(0, getMeasuredHeight() - width, getMeasuredWidth() - width, getMeasuredHeight(), paint);
        //draw bottom|right 交接处 gradient 1/4的圆
        RectF mRectF = new RectF(getMeasuredWidth() - width*2, getMeasuredHeight() - width*2, getMeasuredWidth(), getMeasuredHeight());
        RadialGradient radialGradient = new RadialGradient(mRectF.centerX(), mRectF.centerY(),width, colors, position,
                Shader.TileMode.CLAMP);
        paint.setShader(radialGradient);
        canvas.drawArc(mRectF, 0, 90, true, paint);
    }
}
