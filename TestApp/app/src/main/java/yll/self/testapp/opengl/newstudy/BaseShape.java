package yll.self.testapp.opengl.newstudy;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public abstract class BaseShape {

    private FloatBuffer mTriangleVerFloatBuffer;
    private FloatBuffer mTriangleColorFloatBuffer;

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
            mTriangleVerFloatBuffer = getFloatBuffer(getVertexFloatArray());
        }
        return mTriangleVerFloatBuffer;
    }

    public FloatBuffer getColorFloatBuffer() {
        if (mTriangleColorFloatBuffer == null) {
            mTriangleColorFloatBuffer = getFloatBuffer(getColorFloatArray());
        }
        return mTriangleColorFloatBuffer;
    }

    protected abstract float[] getVertexFloatArray();
    protected abstract float[] getColorFloatArray();
}
