package yll.self.testapp.opengl.common;

import android.graphics.Bitmap;
import android.opengl.GLES30;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

import yll.self.testapp.utils.UtilsManager;

public class MyGLUtils {

    public static final String FILE_PATH = UtilsManager.FILE_PATH;

    public static void senImage(int width, int height){
        ByteBuffer rgbaBuf = ByteBuffer.allocateDirect(width * height * 4);
        rgbaBuf.position(0);
        long startTime = System.nanoTime();
        GLES30.glReadPixels(0, 0, width, height, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, rgbaBuf);
        long endTime = System.nanoTime();
        UtilsManager.log("glReadPixels cost nanoTime: " + (endTime - startTime));
        saveRgb2Bitmap(rgbaBuf, FILE_PATH + "/gl_dump_" + width +"_" + height + ".png", width, height);
    }

    public static void saveRgb2Bitmap(Buffer buf, String filename, int width, int height) {
        UtilsManager.log("TryOpenGL", "Creating " + filename);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(filename));
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bmp.copyPixelsFromBuffer(buf);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, bos);
            bmp.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
