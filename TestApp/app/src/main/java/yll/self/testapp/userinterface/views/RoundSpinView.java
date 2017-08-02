package yll.self.testapp.userinterface.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import yll.self.testapp.R;
import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 17/7/28.
 */

public class RoundSpinView extends View {
    private String TAG = "RoundSpinView";

    private Activity act;

    private Paint mPaint = new Paint();

    // 图片列表列表
    private BigStone[] mStones;
    // 中心点stone
    private BigStone centerStones;
    // 数目
    private static final int STONE_COUNT = 4;
    // 圆心坐标
    private int mPointX = 0, mPointY = 0;
    // 半径
    private int mRadius = 0;
    // 每两个点间隔的角度
    private int mDegreeDelta;
    /** 触摸时 bitmap*/
    private Bitmap lockscre_pressed_bit ;
    /** 未触摸时 bitmap*/
    private Bitmap lockscreen_normal_bit;
    /** 选中时bitmap */
    private Bitmap select_bg_bit;
    //右下左上
    private int[] normal_img = { R.drawable.icon_login_right, R.drawable.icon_login_bottom, R.drawable.icon_login_left, R.drawable.icon_login_top1};
    private int[] select_img = { R.drawable.selected_setting, R.drawable.selected_setting, R.drawable.selected_setting, R.drawable.selected_setting };
    private Bitmap[] normal_img_bitmap = new Bitmap[STONE_COUNT];
    private Bitmap[] select_img_bitmap = new Bitmap[STONE_COUNT];

    public RoundSpinView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RoundSpinView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundSpinView(Activity act) {
        super(act);
        this.act = act;
    }

    public void init() {
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(0);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        lockscre_pressed_bit = BitmapFactory.decodeResource(getResources(), R.drawable.icon_information);
        lockscreen_normal_bit = BitmapFactory.decodeResource(getResources(), R.drawable.icon_friend_circle);
        select_bg_bit = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        for (int index = 0; index < STONE_COUNT; index++) {
            normal_img_bitmap[index] = BitmapFactory.decodeResource(getResources(), normal_img[index]);
            select_img_bitmap[index] = BitmapFactory.decodeResource(getResources(), select_img[index]);
        }

//        setBackgroundResource(R.drawable.bg1);

        mPointX = getWidth() / 2;
        mPointY = getHeight() / 3 * 2;
        mRadius = UtilsManager.dip2px(act, 50);

        setupStones();
        computeCoordinates();
    }

    private boolean isInit = false;

    /**
     * 初始化每个点
     */
    private void setupStones() {
        mStones = new BigStone[STONE_COUNT];
        BigStone stone;
        int angle = 0;
        mDegreeDelta = 360 / STONE_COUNT;

        centerStones = new BigStone();
        centerStones.angle = angle;
        centerStones.x = mPointX;
        centerStones.y = mPointY;

        for (int index = 0; index < STONE_COUNT; index++) {
            stone = new BigStone();
            stone.angle = angle;
            angle += mDegreeDelta;

            mStones[index] = stone;
        }
    }


    /**
     * 计算每个点的坐标
     */
    private void computeCoordinates() {
        BigStone stone;
        for (int index = 0; index < STONE_COUNT; index++) {
            stone = mStones[index];
            stone.x = mPointX + (float) ((mRadius + select_bg_bit.getWidth()/2) * Math.cos(stone.angle * Math.PI / 180));
            stone.y = mPointY + (float) ((mRadius + select_bg_bit.getHeight()/2) * Math.sin(stone.angle * Math.PI / 180));
            stone.bitmap = normal_img_bitmap[index];
            stone.angle = computeCurrentAngle(stone.x, stone.y);
        }
    }

    /**
     * 计算坐标点与圆心直径的角度
     *
     * @param x
     * @param y
     * @return
     */
    private int computeCurrentAngle(float x, float y) {
        float distance = (float) Math.sqrt(((x - mPointX) * (x - mPointX) + (y - mPointY) * (y - mPointY)));
        int degree = (int) (Math.acos((x - mPointX) / distance) * 180 / Math.PI);
        if (y < mPointY) {
            degree = -degree;
        }
        return degree;
    }

