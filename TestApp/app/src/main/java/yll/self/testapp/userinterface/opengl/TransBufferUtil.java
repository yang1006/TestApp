package yll.self.testapp.userinterface.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class TransBufferUtil {

    public static IntBuffer int2IntBuffer(int[] arr){
        // 初始化ByteBuffer，长度为arr数组的长度*4，因为一个float占4个字节
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(arr.length * 4);
        // 数组排列用nativeOrder
        byteBuffer.order(ByteOrder.nativeOrder());
        // 从ByteBuffer创建一个整形缓冲区
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        // 将坐标添加到FloatBuffer
        intBuffer.put(arr);
        // 设置缓冲区来读取第一个坐标
        intBuffer.position(0);
        return intBuffer;
    }

    public static FloatBuffer float2FloatBuffer(float[] arr){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(arr.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(arr);
        floatBuffer.position(0);
        return floatBuffer;
    }


    public static ShortBuffer short2ShortBuffer(short[] arr){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(arr.length * 2);
        byteBuffer.order(ByteOrder.nativeOrder());
        ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
        shortBuffer.put(arr);
        shortBuffer.position(0);
        return shortBuffer;
    }


}
