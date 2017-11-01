package yll.self.testapp.userinterface.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;


import yll.self.testapp.R;
import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 17/10/30.
 */

public class TextUpAniView extends View {


    private String[] s = new String[]{"嗨？", "你好吗？", "好好好好个毛！", "啊啊啊啊"};
    private Paint paint, bitmapPaint, bgPaint;

    private final int BACKGROUND_ANI_DURATION = 1000; //背景的动画执行时间
    private final int ITEM_ANI_DURATION = 2000;  //文字动画执行时间
    private final int ITEM_INTERVAL = 400;       //多少ms之后开始下一行文字动画
    private final int DRAW_INTERVAL = 20;        //重绘间隔
    private DecelerateInterpolator interpolator;

    /** 头像 绘制是左上角的坐标*/
    private int avatarX, avatarY, backgroundY = -1;
    /** 文字 绘制起点的坐标*/
    private int[] x;
    private int[] y;
    /** 文字行数*/
    private int textLines;
    /** 头像宽高*/
    private int avatarWH;
    private Bitmap bitmap;
    private int itemHeight;
    /** 头像和昵称之间间距 */
    private int spaceBetweenAvatarAndName;


    public TextUpAniView(Context context) {
        super(context);
        init();
    }

    public TextUpAniView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setTextSize(UtilsManager.dip2px(getContext(), 15));

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(getResources().getColor(R.color.black_19));

        interpolator = new DecelerateInterpolator(3);

        avatarWH = UtilsManager.dip2px(getContext(), 30);
        itemHeight = UtilsManager.dip2px(getContext(), 30);
        spaceBetweenAvatarAndName = UtilsManager.dip2px(getContext(), 10);

        initCircleBitmap();

        textLines = s.length;
        post(new Runnable() {
            @Override
            public void run() {
                x = new int[textLines];
                for (int i = 0 ; i < textLines; i++){
                    if (i == 0){ //预留头像的位置
                        avatarX = (getWidth() -  UtilsManager.getTheTextNeedWidth(paint, s[i]) - avatarWH - spaceBetweenAvatarAndName) / 2;
                        x[i] = avatarX + avatarWH + spaceBetweenAvatarAndName;
                    }else {
                        x[i] = (getWidth() -  UtilsManager.getTheTextNeedWidth(paint, s[i])) / 2;
                    }
                }
            }
        });
    }


    private BitmapShader shader;
    private Matrix mShaderMatrix = new Matrix();
    private void initCircleBitmap(){
        bitmapPaint = new Paint();
        bitmapPaint.setAntiAlias(true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_cover_system);
        shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapPaint.setShader(shader);

    }


    private RectF mDrawableRectF = new RectF();
    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;

        mDrawableRectF.set(0, 0, avatarWH, avatarWH);
        mShaderMatrix.set(null);

        if (bitmap.getWidth() * mDrawableRectF.height() > mDrawableRectF.width() * bitmap.getHeight()) {
            scale = mDrawableRectF.height() / (float) bitmap.getHeight();
            dx = (mDrawableRectF.width() - bitmap.getWidth() * scale) * 0.5f;
        } else {
            scale = mDrawableRectF.width() / (float) bitmap.getWidth();
            dy = (mDrawableRectF.height() - bitmap.getHeight() * scale) * 0.5f;
        }
        mShaderMatrix.setScale(scale, scale);
        /**不设置 位移 那么bitmap总是从画布的 (0, 0) 位置开始绘制*/
        mShaderMatrix.postTranslate(dx + avatarX, dy + avatarY);
        shader.setLocalMatrix(mShaderMatrix);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (backgroundY != -1){
            canvas.drawRect(0, backgroundY, getWidth(), getHeight(), bgPaint);
        }

        if (avatarY != 0){
            updateShaderMatrix();
            canvas.drawCircle(avatarX + avatarWH / 2 , avatarY + avatarWH / 2 , avatarWH / 2, bitmapPaint);
        }

        if (y != null) {
            for (int i = 0; i < textLines; i++) {
                if (y[i] != 0) {
                    canvas.drawText(s[i], x[i], y[i], paint);
                }
            }
        }
    }


    public void startAni(){
        time = 0;
        y = new int[textLines];
        avatarY = 0;
        handler.removeMessages(BACKGROUND_ANIMATION);
        handler.removeMessages(TEXT_ANIMATION);
        handler.sendEmptyMessage(BACKGROUND_ANIMATION);
    }


    private int time = 0;

    private final int BACKGROUND_ANIMATION = 99;
    private final int TEXT_ANIMATION = 100;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case BACKGROUND_ANIMATION:
                    time += DRAW_INTERVAL;

                    int bgFromY = getHeight();
                    int bgToY = 0;

                    backgroundY = (int) (bgFromY - (bgFromY - bgToY) * interpolator.getInterpolation(time * 1f / BACKGROUND_ANI_DURATION));
                    invalidate();
                    if (time <= BACKGROUND_ANI_DURATION){
                        handler.sendEmptyMessageDelayed(BACKGROUND_ANIMATION, DRAW_INTERVAL);
                    }else {
                        handler.sendEmptyMessageDelayed(TEXT_ANIMATION, DRAW_INTERVAL);
                    }

                    break;
                case TEXT_ANIMATION:
                    int fromY = getHeight() - UtilsManager.dip2px(getContext(), 10);
                    int toY;
                    boolean bShouldDraw = false;
                    time += DRAW_INTERVAL;
                    for (int i = 0; i < textLines; i++){
                        //动画已经持续的时间
                        int time1 = time - i * ITEM_INTERVAL - BACKGROUND_ANI_DURATION;
                        if (time1 > 0 && time1 <= ITEM_ANI_DURATION){
                            toY = itemHeight * i + getHeight() / 4;
                            y[i] = (int) (fromY - (fromY - toY) * interpolator.getInterpolation(time1 * 1f / ITEM_ANI_DURATION));
                            bShouldDraw = true;
                            if (i == 0){
                                avatarY = y[i] - UtilsManager.dip2px(getContext(), 25);
                            }
                        }
                    }
                    if (bShouldDraw){
                        invalidate();
                        handler.sendEmptyMessageDelayed(TEXT_ANIMATION, DRAW_INTERVAL);
                    }
                    break;
                default:
                    break;
            }
        }
    };

}
