package yll.self.testapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import yll.self.testapp.compont.CompontActivity;
import yll.self.testapp.datasave.ParcelableTestClass;
import yll.self.testapp.datasave.SaveDataActivity;
import yll.self.testapp.datasave.SerializableTestClass;
import yll.self.testapp.design.DesignActivity;
import yll.self.testapp.normal.NormalActivity;
import yll.self.testapp.userinterface.UIAndAniActivity;
import yll.self.testapp.other.OtherActivity;
import yll.self.testapp.utils.UtilsManager;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView tv_normal, tv_data_save;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        ctx = MainActivity.this;
        init();
        tv_normal = (TextView) findViewById(R.id.tv_normal);
    }

    private void init(){
        tv_normal = (TextView) findViewById(R.id.tv_normal);
        tv_normal.setOnClickListener(this);
        tv_data_save = (TextView) findViewById(R.id.tv_data_save);
        tv_data_save.setOnClickListener(this);
        findViewById(R.id.tv_compont).setOnClickListener(this);
        findViewById(R.id.tv_ui).setOnClickListener(this);
        findViewById(R.id.tv_design).setOnClickListener(this);
        findViewById(R.id.tv_other).setOnClickListener(this);
        SerializableTestClass.serialize();
        SerializableTestClass.deSerialize();

        ParcelableTestClass.testParcelable();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tv_normal:
                intent = new Intent(ctx, NormalActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_data_save:
                intent = new Intent(ctx, SaveDataActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_compont:
                intent = new Intent(ctx, CompontActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_ui:
                intent = new Intent(ctx, UIAndAniActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_design:
                intent = new Intent(ctx, DesignActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_other:
                intent = new Intent(ctx, OtherActivity.class);
                startActivity(intent);
                break;
        }
    }

}
