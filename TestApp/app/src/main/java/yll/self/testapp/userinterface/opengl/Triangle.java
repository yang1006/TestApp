package yll.self.testapp.userinterface.opengl;

import android.opengl.GLES20;

import java.nio.FloatBuffer;

//三角形图
public class Triangle {

    private FloatBuffer vertexBuffer, colorBuffer;

    // number of coordinates per vertex in this array 每个顶点3个坐标(x,y,z)
    static final int COORDS_PER_VERTEX = 3;

    static float triangleCoords[] = {   // in counterclockwise order:
            0.0f,  0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };

    float color[] = {  1.0f, 0f, 0f, 1.0f ,
            0f, 1.0f, 0f, 1.0f ,
            0f, 0f, 1.0f, 1.0f };

    /**
     * shader的变量类型(uniform，attribute和varying)的区别
     关于shader的变量类型(uniform，attribute和varying)的区别及使用，下面做下说明：
     1. uniform:uniform变量在vertex和fragment两者之间声明方式完全一样，则它可以在vertex和fragment共享使用。
     （相当于一个被vertex和fragment shader共享的全局变量）uniform变量一般用来表示：变换矩阵，材质，光照参数和颜色等信息。
      在代码中通过GLES20.glGetUniformLocation(int program, String name)来获取属性值。
      并通过 GLES20.glUniformMatrix4fv(int location, int count, boolean transpose, float[] value, int offset);
      方法将数据传递给着色器。
     2. attribute:这个变量只能在顶点着色器中使用(vertex Shader),用来表示顶点的数据，比如顶点坐标，顶点颜色，法线，纹理坐标等。
      在绘制的时候通过GLES20.glGetAttribLocation（int program, String name）来获取变量值，
      通过 GLES20.glEnableVertexAttribArray(int index)来启动句柄，最后通过
      GLES20.glVertexAttribPointer(int indx,int size,int type,boolean normalized,int stride,java.nio.Buffer ptr)
      来设置图形数据。
     3. varying变量：这个变量只能用来在vertex和fragment shader之间传递数据时使用，不可以通过代码获取其变量值。
     * */
    //着色器代码
    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "uniform mat4 uMVPMatrix;"+
                    "varying  vec4 vColor;"+
                    "attribute vec4 aColor;"+
                    "void main() {" +
                    "  gl_Position = uMVPMatrix*vPosition;" +
                    "  vColor=aColor;"+
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    private final int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private final int vertexCount = triangleCoords.length;
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    // Use to access and set the view transformation
    private int mMVPMatrixHandle;

    public Triangle(){
        //数据转换
        vertexBuffer = TransBufferUtil.float2FloatBuffer(triangleCoords);
        colorBuffer = TransBufferUtil.float2FloatBuffer(color);

        int vertexShader = OneGlRender.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = OneGlRender.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        //创建空的 OpenGl ES 程序
        mProgram = GLES20.glCreateProgram();

        //添加顶点和片段着色器到程序中
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        //创建 OpenGL ES 程序可执行文件
        GLES20.glLinkProgram(mProgram);
    }

    //绘制三角形
    public void draw(float[] mvpMatrix){

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT| GLES20.GL_DEPTH_BUFFER_BIT);
        // 将程序添加到OpenGL ES环境
        GLES20.glUseProgram(mProgram);

        // 得到形状的变换矩阵的句柄
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // 将投影和视图转换传递给着色器
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // 获取顶点着色器的vPosition成员的句柄
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

        // 启用三角形顶点的句柄
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // 准备三角形坐标数据
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

       /* // 获取片段着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        // 设置绘制三角形的颜色
        GLES20.glUniform4fv(mColorHandle, 1, colorBuffer, 0);*/

        //获取片元着色器的vColor成员的句柄
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
        //设置绘制三角形的颜色
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle,4,
                GLES20.GL_FLOAT,false,
                0,colorBuffer);


        // 画三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        // 禁用顶点数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);

    }
}
