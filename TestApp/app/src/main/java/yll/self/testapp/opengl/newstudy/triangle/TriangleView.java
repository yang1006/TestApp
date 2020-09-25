package yll.self.testapp.opengl.newstudy.triangle;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TriangleView extends GLSurfaceView implements GLSurfaceView.Renderer{

    private Shader mShader;
    private Shape mShape;

    public TriangleView(Context context) {
        super(context);
        init();
    }

    public TriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mShader = new Shader(getContext());
        mShader.initShader();
        mShape = new Shape(getContext());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(0, 0, 0, 0);
        drawTriangle();
    }

    //绘制一个三角形
    private void drawTriangle() {
        GLES20.glUseProgram(mShader.mProgram);
        //启用顶点句柄
        GLES20.glEnableVertexAttribArray(mShader.mAPostion);
        //画笔设置顶点数据
        GLES20.glVertexAttribPointer(mShader.mAPostion, 2, GLES20.GL_FLOAT, false, 0, mShape.getVertexFloatBuffer());

        GLES20.glEnableVertexAttribArray(mShader.mAColor);
        GLES20.glVertexAttribPointer(mShader.mAColor, 4, GLES20.GL_FLOAT, false, 0, mShape.getColorFloatBuffer());

        //开始绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, mShape.mTriangleVertex.length / 2);

        //绘制 禁用顶点
        GLES20.glDisableVertexAttribArray(mShader.mAPostion);
        GLES20.glDisableVertexAttribArray(mShader.mAColor);
    }
}
