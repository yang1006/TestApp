package yll.self.testapp.thirdlib.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import yll.self.testapp.R;
import yll.self.testapp.thirdlib.retrofit.api.GitUserApi;
import yll.self.testapp.thirdlib.retrofit.model.GitUserModel;

/**
 * Created by yll on 17/7/18.
 * Retrofit使用
 */

public class RetrofitActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView tv_content;
    //Retrofit2 的baseUlr 必须以 /（斜线） 结束，不然会抛出一个IllegalArgumentException,
    //所以如果你看到别的教程没有以 / 结束，那么多半是直接从Retrofit 1.X 照搬过来的。
    private final String BASE_URL = "https://api.github.com/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        tv_content = (TextView) findViewById(R.id.tv_content);

        Button btn_click = (Button) findViewById(R.id.btn_click);
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .build();
                GitUserApi userApi = retrofit.create(GitUserApi.class);
                Call<ResponseBody> call = userApi.getUserFeedBody("yang1006");
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            tv_content.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        tv_content.setText(t.toString());
                    }
                });
            }
        });

        Button btn_click2 = (Button) findViewById(R.id.btn_click2);
        btn_click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                GitUserApi userApi = retrofit.create(GitUserApi.class);
                Call<GitUserModel> call = userApi.getUserFeed("yang1006");
                call.enqueue(new Callback<GitUserModel>() {
                    @Override
                    public void onResponse(Call<GitUserModel> call, Response<GitUserModel> response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            tv_content.setText("login: "+response.body().getLogin()
                                      + " \nid: " + response.body().getId()
                                       + " \navatar_url : " + response.body().getAvatarUrl()
                                    + " \nurl: " + response.body().getUrl()
                                    + " \nhtml_url: " + response.body().getHtmlUrl());
                        }catch (Exception e){}
                    }

                    @Override
                    public void onFailure(Call<GitUserModel> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        tv_content.setText(t.toString());
                    }
                });
            }
        });
    }



}
