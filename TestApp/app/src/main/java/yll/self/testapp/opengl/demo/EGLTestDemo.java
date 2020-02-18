package yll.self.testapp.opengl.demo;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.os.Build;
import androidx.annotation.RequiresApi;

/** EGL 相关
 * EGL 是 OpenGL ES 3.0 用于创建surface 和渲染上下文的 API*/
public class EGLTestDemo {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void test(){
        int[] major = new int[1], minor = new int[1];
        // 初始化 EGL
        EGLDisplay display = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
        if (display == EGL14.EGL_NO_DISPLAY){
            //EGL不可用 不能使用opengl es 3.0
        }

        //初始化EGL， 返回 major 主版本号，minor次版本号
        if (!EGL14.eglInitialize(display, major, 0, minor, 0)){

            // EGL14.eglGetError()可能为 EGL14.EGL_BAD_DISPLAY, EGL14.EGL_NOT_INITIALIZED
            //无法初始化
        }

        //返回最近调用egl的错误
        EGL14.eglGetError();
        //查询底层窗口系统支持的所有EGL表面配置
        EGLConfig[] eglConfigs = new EGLConfig[1];
        int[] numConfig = new int[1];
        EGL14.eglGetConfigs(display, eglConfigs, 0, 0, numConfig, 0);

        //查询指定的EGLConfig属性
        int[] value = new int[1];
        EGL14.eglGetConfigAttrib(display, eglConfigs[0], 0, value, 0);

        //让EGL 选择配置
//        EGL14.eglChooseConfig()

        //创建屏幕上的渲染区域 EGL窗口
        int[] attrList = new int[]{EGL14.EGL_RENDER_BUFFER, EGL14.EGL_BACK_BUFFER, EGL14.EGL_NONE};
        EGLSurface window = EGL14.eglCreateWindowSurface(display, eglConfigs[0], "", attrList, 0);

        //创建屏幕外渲染区域： EGL Pbuffer（pixel buffer 像素缓冲区）
        attrList = new int[]{
                EGL14.EGL_SURFACE_TYPE, EGL14.EGL_PBUFFER_BIT,
                EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES_BIT,
                EGL14.EGL_RED_SIZE, 5,
                EGL14.EGL_GREEN_SIZE, 6,
                EGL14.EGL_BLUE_SIZE, 5,
                EGL14.EGL_DEPTH_SIZE, 1,
                EGL14.EGL_NONE
        };
        EGL14.eglCreatePbufferSurface(display, eglConfigs[0], attrList, 0);

        //创建一个渲染上下文
        attrList = new int[]{
                EGL14.EGL_CONTEXT_CLIENT_VERSION, 3,
                EGL14.EGL_NONE
        };
        EGLContext context = EGL14.eglCreateContext(display, eglConfigs[0], EGL14.EGL_NO_CONTEXT, attrList, 0);
        if (context == EGL14.EGL_NO_CONTEXT){
            int error = EGL14.eglGetError();
            if (error == EGL14.EGL_BAD_CONFIG){
                //错误
            }
        }

        //指定某个 EGLContext 为当前上下文
        EGLSurface draw = null, read = null;
        EGL14.eglMakeCurrent(display, draw, read, EGL14.EGL_NO_CONTEXT);

        // TODO: 2018/8/8 同步渲染 eglWaitClient, eglWaitNative

    }

    /*创建 EGL 窗口的完整实例 */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int initWindow(Object nativeWindow){
        int[] configsAttibs = new int[]{
                EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_WINDOW_BIT,
                EGL14.EGL_RED_SIZE, 8,
                EGL14.EGL_GREEN_SIZE, 8,
                EGL14.EGL_BLUE_SIZE, 8,
                EGL14.EGL_DEPTH_SIZE, 24,
                EGL14.EGL_NONE
        };

        int[] contextArribs = new int[]{
                EGL14.EGL_CONTEXT_CLIENT_VERSION, 3,
                EGL14.EGL_NONE
        };

        EGLDisplay display = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
        if (display == EGL14.EGL_NO_DISPLAY){
            return EGL14.EGL_FALSE;
        }

        int[] major = new int[1] , minor = new int[1];
        if (!EGL14.eglInitialize(display, major, 0,  minor, 0)){
            return EGL14.EGL_FALSE;
        }

        EGLConfig[] configs = new EGLConfig[1];
        int[] numConfigs = new int[1];
        if (!EGL14.eglChooseConfig(display, configsAttibs, 0, configs, 0, 1, numConfigs, 0)){
            return EGL14.EGL_FALSE;
        }

        EGLSurface window = EGL14.eglCreateWindowSurface(display, configs[0], nativeWindow, null, 0);
        if (window == EGL14.EGL_NO_SURFACE){
            return EGL14.EGL_FALSE;
        }

        EGLContext context = EGL14.eglCreateContext(display, configs[0], EGL14.EGL_NO_CONTEXT, contextArribs, 0);
        if (context == EGL14.EGL_NO_CONTEXT){
            return EGL14.EGL_FALSE;
        }

        if (!EGL14.eglMakeCurrent(display, window, window, context)){
            return EGL14.EGL_FALSE;
        }

        return EGL14.EGL_TRUE;

        //以上代码和例3-8中 打开512x512窗口的代码类似 todo
    }
}
