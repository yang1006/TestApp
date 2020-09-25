package yll.self.testapp.opengl.newstudy.triangle;

import android.content.Context;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Shape {

    private Context mContext;

    //三角形 三个顶点坐标，这里只有x y轴
    public float[] mTriangleVertex = {
            0.0f, 0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f

    };

    //三个顶点，每个顶点都是  r g b a 四个通道
    public float[] mTriangleColor = {
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f
    };

    private FloatBuffer mTriangleVerFloatBuffer;
    private FloatBuffer mTriangleColorFloatBuffer;


    public Shape(Context context) {
        mContext = context;
    }

    public FloatBuffer getFloatBuffer(float verTex[]) {
        ByteBuffer bb = ByteBuffer.allocateDirect(verTex.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(verTex);
        vertexBuffer.position(0);
        return vertexBuffer;
    }

    public FloatBuffer getVertexFloatBuffer() {
        if (mTriangleVerFloatBuffer == null) {
            mTriangleVerFloatBuffer = getFloatBuffer(mTriangleVertex);
        }
        return mTriangleVerFloatBuffer;
    }

    public FloatBuffer getColorFloatBuffer() {
        if (mTriangleColorFloatBuffer == null) {
            mTriangleColorFloatBuffer = getFloatBuffer(mTriangleColor);
        }
        return mTriangleColorFloatBuffer;
    }

}
