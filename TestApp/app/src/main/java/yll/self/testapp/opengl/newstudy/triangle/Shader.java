package yll.self.testapp.opengl.newstudy.triangle;

import android.content.Context;
import android.opengl.GLES20;

import java.io.InputStream;

import yll.self.testapp.utils.LogUtil;

public class Shader {

    private final static String TAG = "Shader";
    private Context mContext;
    public int mProgram;

    public int mAPostion;
    public int mAColor;

    public Shader(Context context) {
        mContext = context;
    }

    public void initShader() {
        //加载顶点着色器
        int vertexShader = createShader(GLES20.GL_VERTEX_SHADER, loadStringFromAssertFile("TriangleVertexShader.glsl"));
        //加载片源着色器
        int fragmentShader = createShader(GLES20.GL_FRAGMENT_SHADER, loadStringFromAssertFile("TriangleFragmentShader.glsl"));
        //创建着色器程序
        mProgram = GLES20.glCreateProgram();
        //向程序中加入顶点着色器和片源着色器
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        //链接程序
        GLES20.glLinkProgram(mProgram);

        //存放链接成功program数量的数组
        int[] linkStatus = new int[1];
        //获取program 的链接情况
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] != GLES20.GL_TRUE) {
            LogUtil.e(TAG, "could not link program " + GLES20.glGetProgramInfoLog(mProgram));
            mProgram = 0;
            GLES20.glDeleteProgram(mProgram);
            throw new NullPointerException("创建着色器程序失败");
        }

        mAPostion = GLES20.glGetAttribLocation(mProgram, "aPosition");
        mAColor = GLES20.glGetAttribLocation(mProgram, "aColor");

    }


    private int createShader(int type, String codeSource) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, codeSource);
        GLES20.glCompileShader(shader);
        return shader;
    }


    private String loadStringFromAssertFile(String fName) {

        StringBuilder result = new StringBuilder();
        try {
            InputStream is = mContext.getResources().getAssets().open(fName);
            int ch;
            byte[] buffer = new byte[1024];
            while (-1 != (ch = is.read(buffer))) {
                result.append(new String(buffer, 0, ch));
            }
        } catch (Exception e) {
            return null;
        }
        String str = result.toString().replaceAll("\\r\\n", "\n");
        LogUtil.e("yll", "str \n" + str);
        return result.toString().replaceAll("\\r\\n", "\n");
    }
}
