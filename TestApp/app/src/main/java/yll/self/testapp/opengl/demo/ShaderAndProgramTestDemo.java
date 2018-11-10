package yll.self.testapp.opengl.demo;

import android.opengl.GLES30;

import yll.self.testapp.opengl.HelloTriangleActivity;

/**
 * 第四章 着色器和程序
 * 内容：
 *      创建、编译和链接着色器到程序
 *      如何查询程序对象的信息以及加载统一变量的方法
 *      着色器代码和程序二进制码的区别以及各自的使用方法
 *
 * 有实例代码 {@link HelloTriangleActivity}
 *
 * */
public class ShaderAndProgramTestDemo {

    private void testShader(){
        //创建着色器  GL_FRAGMENT_SHADER 和  GL_VERTEX_SHADER
        int shader = GLES30.glCreateShader(GLES30.GL_VERTEX_SHADER);

        //删除着色器
        GLES30.glDeleteShader(shader);

        //提供着色器源代码
        GLES30.glShaderSource(shader, "");

        //编译 shader
        GLES30.glCompileShader(shader);

        //获取和着色器对象相关的信息，可以看是否有错误
        int[] paras = new int[1];
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, paras, 0);

        //检索出信息日志 可以编译失败是查看原因
        String result = GLES30.glGetShaderInfoLog(shader);
    }

    private void testProgram(){
        //创建一个程序对象
       int program =  GLES30.glCreateProgram();

       //删除一个程序对象
        GLES30.glDeleteProgram(program);

        //连接一个着色器 必须连一个顶点着色器和一个片段着色器
        int shader = 0;
        GLES30.glAttachShader(program, shader);

        //断开着色器连接
        GLES30.glDetachShader(program, shader);

        //连接了着色器之后，并且着色器已经编译后，就可以链接程序了
        GLES30.glLinkProgram(program);

        //链接程序后，必须检查链接是否成功
        GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, new int[1], 0);

        //同样可以检索出日志信息，可以查看失败原因
        GLES30.glGetProgramInfoLog(program);

        //检查程序能以当前状态执行
        GLES30.glValidateProgram(program);

        //将程序对象设置为活动程序
        GLES30.glUseProgram(program);


        /*统一变量和属性（顶点属性）*/
//        GLES30.glGetActiveUniform();

        //获取和设置统一变量 每种统一类型变量都对应不同的函数
//        GLES30.glUniform1f();
//        GLES30.glUniform1fv();

        //统一变量保存在程序中

        //统一变量缓冲区对象 可以用缓冲区对象存储统一变量数据，从而在程序中的着色器之间甚至程序之间共享统一变量
        //这种缓冲区对象称作统一变量缓冲区对象，可在更新大的统一变量块的时候降低api开销
        // glBufferData, glBufferSubData 等命令修改缓冲区对象而不是 glUniform*


        //完成程序对着色器的编译后，可以提示OpenGL es 释放编译器资源
        GLES30.glReleaseShaderCompiler();

        //程序二进制码
        // 可以在成功编译和链接程序后，检索程序二进制码
//        GLES30.glGetProgramBinary();

        //可以用 glProgramBinary()将其保存到文件，或者将二进制码读回 OpenGL ES 实现，注意兼容性问题

    }


    private void testValue(){

    }
}
