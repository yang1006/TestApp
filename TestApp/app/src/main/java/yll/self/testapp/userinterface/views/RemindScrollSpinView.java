package yll.self.testapp.userinterface.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import yll.self.testapp.R;
import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 17/7/28.
 * 提醒界面 上下滑动解锁界面
 */

public class RemindScrollSpinView extends View {

    /**
     * 上下文字的画笔
     */
    private Paint topTextPaint, bottomTextPaint;
    /**
     * 绘制圆的画笔
     */
    private Paint paint;
    /**
     * 中间圆 默认能滚动的范围
     */
    private int radius;
    /**
     * 圆心坐标
     */
    private int mPointX, mPointY;
    /**
     * 顶部 中间和底部的 图片对象 保存坐标和bitmap
     */
    private Point topPoint, centerPoint, bottomPoint;
    /**
     * 顶部和顶部的文字
     */
    private String topText = "上滑解锁", bottomText = "下滑解锁";

    /**顶部底部 未选中和选中时候的图片*/
    private Bitmap[] normalBitmaps = new Bitmap[2];
    private Bitmap[] selectedBitmaps = new Bitmap[2];

    /** 中间未触摸、触摸和选中时的 bitmap*/
    private Bitmap centerNormalBitmap, centerPressedBitmap, centerSelectedBitmap;

    /** 顶部和底部 图标和文字的间距*/
    private int textSpace;

    /** 解锁成功的回调*/
    private OnUnlockListener onUnlockListener;

    public RemindScrollSpinView(Context context) {
        super(context);
    }

