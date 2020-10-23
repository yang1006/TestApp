package yll.self.testapp.opengl.newstudy;

import android.content.Context;
import android.opengl.GLES20;

import java.io.InputStream;

import yll.self.testapp.utils.LogUtil;

public class BaseShader {

    protected Context mContext;
    public BaseShader(Context context) {
        mContext = context;
    }


    protected String loadStringFromAssertFile(String fName) {

        StringBuilder result = new StringBuilder();
        try {
            InputStream is = mContext.getResources().getAssets().open(fName);
            int ch;
            byte[] buffer = new byte[1024];
            while (-1 != (ch = is.read(buffer))) {
                result.append(new String(buffer, 0, ch));
            }
        } catch (Exception e) {
            return null;
        }
        String str = result.toString().replaceAll("\\r\\n", "\n");
        LogUtil.e("yll", "str \n" + str);
        return result.toString().replaceAll("\\r\\n", "\n");
    }

    protected int createShader(int type, String codeSource) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, codeSource);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
