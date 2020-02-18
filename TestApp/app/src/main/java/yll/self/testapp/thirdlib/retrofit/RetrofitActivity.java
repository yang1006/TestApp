package yll.self.testapp.thirdlib.retrofit;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import yll.self.testapp.R;
import yll.self.testapp.thirdlib.retrofit.api.GitUserApi;
import yll.self.testapp.thirdlib.retrofit.api.GitUserApiRxJava;
import yll.self.testapp.thirdlib.retrofit.model.GitUserModel;
import yll.self.testapp.utils.LogUtil;

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
                //获取github原始数据
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
                //获取github数据并格式化数据
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


        Button btn_click3 = (Button) findViewById(R.id.btn_click3);
        btn_click3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRetrofitWithRxJava();
            }
        });
    }

    //RxJava 和 Retrofit 结合的网络请求
    private void testRetrofitWithRxJava(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitUserApiRxJava api = retrofit.create(GitUserApiRxJava.class);

        api.getUserFeed("yang1006")
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())  // doOnSubscribe 回调在主线程执行
                .observeOn(Schedulers.newThread())   // doOnNext 在新线程执行
                .doOnNext(new Action1<GitUserModel>() {
                    @Override
                    public void call(GitUserModel gitUserModel) {
                        //这里可以先对返回的数据进行处理
                        LogUtil.yll("doOnNext->"+Thread.currentThread());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())   // Subscriber 回调在主线程执行
                .subscribe(new Subscriber<GitUserModel>() {

                    @Override
                    public void onStart() {
                        LogUtil.yll("onStart->" + Thread.currentThread());
                    }

                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        tv_content.setText("");
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RetrofitActivity.this, "网络异常请重试", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(GitUserModel gitUserModel) {
                        LogUtil.yll("onNext->"+ Thread.currentThread());
                        tv_content.setText("login: "+ gitUserModel.getLogin()
                                + " \nid: " + gitUserModel.getId()
                                + " \navatar_url : " + gitUserModel.getAvatarUrl()
                                + " \nurl: " + gitUserModel.getUrl()
                                + " \nhtml_url: " + gitUserModel.getHtmlUrl());
                    }
                });

    }



}
