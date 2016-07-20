package yll.self.testapp.datasave;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import yll.self.testapp.R;

/**
 * Created by yll on 2016/1/21.
 * 测试数据库界面
 */
public class DbTestActivity extends Activity implements View.OnClickListener {

    private EditText et_name, et_age, et_home;
    private TextView tv_result;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);
        init();
        dbManager = DBManager.getInstance(DbTestActivity.this);
    }

    private void init(){
        et_name = (EditText) findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        et_home = (EditText) findViewById(R.id.et_home);

        tv_result = (TextView) findViewById(R.id.tv_result);

        findViewById(R.id.btn_save_data).setOnClickListener(this);
        findViewById(R.id.btn_read_data).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save_data:
                insertOneStudent();
                break;
            case R.id.btn_read_data:
                getAllData();
                break;
        }
    }

    private void insertOneStudent(){
        dbManager.insertAStudent(et_name.getText().toString().trim(),
                et_age.getText().toString().trim(), et_home.getText().toString().trim());
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    private void getAllData(){
        tv_result.setText("");
        Cursor cursor = dbManager.getAllStudent();
        while (cursor.moveToNext()){
            tv_result.append(cursor.getString(1) + "  ");
            tv_result.append(cursor.getString(2) + "  ");
            tv_result.append(cursor.getString(3) + "\n");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }
}
