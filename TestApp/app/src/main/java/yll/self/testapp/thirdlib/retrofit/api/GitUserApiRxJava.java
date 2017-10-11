package yll.self.testapp.thirdlib.retrofit.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import yll.self.testapp.thirdlib.retrofit.model.GitUserModel;

/**
 * Created by yll on 17/10/11.
 * https://api.github.com/users/yang1006
 */

public interface GitUserApiRxJava {

    @GET("users/{user}")
    Observable<GitUserModel> getUserFeed(@Path("user")String user);

    @GET("users/{user}")
    Observable<ResponseBody> getUserFeedBody(@Path("user")String user);
}
