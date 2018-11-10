package yll.self.testapp.opengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import yll.self.testapp.BaseActivity;
import yll.self.testapp.opengl.common.ESShader;
import yll.self.testapp.utils.UtilsManager;

public class Chapter6Example6_3Activity extends BaseActivity {

    private static final String TAG = "Chapter6Example6_3Activity";

    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (detectOpenGLES30()){
            mGLSurfaceView = new GLSurfaceView(this);
            mGLSurfaceView.setEGLContextClientVersion(3);
            mGLSurfaceView.setRenderer(new Example6_3Renderer());
            setContentView(mGLSurfaceView);
        }else {
            UtilsManager.log(TAG, "设备不支持 OpenGL ES 3.0");
            finish();
        }
    }

    private boolean detectOpenGLES30(){
        ActivityManager am =
                ( ActivityManager ) getSystemService ( Context.ACTIVITY_SERVICE );
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return ( info.reqGlEsVersion >= 0x30000 );
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

    private class Example6_3Renderer implements GLSurfaceView.Renderer{

        // Handle to a program object
        private int mProgramObject;

        // Additional member variables
        private int mWidth;
        private int mHeight;
        private FloatBuffer mVertices;

        private final float[] mVerticesData =
                {
                        0.0f,  0.5f, 0.0f, // v0
                        -0.5f, -0.5f, 0.0f, // v1
                        0.5f, -0.5f, 0.0f  // v2
                };

        Example6_3Renderer(){
            mVertices = ByteBuffer.allocateDirect(mVerticesData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            mVertices.put(mVerticesData).position(0);
        }


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            String vShaderStr =
                    "#version 300 es                            \n" +
                            "layout(location = 0) in vec4 a_color;      \n" +
                            "layout(location = 1) in vec4 a_position;   \n" +
                            "out vec4 v_color;                          \n" +
                            "void main()                                \n" +
                            "{                                          \n" +
                            "    v_color = a_color;                     \n" +
                            "    gl_Position = a_position;              \n" +
                            "}";


            String fShaderStr =
                    "#version 300 es            \n" +
                            "precision mediump float;   \n" +
                            "in vec4 v_color;           \n" +
                            "out vec4 o_fragColor;      \n" +
                            "void main()                \n" +
                            "{                          \n" +
                            "    o_fragColor = v_color; \n" +
                            "}" ;

            mProgramObject = ESShader.loadProgram(vShaderStr, fShaderStr);
            GLES30.glClearColor(1f, 1f, 1f, 0f);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            mWidth = width;
            mHeight = height;
        }

        @Override
        public void onDrawFrame(GL10 gl) {

            GLES30.glViewport(0, 0, mWidth, mHeight);
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
            GLES30.glUseProgram(mProgramObject);
            //指定一个图元的所有顶点，加载index指定的通用顶点属性 todo 不知道顶点什么意思
            GLES30.glVertexAttrib4f(0, 1f, 0f, 0f, 1f);
            mVertices.position(0);
            //顶点数据指定每个顶点的属性 是保存在应用程序地址空间的缓冲区
            GLES30.glVertexAttribPointer(1, 3, GLES30.GL_FLOAT, false, 0, mVertices);
            GLES30.glEnableVertexAttribArray ( 1 );
            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
            GLES30.glDisableVertexAttribArray(1);

        }
    }
}
