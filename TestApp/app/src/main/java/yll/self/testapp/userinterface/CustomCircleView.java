package yll.self.testapp.userinterface;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import yll.self.testapp.R;


/**画一个圆形的view, isChecked为true时会在中间绘制一个勾*/
public class CustomCircleView extends View {

    private Context ctx;
    /**
     * 圆环的颜色
     */
    private int roundColor;
    private Paint paint;

    public CustomCircleView(Context context) {
        this(context, null);
        ctx = context;
    }

    public CustomCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        ctx = context;
    }

    public CustomCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        ctx = context;

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CustomCircleView);

        //获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.CustomCircleView_circleColor, Color.RED);

        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centreX = getWidth() / 2;
        int centreY = getHeight() / 2;
        int radius = Math.min(centreX, centreY); //圆的半径
        paint.setColor(roundColor); //设置圆的颜色
        paint.setStyle(Paint.Style.FILL); //设置实心
        paint.setStrokeWidth(radius); //设置圆环的宽度
        paint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(centreX, centreY, radius, paint); //画出圆
    }

    public int getRoundColor() {
        return roundColor;
    }

    public void setRoundColor(int color) {
        this.roundColor = color;
        postInvalidate();
    }

}
