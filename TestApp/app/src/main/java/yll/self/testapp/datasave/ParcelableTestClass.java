package yll.self.testapp.datasave;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import yll.self.testapp.utils.UtilsManager;

/**
 * Created by yll on 17/1/7.
 * 实现Parcelable接口测试序列化
 */

public class ParcelableTestClass implements Parcelable{

    public int userId;
    public String userName;
    public boolean isMale;

    public ParcelableTestClass(int userId, String userName, boolean isMale){
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    protected ParcelableTestClass(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readInt() == 1;
    }

    public static final Creator<ParcelableTestClass> CREATOR = new Creator<ParcelableTestClass>() {
        @Override
        public ParcelableTestClass createFromParcel(Parcel in) {
            return new ParcelableTestClass(in);
        }

        @Override
        public ParcelableTestClass[] newArray(int size) {
            return new ParcelableTestClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(userId);
        out.writeString(userName);
        out.writeInt(isMale ? 1 : 0);
    }

    /**序列化和反序列化使用*/
    public static void testParcelable(){
        Bundle b = new Bundle();
        b.putParcelable("abc", new ParcelableTestClass(2, "zhang", true));
        ParcelableTestClass o = b.getParcelable("abc");
//        UtilsManager.log("o-->" + o.userId +" "+o.userName + " " + o.isMale);
    }

}
