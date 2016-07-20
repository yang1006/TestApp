package yll.self.testapp.datasave;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yll on 2016/1/21.
 */
public class DBManager {

    private static MySQLiteOpenHelper mySQLiteOpenHelper;
    private static SQLiteDatabase db;
    private static DBManager dbManager;   //用于单例
    private Context context;
    private static final int DBVersion = 1;       //版本
    private static final String DBName = "test.db";//数据库名

    public static DBManager getInstance(Context context){

        if (dbManager == null){
            dbManager = new DBManager(context.getApplicationContext());
        }
        if (db == null){
            db = mySQLiteOpenHelper.getWritableDatabase();
        }
        return dbManager;
    }

    private DBManager(Context context){
        this.context = context;
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context);
    }

    private class MySQLiteOpenHelper extends SQLiteOpenHelper{

        public MySQLiteOpenHelper(Context ctx) {
            super(ctx, DBName, null, DBVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldCode, int newCode) {

        }
    }

    public void close(){
        db.close();
        mySQLiteOpenHelper.close();
        dbManager = null;
    }

    public void createTable(SQLiteDatabase db){
        db.execSQL("create table if not exists Student(id integer primary key autoincrement, " +
                "name text not null, age text not null, hometown text);");
    }

    public void insertAStudent(String name, String age, String hometown){
        db.execSQL("insert into Student(name, age, hometown) values(?,?,?)", new Object[]{name, age, hometown});
//        ContentValues cv = new ContentValues();
//        cv.put("name", name);
//        cv.put("age", age);
//        cv.put("hometown", hometown);
//        db.insert("Student", null, cv);
    }

    public Cursor getAllStudent(){
        return db.rawQuery("select * from Student", null);
    }
}
