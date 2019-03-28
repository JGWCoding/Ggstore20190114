package ggstore.com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

import ggstore.com.utils.TDevice;

public class MyImageView extends android.support.v7.widget.AppCompatImageView {

    private Paint paint;
    private float width = TDevice.dp2px(10);
    int[] colors={Color.BLACK,Color.WHITE};
    private LinearGradient shader;

    public MyImageView(Context context) {
        this(context,null);
    }

    public MyImageView(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
//        paint.setStyle(Paint.Style.FILL);
        shader = new LinearGradient(getMeasuredWidth()/2,getMeasuredHeight()/2,getMeasuredWidth(),getMeasuredHeight(),new int[]{Color.BLUE,Color.RED},new float[]{0.5f,1},Shader.TileMode.CLAMP);
//        LinearGradient shader = new LinearGradient(0,0,TDevice.dp2px(width),0, colors, null,Shader.TileMode.CLAMP);
//        paint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setShader(shader);
//        canvas.drawRect(getMeasuredWidth()-width,0,getMeasuredWidth(),getMeasuredHeight(),paint);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint);

//        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, 200, paint);
//        canvas.drawRect(getWidth()-TDevice.dp2px(width),0,getWidth(),getHeight(),paint);
//        canvas.save();
    }
}
