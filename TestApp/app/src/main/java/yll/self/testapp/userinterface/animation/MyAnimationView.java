package yll.self.testapp.userinterface.animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

import yll.self.testapp.R;

/**
 * Created by yll on 16/11/23.
 */

public class MyAnimationView extends View {

    private Context context;
    private final long DURATION = 1000;
    private int circleTime;
    final int WAVE_COUNT = 4;
    private int starCount[] = new int[WAVE_COUNT];//出来4波
    private Path[][] paths = new Path[WAVE_COUNT][];
    private PathMeasure[][] pathMeasures = new PathMeasure[4][];
    private Paint paint1, paint2;
    public MyAnimationView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MyAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){
        setBackgroundColor(getResources().getColor(R.color.trans));

        paint1 = new Paint();
        paint1.setStyle(Paint.Style.FILL);
        paint1.setColor(getResources().getColor(R.color.white));
        paint1.setAntiAlias(true);

        paint2 = new Paint();
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(getResources().getColor(R.color.white_60));
        paint2.setAntiAlias(true);
    }

    private float temp;  //进度从0到1
    private int tempLength;
    private float[] pos = new float[2];
    private int alpha;
    private float rate = 1.5f;  //速率从1.5到0
    private final int times = (int) (DURATION / 50);
    private int moveLength[][] = new int[WAVE_COUNT][];  //记录移动了多少距离
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int k = 0 ; k < WAVE_COUNT; k++){

            for (int i = 0; i < starCount[k]; i++){
                temp = circleTime * 1.0f / DURATION;
                rate = (float) ((1 - temp) * 1.5);
                /**本次需要移动的距离*/
                int length = (int) (pathMeasures[k][i].getLength() / times * rate);
                tempLength = moveLength[k][i] + length;

                pathMeasures[k][i].getPosTan(tempLength, pos, null);
                moveLength[k][i] = tempLength;

                alpha = (int) (255 * (1 - temp));
                paint1.setAlpha(alpha);
                paint2.setAlpha(alpha);

                canvas.drawCircle(pos[0], pos[1], 3, paint2);
                canvas.drawCircle(pos[0], pos[1], 2, paint1);
            }
        }

    }

    public void startAni(){
        circleTime = 0;
        Random random = new Random();
        int leftCount = random.nextInt(10) + 20;
        int rightCount = random.nextInt(10) + 20;
        for (int k = 0; k < WAVE_COUNT; k ++){
            starCount[k] = leftCount + rightCount;
            int stattX ,startY;
            float xielv;
            paths[k] = new Path[starCount[k]];
            pathMeasures[k] = new PathMeasure[starCount[k]];
            moveLength[k] = new int[starCount[k]];
            /**圆心坐标*/
            int radiusX = getWidth() / 2, radiusY = getHeight() / 2;
            /**大圆半径,扩散的范围*/
            int r = 2 * getHeight();
            for (int i = 0; i < starCount[k]; i++){
                paths[k][i] = new Path();
                /**中心两边各随机一定数量的起始坐标*/
                if (i < leftCount){
                    stattX = getWidth() / 2 - random.nextInt(getHeight() / 2);
                }else {
                    stattX = getWidth() / 2 + random.nextInt(getHeight() / 2);
                }
                startY = random.nextInt(getHeight());

                paths[k][i].moveTo(radiusX, radiusY);//起始坐标

                xielv = (startY - radiusY) * 1.0f / (stattX - radiusX);

                /**随机长度*/
                int length = random.nextInt(r / 5) +  r  * (5 - k - 1) / 5;

                int endx = (int) Math.sqrt(Math.pow(length, 2) * 1.0f / (Math.pow(xielv, 2) + 1));
                int endy = (int) Math.abs(endx * xielv);
                /**根据斜率算出终点坐标*/
                if (stattX > radiusX){
                    endx = radiusX + endx;
                }else {
                    endx = radiusX - endx;
                }
                if (startY < radiusY){
                    endy = radiusY - endy;
                }else {
                    endy = radiusY + endy;
                }
                paths[k][i].lineTo(endx, endy);
                pathMeasures[k][i] = new PathMeasure(paths[k][i], false);
            }
        }
        handler.sendEmptyMessage(100);
    }


    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:  //移动
                    postInvalidate();
                    circleTime += 50;
                    if (circleTime < DURATION){
                        sendEmptyMessageDelayed(100, 50);
                    }
                    break;
            }
        }
    };

}
