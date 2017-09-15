package yll.self.testapp.datasave;

import android.os.Environment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 17/1/7.
 * 使用Serializable接口测试序列
 */

public class SerializableTestClass implements Serializable {

    private static final long serialVersionUID = 1L;

    public int userId;
    public String userName;
    public boolean isMale;

    public SerializableTestClass(int userId, String userName, boolean isMale){
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    /**序列化过程*/
    public static void serialize(){
        SerializableTestClass o = new SerializableTestClass(1, "yang", true);
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/cache.txt"));
            out.writeObject(o);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**反序列化过程*/
    public static void deSerialize(){
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(Environment.getExternalStorageDirectory().getPath()+"/cache.txt"));
            SerializableTestClass o = (SerializableTestClass)in.readObject();
            in.close();
//            UtilsManager.log("o->"+o.userId+"  "+o.userName+ "  "+o.isMale);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
