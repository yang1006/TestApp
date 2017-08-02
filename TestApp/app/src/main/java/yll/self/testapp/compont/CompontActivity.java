package yll.self.testapp.compont;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import yll.self.testapp.compont.broadCast.SendBroadCastActivity;
import yll.self.testapp.R;
import yll.self.testapp.compont.contentprovider.ContentProviderActivity;
import yll.self.testapp.compont.service.TestServiceActivity;
import yll.self.testapp.compont.wallpaper.WallPaperActivity;

/**
 * Created by yll on 2016/1/19.
 *
 */
public class CompontActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compont);
        init();
    }

    private void init(){
        findViewById(R.id.tv_service).setOnClickListener(this);
        findViewById(R.id.tv_broadcast).setOnClickListener(this);
        findViewById(R.id.tv_provider).setOnClickListener(this);
        findViewById(R.id.tv_wallpaper).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_service:
                startActivity(new Intent(CompontActivity.this, TestServiceActivity.class));
                break;
            case R.id.tv_broadcast:
                startActivity(new Intent(CompontActivity.this, SendBroadCastActivity.class));
                break;
            case R.id.tv_provider:
                startActivity(new Intent(CompontActivity.this, ContentProviderActivity.class));
                break;
            case R.id.tv_wallpaper:
                startActivity(new Intent(CompontActivity.this, WallPaperActivity.class));
                break;
        }
    }
}
