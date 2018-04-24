package yll.self.testapp.userinterface.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;

//创建一个GlSurfaceView，并为其设置渲染OneGlRenderer;


/**
 * https://blog.csdn.net/qq_32175491/article/details/79091647
 *
 * 绘制图形，因为需要提供很多细节的图形渲染管线，所以绘制图形前至少需要一个顶点着色器来绘制形状和一个片段着色器的颜色，形状。
 * 这些着色器必须被编译，然后加入到一个OpenGL ES程序，然后将其用于绘制形状。简单介绍下这几个概念：
 - 顶点着色器（Vertex Shader）顶点着色器是GPU上运行的小程序，由名字可以知道，通过它来处理顶点，
    他用于渲染图形顶点的OpenGL ES图形代码。顶点着色器可用来修改图形的位置，颜色，纹理坐标，不过不能用来创建新的顶点坐标。
 - 片段着色器（Fragment Shader ) 用于呈现与颜色或纹理的形状的面的OpenGL ES代码。
 - 项目（Program） -包含要用于绘制一个或多个形状着色器的OpenGL ES的对象。

 投影：
 使用OpenGl绘制的3D图形，需要展示在移动端2D设备上，这就是投影。Android OpenGl ES中有两种投影方式：一种是正交投影，一种是透视投影：

 正交投影: 投影物体的带下不会随观察点的远近而发生变化   Matrix.orthoM
 透视投影: 随观察点的距离变化而变化，观察点越远，视图越小，反之越大  Matrix.frustumM

 相机视图： Matrix.setLookAtM
 转换矩阵： Matrix.multiplyMM
 */
public class OneGlSurfaceView extends GLSurfaceView {

    private OneGlRender glRender;


    public OneGlSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        glRender = new OneGlRender();
        setRenderer(glRender);
    }
}
