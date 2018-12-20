package yll.self.testapp.opengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import yll.self.testapp.BaseActivity;
import yll.self.testapp.utils.UtilsManager;

/**
 * https://blog.piasy.com/2016/06/07/Open-gl-es-android-2-part-1/
 * */
public class HelloRectangleActivity extends BaseActivity {

    private final int CONTEXT_CLIENT_VERSION = 3;
    private GLSurfaceView mGLSurfaceView;
    private final String TAG = "HelloTriangleActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaceView = new GLSurfaceView(this);
        if (detectOpenGLES30()){
            mGLSurfaceView.setEGLContextClientVersion(CONTEXT_CLIENT_VERSION);
            mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
            mGLSurfaceView.setRenderer(new HelloRectangleRenderer());
            mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            setContentView(mGLSurfaceView);
        }else {
            UtilsManager.log(TAG, "不支持OpenGL ES 3.0");
            finish();
        }

    }

    private boolean detectOpenGLES30(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = activityManager.getDeviceConfigurationInfo();
        return info.reqGlEsVersion >= 0x30000;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }

    /**
     * 绘制两个三角形时，我们可以指定 6 个顶点的坐标，但实际上只有 4 个不同的点，这样有点浪费，
     * OpenGL 支持用另一种方式完成绘制：用一个数组保存顶点数据，用另一个数组保存顶点的绘制顺序
     * */
    private class HelloRectangleRenderer implements GLSurfaceView.Renderer {

        /**矩形的四个顶点*/
        private final float VERTICES[] = {
                1f, 1f, 0,
                -1f, 1f, 0,
                -1f, -1f, 0,
                1f, -1f, 0
        };

        private final short[] VERTEX_INDEX = {0, 1, 2, 0, 2, 3};
        private ShortBuffer mVertexIndexBuffer;
        private int mProgram;
        //变量 gl_Position 表示顶点的位置，每个顶点都会执行一遍顶点着色器程序
        private final String VERTEX_SHADER =
//                         "#version 300 es \n" +
                         "attribute vec4 vPosition; \n " +
                         "uniform mat4 uMVPMatrix; \n" +
                         "void main() { \n" +
                         " gl_Position = uMVPMatrix * vPosition; \n" +
                         "}";
        //变量 gl_FragColor 表示fragment的颜色，每个fragment都会执行一遍片段着色器程序
        private final String FRAGMENT_SHADER =
//                        "#version 300 es \n" +
                        "precision mediump float; \n" +
                        "void main() { \n" +
                        "    gl_FragColor = vec4(0.5, 0.5, 0, 1); \n" +
                        " }";
        private FloatBuffer mVertexBuffer;
        private int mMvpMatrixHandle;
        private float[] mMVPMatrix = new float[16];

        HelloRectangleRenderer(){
            mVertexBuffer = ByteBuffer.allocateDirect(VERTICES.length * 4)
                    .order(ByteOrder.LITTLE_ENDIAN)
                    .asFloatBuffer()
                    .put(VERTICES);
            mVertexBuffer.position(0);

            mVertexIndexBuffer = ByteBuffer.allocateDirect(VERTEX_INDEX.length * 2)
                    .order(ByteOrder.nativeOrder())
                    .asShortBuffer()
                    .put(VERTEX_INDEX);
            mVertexIndexBuffer.position(0);
        }

        private int loadShader(int type, String shaderSource){
            int shader = GLES30.glCreateShader(type);
            GLES30.glShaderSource(shader, shaderSource);
            GLES30.glCompileShader(shader);
            return shader;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

            GLES30.glClearColor(0f, 0f, 0f, 0f);
            mProgram = GLES30.glCreateProgram();
            int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, VERTEX_SHADER);
            int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
            GLES30.glAttachShader(mProgram, vertexShader);
            GLES30.glAttachShader(mProgram, fragmentShader);
            GLES30.glLinkProgram(mProgram);
            GLES30.glUseProgram(mProgram);
            /**获取shader代码中的变量索引*/
            int mPositionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition");
            mMvpMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
            /**启用 顶点 */
            GLES30.glEnableVertexAttribArray(mPositionHandle);
            /**绑定 顶点 坐标值
             * param: 要配置的顶点，顶点属性大小， 数据类型，是否需要被标准化，步长，顶点数据
             * https://learnopengl-cn.github.io/01%20Getting%20started/04%20Hello%20Triangle/#_7
             * */
            GLES30.glVertexAttribPointer(mPositionHandle, 3, GLES30.GL_FLOAT, false, 12, mVertexBuffer);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
            //openGL坐标系和 Android 手机坐标系不是线性对应的，需要做投影变化(projection)
            Matrix.perspectiveM(mMVPMatrix, 0, 45, width * 1f / height, 0.1f, 100f);
            Matrix.translateM(mMVPMatrix, 0, 0f, 0f, -5f);

        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

            //在绘制的时候为 uMVPMatrix 赋值
            GLES30.glUniformMatrix4fv(mMvpMatrixHandle, 1, false, mMVPMatrix, 0);

//            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
            GLES30.glDrawElements(GLES30.GL_TRIANGLES, VERTEX_INDEX.length, GLES30.GL_UNSIGNED_SHORT, mVertexIndexBuffer);
        }
    }


}
