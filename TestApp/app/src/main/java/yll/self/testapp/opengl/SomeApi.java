package yll.self.testapp.opengl;

import android.opengl.GLES30;

import java.nio.IntBuffer;

/**记录一些api，*/
public class SomeApi {

    public void chapter7(){
        //7.3 图元装配
        //视口变换调用的api 设置 x, y, w, h
        int x = 0,  y = 0, w = 100, h = 100;
        GLES30.glViewport(x, y, w, h);

        //设置深度范围值n和f
        float n = 1f, f = 1f;
        GLES30.glDepthRangef(n , f);

        //7.4 光栅化
        //三角形面积符号指定 顺时针或者逆时针方法
        int mode = GLES30.GL_CW;  //默认GL_CCW
        GLES30.glFrontFace(mode);

        //指定要被剔除的三角形的面
        mode = GLES30.GL_FRONT; //GL_BACK（默认）, GL_FRONT_ADN_BACK
        GLES30.glCullFace(mode);

        //启用或禁用 GL_CULL_FACE状态
        GLES30.glEnable(GLES30.GL_CULL_FACE);
        GLES30.glDisable(GLES30.GL_CULL_FACE);

        //指定多边形偏移
        GLES30.glPolygonOffset(-1f, -2f);
        //可以使用 GLES30.glEnable(GLES30.GL_POLYGON_OFFSET_FILL); 启用禁用

        //7.5 遮挡查询
        int target = GLES30.GL_ANY_SAMPLES_PASSED;
        int id = 0;
        GLES30.glBeginQuery(target, id);
        GLES30.glEndQuery(target);

        //id 用 glGenQueries(n, ids) 创建，用glDeleteQueries(n, ids) 删除

        // 查询检索查询对象的结果
        IntBuffer intBuffer = IntBuffer.allocate(0);
        GLES30.glGetQueryObjectuiv(id, 0, intBuffer);
    }

    public void chapter8(){

    }
}
