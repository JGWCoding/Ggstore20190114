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
    private float width = TDevice.dp2px(3);
    int[] colors={Color.GRAY,Color.WHITE};
    float[] position = {0.1f,1};
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        shader = new LinearGradient(getMeasuredWidth()-width,0,getMeasuredWidth(),0,colors,position,Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRect(getMeasuredWidth()-width,0,getMeasuredWidth(),getMeasuredHeight()-width/2,paint);
        shader = new LinearGradient(0,getMeasuredHeight()-width,0,getMeasuredHeight(),colors,position, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRect(0,getMeasuredHeight()-width,getMeasuredWidth()-width/2,getMeasuredHeight(),paint);
//        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, 200, paint);
//        canvas.drawRect(getWidth()-TDevice.dp2px(width),0,getWidth(),getHeight(),paint);
//        canvas.save();
    }
}