    public RemindScrollSpinView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**在第一次 onDraw 时初始化*/
    private boolean isHadInit = false;
    private void init(){
        topTextPaint = new Paint();
        topTextPaint.setStyle(Paint.Style.FILL);
        topTextPaint.setTextSize(UtilsManager.dip2px(getContext(), 15));
        topTextPaint.setAntiAlias(true);
        topTextPaint.setColor(getContext().getResources().getColor(R.color.black));

        bottomTextPaint = new Paint();
        bottomTextPaint.setStyle(Paint.Style.FILL);
        bottomTextPaint.setTextSize(UtilsManager.dip2px(getContext(), 15));
        bottomTextPaint.setAntiAlias(true);
        bottomTextPaint.setColor(getContext().getResources().getColor(R.color.black));

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(0);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        mPointX = getWidth() / 2;
        mPointY = getHeight() / 2;

        radius = UtilsManager.dip2px(getContext(), 50);
        textSpace = UtilsManager.dip2px(getContext(), 18);

        normalBitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_login_top1);
        normalBitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.icon_login_bottom);

        selectedBitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.selected_setting);
        selectedBitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.selected_setting);

        centerNormalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_information);
        centerPressedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_friend_circle);
        centerSelectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        initPoints();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width  = MeasureSpec.getSize(widthMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        int minWidth = UtilsManager.dip2px(getContext(), 200);
        int minHeight = UtilsManager.dip2px(getContext(), 200);
        if (wMode == MeasureSpec.AT_MOST && hMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(minWidth, minHeight);
        }else if (wMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(minWidth, height);
        }else if (hMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(width, minHeight);
        }
    }

    /**
     * 初始化每个点
     */
    private void initPoints() {
        centerPoint = new Point();
        centerPoint.x = mPointX;
        centerPoint.y = mPointY;
        centerPoint.angle = 0;

        topPoint = new Point();
        topPoint.angle = -90;
        bottomPoint = new Point();
        bottomPoint.angle = 90;
        computeTopAndBottom();
    }

    /**
     * 计算顶部和底部点的坐标
     */
    private void computeTopAndBottom() {
        topPoint.x = mPointX;
        topPoint.y = mPointY - radius - selectedBitmaps[0].getHeight()/ 2;
        topPoint.bitmap = normalBitmaps[0];
        topPoint.angle = computeCurrentAngle(topPoint.x, topPoint.y);

        bottomPoint.x = mPointX;
        bottomPoint.y = mPointY + radius + + selectedBitmaps[0].getHeight() / 2;
        bottomPoint.bitmap = normalBitmaps[0];
        bottomPoint.angle = computeCurrentAngle(bottomPoint.x, bottomPoint.y);
    }

    /**
     * 计算坐标点与圆心直径的角度
     */
    private int computeCurrentAngle(float x, float y) {
        float distance = (float) Math.sqrt(((x - mPointX) * (x - mPointX) + (y - mPointY) * (y - mPointY)));
        int degree = (int) (Math.acos((x - mPointX) / distance) * 180 / Math.PI);
        if (y < mPointY) {
            degree = -degree;
        }
        return degree;
    }

    /**
     * 判断手指按下的时候是否按住中心锁图片
     */
    private boolean isPressLockPic(float x, float y) {
        float l = centerPoint.x - centerPoint.bitmap.getWidth() / 2;
        float r = centerPoint.x + centerPoint.bitmap.getWidth() / 2;
        float t = centerPoint.y - centerPoint.bitmap.getHeight() / 2;
        float b = centerPoint.y + centerPoint.bitmap.getHeight() / 2;
        if (x >= l && x <= r && y >= t && y <= b) {
            return true;
        }
        return false;
    }

    /**
     * 获取坐标点与圆心的距离
     */
    private float getDistance(float x, float y) {
        return (float) Math.sqrt(((x - mPointX) * (x - mPointX) + (y - mPointY) * (y - mPointY)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isHadInit){
            isHadInit = true;
            init();
        }
        if (!isPressed){
            centerPoint.bitmap = centerNormalBitmap;
        }

        /**绘制中心的圆*/
        drawBitmapInCenter(canvas, centerPoint.bitmap, centerPoint.x, centerPoint.y);
        /**绘制上下的bitmap*/
        drawBitmapInCenter(canvas, topPoint.bitmap, topPoint.x, topPoint.y);
        drawBitmapInCenter(canvas, bottomPoint.bitmap, bottomPoint.x, bottomPoint.y);
        /**绘制上下的文字*/
        drawTopAndBottomText(canvas);

    }

    /**传中心点坐标 绘制bitmap到正确的位置*/
    private void drawBitmapInCenter(Canvas canvas, Bitmap bitmap, float x, float y){
        canvas.drawBitmap(bitmap, x - bitmap.getWidth() / 2, y - bitmap.getHeight() / 2, null);
    }

    /**传中心点坐标 绘制文字到正确的位置*/
    private void drawTopAndBottomText(Canvas canvas){
        if (TextUtils.isEmpty(topText) && TextUtils.isEmpty(bottomText)){
            return;
        }
        canvas.drawText(topText, topPoint.x - UtilsManager.getTheTextNeedWidth(topTextPaint, topText) / 2,
                topPoint.y - textSpace, topTextPaint);
        canvas.drawText(bottomText, bottomPoint.x - UtilsManager.getTheTextNeedWidth(bottomTextPaint, bottomText) / 2,
                bottomPoint.y + textSpace + bottomTextPaint.getTextSize(), bottomTextPaint);
    }

    /**是否按住了中间的圆*/
    private boolean isPressed = false;
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x, y;
        x = event.getX();
        y = event.getY();
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                isPressed = isPressLockPic(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                centerPoint.angle = computeCurrentAngle(x, y);
                if (isPressed){
                    centerPoint.bitmap = centerPressedBitmap;
                    topPoint.bitmap = normalBitmaps[0];
                    bottomPoint.bitmap = normalBitmaps[1];
                    if (getDistance(x, y) <= radius) {
                        centerPoint.x = x;
                        centerPoint.y = y;
                    } else {// 大于半径时根据角度算出坐标
                        centerPoint.x = mPointX + (float) ((radius) * Math.cos(centerPoint.angle * Math.PI / 180));
                        centerPoint.y = mPointY + (float) ((radius) * Math.sin(centerPoint.angle * Math.PI / 180));
                        if (centerPoint.angle <= (topPoint.angle + 15) && centerPoint.angle >= (topPoint.angle - 15)) {
                            topPoint.bitmap = selectedBitmaps[0];
                            centerPoint.bitmap = centerSelectedBitmap;
                            centerPoint.x = topPoint.x;
                            centerPoint.y = topPoint.y;
                        }else if (centerPoint.angle <= (bottomPoint.angle + 15) && centerPoint.angle >= (bottomPoint.angle - 15)){
                            bottomPoint.bitmap = selectedBitmaps[1];
                            centerPoint.bitmap = centerSelectedBitmap;
                            centerPoint.x = bottomPoint.x;
                            centerPoint.y = bottomPoint.y;
                        }
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                //处理Action_Up事件：  判断是否解锁成功；不成功时缓慢回退该图片到中心。
                handleActionUpEvent(event);
                break;
        }
        return true;
    }

    /** 判断是否解锁成功；不成功时缓慢回退该图片到中心。*/
    private void handleActionUpEvent(MotionEvent event){
        boolean isUnlockSuccess = false;// 是否解锁成功
        float x = event.getX();
        float y = event.getY();
        centerPoint.angle = computeCurrentAngle(x, y);
        if (getDistance(x, y) >= radius) {
            if (centerPoint.angle <= (topPoint.angle + 15) && centerPoint.angle >= (topPoint.angle - 15)) {
                isUnlockSuccess = true;
                Log.e("yll", "上滑解锁成功");
                if (onUnlockListener != null){
                    onUnlockListener.onUnlock(0);
                }
            }else if (centerPoint.angle <= (bottomPoint.angle + 15) && centerPoint.angle >= (bottomPoint.angle - 15)){
                isUnlockSuccess = true;
                Log.e("yll", "下滑解锁成功");
                if (onUnlockListener != null){
                    onUnlockListener.onUnlock(1);
                }
            }
        }
        if(!isUnlockSuccess) { // 未解锁成功
            backToCenter();
        }
    }

    //回退动画时间间隔值
    private int BACK_DURATION = 20;   // 20ms
    //水平方向前进速率
    private float VE_HORIZONTAL = 0.8f;
    private Handler mHandler =new Handler (Looper.getMainLooper());
    /** 将圆逐渐绘制到中心 */
    private void backToCenter() {
        mHandler.postDelayed(backToCenter, BACK_DURATION);
    }
    private Runnable backToCenter = new Runnable(){
        public void run(){
            //一下次Bitmap应该到达的坐标值
            if (centerPoint.x >= mPointX) {
                centerPoint.x = centerPoint.x - BACK_DURATION * VE_HORIZONTAL;
                if (centerPoint.x < mPointX) {
                    centerPoint.x = mPointX;
                }
            } else {
                centerPoint.x = centerPoint.x + BACK_DURATION * VE_HORIZONTAL;
                if (centerPoint.x > mPointX) {
                    centerPoint.x = mPointX;
                }
            }
            centerPoint.y = mPointY + (float) ((centerPoint.x - mPointX) * Math.tan(centerPoint.angle * Math.PI / 180));
            postInvalidate();//重绘
            boolean shouldEnd = getDistance(centerPoint.x, centerPoint.y) <= 8;
            if (!shouldEnd) {
                mHandler.postDelayed(backToCenter, BACK_DURATION);
            } else { //复原初始场景
                centerPoint.x = mPointX;
                centerPoint.y = mPointY;
                isPressed = false;
                postInvalidate();
            }
        }
    };

    /**设置解锁成功的回调*/
    public void setOnUnlockListener(OnUnlockListener onUnlockListener) {
        this.onUnlockListener = onUnlockListener;
    }

    class Point {
        /**
         * 图片
         */
        Bitmap bitmap;
        /**
         * 角度
         */
        int angle;
        /**
         * x y坐标
         */
        float x, y;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // TODO: @yll 17/7/28 回收bitmap
    }

    public interface OnUnlockListener{
        /**@param direction 0上 1下*/
        void onUnlock(int direction);
    }
}
