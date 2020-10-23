package yll.self.testapp.opengl.newstudy.multishape;

import android.content.Context;
import android.opengl.GLES20;

import yll.self.testapp.opengl.newstudy.BaseShader;
import yll.self.testapp.utils.LogUtil;

public class ShaderSquare extends BaseShader {

    private static final String TAG = "ShaderSquare";

    public int mVPosition;
    public int mAColor;

    public int mProgram;

    public ShaderSquare(Context context) {
        super(context);
    }


    public void initShader() {
        int vertexShader = createShader(GLES20.GL_VERTEX_SHADER, loadStringFromAssertFile("SquareVertexShader.glsl"));
        int fragmentShader = createShader(GLES20.GL_FRAGMENT_SHADER, loadStringFromAssertFile("SquareFragmentShader.glsl"));

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);

        GLES20.glLinkProgram(mProgram);

        int[] status = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, status, 0);
        if (status[0] != GLES20.GL_TRUE) {
            LogUtil.e(TAG, "could not link program " + GLES20.glGetProgramInfoLog(mProgram));
            mProgram = 0;
            GLES20.glDeleteProgram(mProgram);
            throw new NullPointerException("ShaderSquare 创建着色器程序失败");
        }

        mVPosition = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mAColor = GLES20.glGetAttribLocation(mProgram, "aColor");
    }



}
