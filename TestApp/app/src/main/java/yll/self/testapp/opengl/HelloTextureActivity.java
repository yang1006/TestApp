package yll.self.testapp.opengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
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
import yll.self.testapp.R;
import yll.self.testapp.opengl.common.MyGLUtils;
import yll.self.testapp.utils.UtilsManager;

/**
 * https://blog.piasy.com/2016/06/07/Open-gl-es-android-2-part-1/
 * */
public class HelloTextureActivity extends BaseActivity {

    private final int CONTEXT_CLIENT_VERSION = 3;
    private GLSurfaceView mGLSurfaceView;
    private final String TAG = "HelloTriangleActivity";
    private HelloTextureRenderer mRenderer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaceView = new GLSurfaceView(this);
        if (detectOpenGLES30()){
            mGLSurfaceView.setEGLContextClientVersion(CONTEXT_CLIENT_VERSION);
            mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
            mGLSurfaceView.setRenderer(mRenderer = new HelloTextureRenderer());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRenderer.destroy();
    }

    private class HelloTextureRenderer implements GLSurfaceView.Renderer {

        /**三角形的三个顶点*/
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
        /**
         * uniform 由外部程序传递给 shader，就像是C语言里面的常量，shader 只能用，不能改；
         * attribute 是只能在 vertex shader 中使用的变量；
         * varying 变量是 vertex 和 fragment shader 之间做数据传递用的。
         * */
        private final String VERTEX_SHADER =
                "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "attribute vec2 a_texCoord;" +
                "varying vec2 v_texCoord;" +
                "void main() {" +
                "  gl_Position = uMVPMatrix * vPosition;" +
                "  v_texCoord = a_texCoord;" +
                "}";
        //变量 gl_FragColor 表示fragment的颜色，每个fragment都会执行一遍片段着色器程序
        private final String FRAGMENT_SHADER =
//                        "#version 300 es \n" +
                         "precision mediump float;" +
                        "varying vec2 v_texCoord;" +
                        "uniform sampler2D s_texture;" +
                        "void main() {" +
                        "  gl_FragColor = texture2D(s_texture, v_texCoord);" +
                        "}";
        private FloatBuffer mVertexBuffer;
        private int mMvpMatrixHandle;
        private float[] mMVPMatrix = new float[16];
        private int mTexName;

        /**指定了截取纹理区域的坐标 完整区域
         * OpenGL 的纹理坐标系是二维坐标系，原点在左下角，s（x）轴向右，t（y）轴向上，x y 取值范围都是 [0, 1]
         * 但计算机中图片都是y轴向下，所以依然是(原点在左上角，x轴向右，y轴向下)*/
        private final float[] TEX_VERTEX = {
                1, 0,
                0, 0,
                0, 1,
                1, 1,
        };
        private final FloatBuffer mTexVertexBuffer;
        private int mPositionHandle;
        private int mTexCoordHandle;
        private int mTexSamplerHandle;
        private int mWidth, mHeight;
        HelloTextureRenderer(){
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

            mTexVertexBuffer = ByteBuffer.allocateDirect(TEX_VERTEX.length * 4)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer()
                    .put(TEX_VERTEX);
            mTexVertexBuffer.position(0);
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
            mPositionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition");
            mMvpMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
            mTexCoordHandle = GLES30.glGetAttribLocation(mProgram, "a_texCoord");
            mTexSamplerHandle = GLES30.glGetUniformLocation(mProgram, "s_texture");
            /**启用 顶点 */
            GLES30.glEnableVertexAttribArray(mPositionHandle);
            /**绑定 顶点 坐标值
             * param: 要配置的顶点，顶点属性大小， 数据类型，是否需要被标准化，步长，顶点数据
             * https://learnopengl-cn.github.io/01%20Getting%20started/04%20Hello%20Triangle/#_7
             * */
            GLES30.glVertexAttribPointer(mPositionHandle, 3, GLES30.GL_FLOAT, false, 12, mVertexBuffer);

            GLES30.glEnableVertexAttribArray(mTexCoordHandle);
            GLES30.glVertexAttribPointer(mTexCoordHandle, 2, GLES30.GL_FLOAT, false, 0, mTexVertexBuffer);


            int[] texNames = new int[1];
            //创建纹理
            GLES30.glGenTextures(1, texNames, 0);
            mTexName = texNames[0];
            //激活指定编号的纹理
            GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
            //将新建的纹理和编号绑定起来
            GLES30.glBindTexture(GLES20.GL_TEXTURE_2D, mTexName);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);

            Bitmap bitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.drawable.ic_cover_system);
            //把图片数据拷贝到纹理中
            GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
            bitmap.recycle();

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            mWidth = width;
            mHeight = height;
            GLES30.glViewport(0, 0, width, height);
            //openGL坐标系和 Android 手机坐标系不是线性对应的，需要做投影变化(projection)
            Matrix.perspectiveM(mMVPMatrix, 0, 45, width * 1f / height, 0.1f, 100f);
            Matrix.translateM(mMVPMatrix, 0, 0f, 0f, -2.5f);

        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

            //在绘制的时候为 uMVPMatrix 赋值
            GLES30.glUniformMatrix4fv(mMvpMatrixHandle, 1, false, mMVPMatrix, 0);

            GLES30.glDrawElements(GLES30.GL_TRIANGLES, VERTEX_INDEX.length, GLES30.GL_UNSIGNED_SHORT, mVertexIndexBuffer);

            //保存图片后发现图片是倒的
            /**
             * 这里又涉及到上篇中所说的坐标系的问题了，OpenGL 的坐标系和安卓手机的坐标系的 y 轴是相反的，
             * 所以即便我们在屏幕上看起来是正常的，一旦导出帧数据保存为图片，
             * 它看起来还是倒的！所以我们在拿到帧数据之后，需要进行处理，
             * 而且不是简单的旋转操作，因为这个颠倒，是由于图像沿着 x 轴旋转了 180° 而不是沿着 z 轴旋转了 180° ！
             * */
            MyGLUtils.senImage(mWidth, mHeight);
        }

        void destroy(){
            GLES30.glDeleteTextures(1, new int[]{mTexName}, 0);
        }
    }


}
