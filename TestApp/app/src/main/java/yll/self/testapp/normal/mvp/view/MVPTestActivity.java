package yll.self.testapp.normal.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import yll.self.testapp.R;
import yll.self.testapp.normal.mvp.presenter.USerPresenter;

/**
 * Created by yll on 2015/12/22.
 * activity中实现IUserView接口，在其中操作view，实例化一个presenter变量。
 */
public class MVPTestActivity extends Activity implements View.OnClickListener, IUserView {

    USerPresenter userPresenter;
    EditText id, first, last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_test);

        findViewById(R.id.btn_read).setOnClickListener(this);
        findViewById(R.id.btn_write).setOnClickListener(this);

        id = (EditText) findViewById(R.id.id);
        first = (EditText) findViewById(R.id.first);
        last = (EditText) findViewById(R.id.last);

        userPresenter = new USerPresenter(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_write:
                userPresenter.saveData(getID(), getFirstName(), getLastName());
                break;
            case R.id.btn_read:
                userPresenter.loadUser(getID());
                break;
        }
    }

    @Override
    public int getID() {
        return Integer.valueOf(id.getText().toString());
    }

    @Override
    public String getFirstName() {
        return first.getText().toString();
    }

    @Override
    public String getLastName() {
        return last.getText().toString();
    }

    @Override
    public void setFirstName(String firstName) {
        first.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        last.setText(lastName);
    }

    @Override
    public void cleanAll() {
        id.setText("");
        first.setText("");
        last.setText("");
    }
}
