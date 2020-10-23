package yll.self.testapp.opengl.newstudy.triangle;

import android.content.Context;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import yll.self.testapp.opengl.newstudy.BaseShape;

public class Shape extends BaseShape {

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


    public Shape(Context context) {
        mContext = context;
    }


    @Override
    protected float[] getVertexFloatArray() {
        return mTriangleVertex;
    }

    @Override
    protected float[] getColorFloatArray() {
        return mTriangleColor;
    }
}
