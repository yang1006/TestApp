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
import yll.self.testapp.utils.UtilsManager;

public class HelloTriangleActivity extends BaseActivity {

    private final int CONTEXT_CLIENT_VERSION = 3;
    private GLSurfaceView mGLSurfaceView;
    private final String TAG = "HelloTriangleActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaceView = new GLSurfaceView(this);
        if (detectOpenGLES30()){
            mGLSurfaceView.setEGLContextClientVersion(CONTEXT_CLIENT_VERSION);
            mGLSurfaceView.setRenderer(new HelloTriangleRenderer());
//            mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
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

    private class HelloTriangleRenderer implements GLSurfaceView.Renderer {


        private int mProgramObject;
        private int mWidth;
        private int mHeight;
        private FloatBuffer mVertices;
        private final String TAG = "HelloTriangleRenderer";
        private final float[] mVerticesData =
                { 0.0f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f };

        HelloTriangleRenderer(){
            mVertices = ByteBuffer.allocateDirect(mVerticesData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            mVertices.put(mVerticesData).position(0);
        }

        //创建着色器对象，加载和编译着色器
        private int loadShader(int type, String shaderStr){
            int shader;
            int[] compiled = new int[1];
            //创建 shader对象
            shader = GLES30.glCreateShader(type);
            if (shader == 0){
                return 0;
            }
            //加载 shader source
            GLES30.glShaderSource(shader, shaderStr);
            // compile the shader
            GLES30.glCompileShader(shader);
            // check the compile status
            GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0){
                UtilsManager.log(TAG, GLES30.glGetShaderInfoLog(shader));
                GLES30.glDeleteShader(shader);
            }
            return shader;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // 初始化着色器和程序对象
            String vShaderStr =
                    "#version 300 es 			  \n"                 //声明着色器版本 v3.00
                            +   "in vec4 vPosition;           \n"
                            + "void main()                  \n"
                            + "{                            \n"
                            + "   gl_Position = vPosition;  \n"
                            + "}                            \n";

            String fShaderStr =
                    "#version 300 es		 			          	\n"
                            + "precision mediump float;					  	\n"
                            + "out vec4 fragColor;	 			 		  	\n"
                            + "void main()                                  \n"
                            + "{                                            \n"
                            + "  fragColor = vec4 ( 1.0, 0.0, 0.0, 1.0 );	\n"
                            + "}                                       \n";

            int vertexShader;
            int fragmentShader;
            int programObject;
            int[] linked = new int[1];
            // 加载顶点和片段着色器
            vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vShaderStr);
            fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fShaderStr);
            // 创建程序对象
            programObject = GLES30.glCreateProgram();
            if (programObject == 0){
                return;
            }
            //将顶点和片段着色器连接到程序对象上
            GLES30.glAttachShader(programObject, vertexShader);
            GLES30.glAttachShader(programObject, fragmentShader);
            //bind vPosition to attribute 0 顶点位置加载到GL，并连接到顶点着色器声明的vPosition属性
            GLES30.glBindAttribLocation(programObject, 0, "vPosition");
            //link the program
            GLES30.glLinkProgram(programObject);
            //check the link status
            GLES30.glGetProgramiv(programObject, GLES30.GL_LINK_STATUS, linked, 0);
            if (linked[0] == 0){
                UtilsManager.log(TAG, "error linking program:");
                UtilsManager.log(TAG, GLES30.glGetProgramInfoLog(programObject));
                GLES30.glDeleteProgram(programObject);
                return;
            }

            mProgramObject = programObject;
            //清除颜色，应该在glClear之前调用
            GLES30.glClearColor(1f, 1f, 1f, 0f);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            mWidth = width;
            mHeight = height;
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            // Set the viewport（视口）  通知用于绘制的2D渲染表面的原点和宽高
            GLES30.glViewport(0, 0, mWidth, mHeight);
            // Clear the color buffer 清除颜色缓冲区
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

            // Use the program object
            GLES30.glUseProgram(mProgramObject);

            // Load the vertex data
            GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, mVertices);
            GLES30.glEnableVertexAttribArray(0);
            //通知绘制图元
            GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
        }
    }
}