    private boolean isPressLock = false;// 标记是否按住中心锁图片

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x, y;
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                isPressLock = isPressLockPic(x, y);
                setIsVisible(isPressLock);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                // 算出当前坐标和圆心的距离
                centerStones.angle = computeCurrentAngle(x, y);
                if (isPressLock) {
                    centerStones.bitmap = lockscre_pressed_bit;
                    computeCoordinates();
                    if (getDistance(x, y) <= mRadius) {
                        centerStones.x = x;
                        centerStones.y = y;
                    } else {// 大于直径时根据角度算出坐标
                        centerStones.x = mPointX + (float) ((mRadius) * Math.cos(centerStones.angle * Math.PI / 180));
                        centerStones.y = mPointY + (float) ((mRadius) * Math.sin(centerStones.angle * Math.PI / 180));
                        if (centerStones.angle <= (mStones[0].angle + 15) && centerStones.angle >= (mStones[0].angle - 15)) {
                            mStones[0].bitmap = select_img_bitmap[0];
                            centerStones.bitmap = select_bg_bit;
                            centerStones.x = mStones[0].x;
                            centerStones.y = mStones[0].y;
                        }
                        if (centerStones.angle <= (mStones[1].angle + 15) && centerStones.angle >= (mStones[1].angle - 15)) {
                            mStones[1].bitmap = select_img_bitmap[1];
                            centerStones.bitmap = select_bg_bit;
                            centerStones.x = mStones[1].x;
                            centerStones.y = mStones[1].y;
                        }
                        if (centerStones.angle <= (mStones[2].angle + 15) && centerStones.angle >= (mStones[2].angle - 15)) {
                            mStones[2].bitmap = select_img_bitmap[2];
                            centerStones.bitmap = select_bg_bit;
                            centerStones.x = mStones[2].x;
                            centerStones.y = mStones[2].y;
                        }
                        if (centerStones.angle <= (mStones[3].angle + 15) && centerStones.angle >= (mStones[3].angle - 15)) {
                            mStones[3].bitmap = select_img_bitmap[3];
                            centerStones.bitmap = select_bg_bit;
                            centerStones.x = mStones[3].x;
                            centerStones.y = mStones[3].y;
                        }
                    }
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:
                //处理Action_Up事件：  判断是否解锁成功，成功则结束我们的Activity ；否则 ，缓慢回退该图片。
                handleActionUpEvent(event);
                break;
        }
        return true;
    }

    private void handleActionUpEvent(MotionEvent event){
        boolean islocksuc = false;// 是否解锁成功
        float x = event.getX();
        float y = event.getY();
        centerStones.angle = computeCurrentAngle(x, y);
        if (getDistance(x, y) >= mRadius) {
            if (centerStones.angle <= (mStones[0].angle + 15) && centerStones.angle >= (mStones[0].angle - 15) && mStones[0].isVisible) {
                islocksuc = true;
                Log.i(TAG,"解锁-短信 跳转到短信界面");
                act.finish();
            }
            if (centerStones.angle <= (mStones[1].angle + 15) && centerStones.angle >= (mStones[1].angle - 15) && mStones[1].isVisible) {
                islocksuc = true;
                Log.i(TAG,"解锁-解锁");
                act.finish();
            }
            if (centerStones.angle <= (mStones[2].angle + 15) && centerStones.angle >= (mStones[2].angle - 15) && mStones[2].isVisible) {
                islocksuc = true;
                Log.i(TAG,"解锁-电话 跳转到电话界面");
                act.finish();
            }
            if (centerStones.angle <= (mStones[3].angle + 15) && centerStones.angle >= (mStones[3].angle - 15) && mStones[3].isVisible) {
                islocksuc = true;
                Log.i(TAG,"解锁-相机 跳转到相机界面");
                act.finish();
            }
        }
        if(!islocksuc) { // 未解锁成功
            backToCenter();
        }
    }


    //回退动画时间间隔值
    private static int BACK_DURATION = 20 ;   // 20ms
    //水平方向前进速率
    private static float VE_HORIZONTAL = 0.8f ;  //0.1dip/ms
    private Handler mHandler =new Handler (Looper.getMainLooper());

    private void backToCenter() {
        mHandler.postDelayed(BackDragImgTask, BACK_DURATION);
    }

    //通过延时控制当前绘制bitmap的位置坐标
    private Runnable BackDragImgTask = new Runnable(){
        public void run(){
            //一下次Bitmap应该到达的坐标值
            if(centerStones.x>=mPointX){
                centerStones.x = centerStones.x - BACK_DURATION * VE_HORIZONTAL;
                if(centerStones.x<mPointX){
                    centerStones.x = mPointX;
                }
            } else {
                centerStones.x = centerStones.x + BACK_DURATION * VE_HORIZONTAL;
                if(centerStones.x>mPointX){
                    centerStones.x = mPointX;
                }
            }
            centerStones.y = mPointY + (float) ((centerStones.x-mPointX) * Math.tan(centerStones.angle * Math.PI / 180));

            invalidate();//重绘
            boolean shouldEnd = getDistance(centerStones.x, centerStones.y) <= 8 ;
            if(!shouldEnd)
                mHandler.postDelayed(BackDragImgTask, BACK_DURATION);
            else { //复原初始场景
                centerStones.x = mPointX;
                centerStones.y = mPointY;
                isPressLock = false;
                setIsVisible(isPressLock);
                invalidate();
            }
        }
    };

    /**
     * 获取坐标点与圆心直径的距离
     * @param x
     * @param y
     * @return
     */
    private float getDistance(float x, float y) {
        return (float) Math.sqrt(((x - mPointX) * (x - mPointX) + (y - mPointY) * (y - mPointY)));
    }

    /**
     * 判断手指按下的时候是否按住中心锁图片
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isPressLockPic(float x, float y) {
        float l = centerStones.x - centerStones.bitmap.getWidth() / 2;
        float r = centerStones.x + centerStones.bitmap.getWidth() / 2;
        float t = centerStones.y - centerStones.bitmap.getHeight() / 2;
        float b = centerStones.y + centerStones.bitmap.getHeight() / 2;
        if (x >= l && x <= r && y >= t && y <= b) {
            return true;
        }
        return false;
    }

    @Override
    public void onDraw(Canvas canvas) {

        if (!isInit){
            isInit = true;
            init();
        }

        if (isPressLock) {// 手指按下状态
            canvas.drawCircle(mPointX, mPointY, mRadius, mPaint);// 画圆
            drawInCenter(canvas, centerStones.bitmap, centerStones.x, centerStones.y);// 画中心锁图片
            for (int index = 0; index < STONE_COUNT; index++) {
                if (!mStones[index].isVisible)
                    continue;
                drawInCenter(canvas, mStones[index].bitmap, mStones[index].x, mStones[index].y);
            }
        } else {
            centerStones.bitmap = lockscreen_normal_bit;
            drawInCenter(canvas, centerStones.bitmap, centerStones.x, centerStones.y);// 画中心锁图片
        }
    }

    /**
     * 把中心点放到中心处
     *
     * @param canvas
     * @param bitmap
     * @param left
     * @param top
     */
    void drawInCenter(Canvas canvas, Bitmap bitmap, float left, float top) {
        canvas.drawBitmap(bitmap, left - bitmap.getWidth() / 2, top - bitmap.getHeight() / 2, null);
    }

    private void setIsVisible(boolean isVisible){
        for (int index = 0; index < STONE_COUNT; index++) {
            mStones[index].isVisible = isVisible;
        }
    }

    class BigStone {
        // 图片
        public Bitmap bitmap;
        // 角度
        public int angle;
        // x坐标
        public float x;
        // y坐标
        public float y;
        // 是否可见
        public boolean isVisible = false;
    }
}
