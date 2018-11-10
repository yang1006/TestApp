package yll.self.testapp.opengl.demo;

/**第五章 着色语言
 * 大部分和 C 语言一样
 * 内容：
 *      使用 #version 执行着色器版本规范
 *      标量、向量和矩阵数据类型及构造器
 *      用 const 限定符声明常量
 *      结构和数组的创建和初始化
 *      运算符、控制流和函数
 *      使用in、out关键字及布局限定符的顶点、片段着色器的输入、输出
 *      平滑、平面和质心插值限定符
 *      预处理器和命令
 *      统一变量和插值器打包
 *      精度限定符和不变性
 * */
public class ShaderLanguageTestDemo {

    private void test(){
//        声明着色器版本   没有声明默认1.00版本，适用于OpenGL es 2.0
//        #version 300 es

//        基于标量、向量和矩阵的数据类型
//        标量：float、int、uint、bool
//        浮点向量：float、vec2、vec3、vec4
//        整数向量：int、ivec2、ivec3、ivec4
//        无符号整数向量：uint、uvec2, uvec3, uvec4
//        布尔向量：bool, bvec2, bvec3, bvec4
//        矩阵: mat2(mat2x2), mat2x3,......mat4(mat4x4) 基于浮点的矩阵

//        声明一个变量，和Java一样，语言中不允许隐含类型转化 float f = 1 会error
//         但可以使用 bool mybool = true, float f = float(mybool)


//        初始化、赋值 略过
//        可以和C语言一样聚合成结构
        /*
        struct fogStruct{
            vec4 color;
            float start;
            float end;
        } fogVar;
        */

        // 统一变量块
//        uniform ThransFormBlock{
//            mat4 matViewProj;
//            mat3 matNormal;
//            mat matTextGen;
//        }

        // 布局限定符 可用于指定支持统一变量块的缓冲区对象在内存中的布局方式 todo
        // shared packed std140 row_major column_major todo 意思以后再了解

        // 顶点和片段着色器 输入和输出 ；顶点输入是为绘制的每个顶点指定的数据
        // a_position 和 a_color 的数据由程序加载，layout可选，用于指定顶点属性的索引
        // v_color 为输出变量，是传递给片段着色器的数据，也会在片段着色器中声明为 in 的变量
        // 顶点着色器
        // uniform mat4 u_matViewProjection
        // layout(location = 0) in vec4 a_position
        // layout(location = 1) in vec3 a_color
        // out vec3 v_color
        // void main(void){
        //  gl_Position = u_matViewProjection * a_position
        //  v_color = a_color
        // }

        // 片段着色器
        // precision mediump float;
        // in vec3 v_color;
        // layout(location = 0) out vec4 o_fragColor
        // void main(){
        //  o_fragColor = vec4(v_color, 1.0)
        // }

        /**插值限定符 smooth 平滑着色，flat 平面着色， centroid 质心采样*/

        /**精度限定符 highp varying mediump
         * highp vec4 position
         * 默认精度
         * precision mediump int 为 int 指定的精度默认作用于所有未指定精度的 int
         * 顶点着色器int 和 float 默认 highp
         * 片段着色器 float 没有默认，必须指定一个*/

        /** 不变性 可以用于声明变量，也可以用于已经声明的变量
         * 输出变量声明了不变性，编译器便保证相同的计算和着色器输入条件下结果相同
         * */

    }
}
