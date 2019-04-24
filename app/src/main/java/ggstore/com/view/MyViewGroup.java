package ggstore.com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import ggstore.com.utils.TDevice;

public class MyViewGroup extends LinearLayout {

    private Paint paint;
    private float width = TDevice.dp2px(5);
    private float radiusWidth = 0;
    int[] colors = {0xFFFFFFFF, 0xFFE6E6E6, 0xFFFFFFFF};  //不要用R.colors.xxx
    float[] position = {0.0f, 0.2f, 1f};
    private LinearGradient shader;

    public MyViewGroup(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        paint = new Paint();
//        //draw Right gradient
//        shader = new LinearGradient(getMeasuredWidth() - width, 0, getMeasuredWidth(), 0, colors, position, Shader.TileMode.CLAMP);
//        paint.setShader(shader);
//        canvas.drawRect(getMeasuredWidth() - width, 0, getMeasuredWidth(), getMeasuredHeight() - width - radiusWidth, paint);
//        //draw bottom gradient
//        shader = new LinearGradient(0, getMeasuredHeight() - width, 0, getMeasuredHeight(), colors, position, Shader.TileMode.CLAMP);
//        paint.setShader(shader);
//        canvas.drawRect(0, getMeasuredHeight() - width, getMeasuredWidth() - width - radiusWidth, getMeasuredHeight() , paint);
//        //draw bottom|right 交接处 gradient 1/4的圆
//        RectF mRectF = new RectF(getMeasuredWidth() - width * 2-2*radiusWidth, getMeasuredHeight() - width * 2-2*radiusWidth, getMeasuredWidth(), getMeasuredHeight());
//        RadialGradient radialGradient = new RadialGradient(mRectF.centerX(), mRectF.centerY(), width+radiusWidth, colors, position,
//                Shader.TileMode.CLAMP);
//        paint.setShader(radialGradient);
//        canvas.drawArc(mRectF, 0, 90, true, paint);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        //draw Right gradient
        shader = new LinearGradient(getMeasuredWidth() - width, 0, getMeasuredWidth(), 0, colors, position, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRect(getMeasuredWidth() - width, 0, getMeasuredWidth(), getMeasuredHeight() - width, paint);
        //draw bottom gradient
        shader = new LinearGradient(0, getMeasuredHeight() - width, 0, getMeasuredHeight(), colors, position, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRect(0, getMeasuredHeight() - width, getMeasuredWidth() - width, getMeasuredHeight(), paint);
        //draw bottom|right 交接处 gradient 1/4的圆
        RectF mRectF = new RectF(getMeasuredWidth() - width * 2, getMeasuredHeight() - width * 2, getMeasuredWidth(), getMeasuredHeight());
        RadialGradient radialGradient = new RadialGradient(mRectF.centerX(), mRectF.centerY(), width, colors, position,
                Shader.TileMode.CLAMP);
        paint.setShader(radialGradient);
        canvas.drawArc(mRectF, 0, 90, true, paint);
    }
}
