package yll.self.testapp.userinterface.camera;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.view.LayoutInflater;
import android.view.View;

import java.io.IOException;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import yll.self.testapp.R;
import yll.self.testapp.userinterface.opengl.TransBufferUtil;

public class CameraGLSurfaceViewFragment extends CameraPreviewFragment implements SurfaceTexture.OnFrameAvailableListener {


    private GLSurfaceView mGLSurfaceView;
    private SurfaceTexture mSurfaceTexture;
    private Camera camera;
    private int camera_status = 0;
    private MyRender myRender;


    public CameraGLSurfaceViewFragment(Context context) {
        super(context);
    }

    @Override
    public void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.fragemt_camera_glsurface, null);
        mGLSurfaceView = rootView.findViewById(R.id.mGLSurface);

        rootView.findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera_status ^= 1;
                if (camera != null){
                    camera.stopPreview();
                    camera.release();
                }
                myRender.mBoolean = true;
                camera = Camera.open(camera_status);
                try {
                    camera.setPreviewTexture(mSurfaceTexture);
                }catch (Exception e){}
                camera.startPreview();
            }
        });

        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(myRender = new MyRender());
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (camera != null){
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        mGLSurfaceView.requestRender();
    }


    public class MyRender implements GLSurfaceView.Renderer{

        private final String vertexShaderCode = "uniform mat4 textureTransform;\n" +
                "attribute vec2 inputTextureCoordinate;\n" +
                "attribute vec4 position;            \n" +//NDK坐标点
                "varying   vec2 textureCoordinate; \n" +//纹理坐标点变换后输出
                "\n" +
                " void main() {\n" +
                "     gl_Position = position;\n" +
                "     textureCoordinate = inputTextureCoordinate;\n" +
                " }";

        private final String fragmentShaderCode = "#extension GL_OES_EGL_image_external : require\n" +
                "precision mediump float;\n" +
                "uniform samplerExternalOES videoTex;\n" +
                "varying vec2 textureCoordinate;\n" +
                "\n" +
                "void main() {\n" +
                "    vec4 tc = texture2D(videoTex, textureCoordinate);\n" +
                "    float color = tc.r * 0.3 + tc.g * 0.59 + tc.b * 0.11;\n" +  //所有视图修改成黑白 黑白滤镜
                "    gl_FragColor = vec4(color,color,color,1.0);\n" +
//                "    gl_FragColor = vec4(tc.r,tc.g,tc.b,1.0);\n" +
                "}\n";


        private FloatBuffer mPosBuffer;
        private FloatBuffer mTexBuffer;
        private float[] mPosCoordinate = {-1, -1, -1, 1, 1, -1, 1, 1};
        private float[] mTexCoordinateBackRight = {1, 1, 0, 1, 1, 0, 0, 0};//顺时针转90并沿Y轴翻转  后摄像头正确，前摄像头上下颠倒
        private float[] mTexCoordinateForntRight = {0, 1, 1, 1, 0, 0, 1, 0};//顺时针旋转90  后摄像头上下颠倒了，前摄像头正确

        public int mProgram;
        public boolean mBoolean = false;


        private int uPosHandle;
        private int aTexHandle;
        private int mMVPMatrixHandle;
        private float[] mProjectMatrix = new float[16];
        private float[] mCameraMatrix = new float[16];
        private float[] mMVPMatrix = new float[16];
        private float[] mTempMatrix = new float[16];

        public MyRender(){
            Matrix.setIdentityM(mProjectMatrix, 0);
            Matrix.setIdentityM(mCameraMatrix, 0);
            Matrix.setIdentityM(mMVPMatrix, 0);
            Matrix.setIdentityM(mTempMatrix, 0);
        }

        private int loadShader(int type , String shaderCode){
            int shader = GLES20.glCreateShader(type);
            // 添加上面编写的着色器代码并编译它
            GLES20.glShaderSource(shader, shaderCode);
            GLES20.glCompileShader(shader);
            return shader;
        }

        private void createProgram(){
            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
            int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
            //创建空的OpenGL ES 程序
            mProgram = GLES20.glCreateProgram();

            /*添加顶点着色器*/
            GLES20.glAttachShader(mProgram, vertexShader);

            /*添加片段着色器*/
            GLES20.glAttachShader(mProgram, fragmentShader);

            /*创建OpenGL ES 可执行文件*/
            GLES20.glLinkProgram(mProgram);

            /*释放shader资源*/
            GLES20.glDeleteShader(vertexShader);
            GLES20.glDeleteShader(fragmentShader);
        }

        /*添加程序到ES环境中*/
        private void activeProgram(){
            GLES20.glUseProgram(mProgram);

            mSurfaceTexture.setOnFrameAvailableListener(CameraGLSurfaceViewFragment.this);

            uPosHandle = GLES20.glGetAttribLocation(mProgram, "position");
            aTexHandle = GLES20.glGetAttribLocation(mProgram, "inputTextureCoordinate");
            mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "textureTransform");

            mPosBuffer = TransBufferUtil.float2FloatBuffer(mPosCoordinate);

            if (camera_status == 0){
                mTexBuffer = TransBufferUtil.float2FloatBuffer(mTexCoordinateBackRight);
            }else {
                mTexBuffer = TransBufferUtil.float2FloatBuffer(mTexCoordinateForntRight);
            }

            GLES20.glVertexAttribPointer(uPosHandle, 2, GLES20.GL_FLOAT, false, 0, mPosBuffer);
            GLES20.glVertexAttribPointer(aTexHandle, 2, GLES20.GL_FLOAT, false, 0, mTexBuffer);

            /*启动顶点位置句柄*/
            GLES20.glEnableVertexAttribArray(uPosHandle);
            GLES20.glEnableVertexAttribArray(aTexHandle);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            mSurfaceTexture = new SurfaceTexture(createOESTextureObject());
            createProgram();
//            mProgram = ShaderUtils.createProgram(CameraGlSurfaceShowActivity.this, "vertex_texture.glsl", "fragment_texture.glsl");
            camera = Camera.open(camera_status);
            try {
                camera.setPreviewTexture(mSurfaceTexture);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
            activeProgram();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
            Matrix.scaleM(mMVPMatrix, 0, 1, -1, 1);
            float ratio = width * 1f / height;
            Matrix.orthoM(mProjectMatrix, 0, -1, 1, -ratio, ratio, 1, 7);// 3和7代表远近视点与眼睛的距离，非坐标点
            Matrix.setLookAtM(mCameraMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);// 3代表眼睛的坐标点
            Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mCameraMatrix, 0);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            if (mBoolean){
                activeProgram();
                mBoolean = false;
            }
            if (mSurfaceTexture != null){
                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
                mSurfaceTexture.updateTexImage();
                GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
                GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, mPosCoordinate.length / 2);
            }
        }
    }


    private int createOESTextureObject(){
        int[] tex = new int[1];
        /*生成一个纹理*/
        GLES20.glGenTextures(1, tex, 0);
        /*将此纹理绑定到外部纹理上*/
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, tex[0]);
        /*设置纹理过滤参数*/
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
        return tex[0];
    }
}
