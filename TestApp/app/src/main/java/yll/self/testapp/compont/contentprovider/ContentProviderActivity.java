package yll.self.testapp.compont.contentprovider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import yll.self.testapp.R;

/**
 * Created by yll on 2016/7/22.
 */
public class ContentProviderActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        init();
    }

    private void init(){
        findViewById(R.id.tv_contact).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_contact:
                startActivity(new Intent(ContentProviderActivity.this, ContactActivity.class));
                break;
        }
    }
}
