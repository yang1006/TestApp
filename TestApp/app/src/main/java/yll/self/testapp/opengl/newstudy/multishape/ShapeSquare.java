package yll.self.testapp.opengl.newstudy.multishape;

import yll.self.testapp.opengl.newstudy.BaseShape;

public class ShapeSquare extends BaseShape {



    //正方形四个顶点，只有x y轴
    public float[] mVertex = new float[] {
            -0.5f, 0.5f,
            -0.5f, -0.5f,
            0.5f, 0.5f,
            0.5f, -0.5f
    };

    public float[] mColor = new float[] {
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
    };


    @Override
    protected float[] getVertexFloatArray() {
        return mVertex;
    }

    @Override
    protected float[] getColorFloatArray() {
        return mColor;
    }
}
