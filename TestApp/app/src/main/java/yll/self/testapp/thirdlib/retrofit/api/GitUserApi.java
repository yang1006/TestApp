package yll.self.testapp.thirdlib.retrofit.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;
import yll.self.testapp.thirdlib.retrofit.model.GitUserModel;

/**
 * Created by yll on 17/7/18.
 * https://api.github.com/users/yang1006
 *
 * 请求类型注解一共有8个 GET,POST,PUT,DELETE,PATCH,HEAD,OPTIONS,HTTP
 * 除HTTP以外都对应了HTTP标准中的请求方法; 而HTTP注解则可以代替以上方法中的任意一个注解,有3个属性：method、path,hasBody
 */

public interface GitUserApi {

    //添加这个注解会调用服务器，参数url基于BASE URL，服务调用的参数以'/'开头。
    @GET("users/{user}")
    //@Path("user") String user 就是{user}参数
    Call<GitUserModel> getUserFeed(@Path("user")String user);



//    @GET("users/{user}") //http可以
    @HTTP(method = "GET", path = "users/{user}", hasBody = false)
    Call<ResponseBody> getUserFeedBody(@Path("user")String user);

//    @POST()
//    Call<ResponseBody> postSomeData(@QueryMap(encoded = true) Map<String, String> map);
}
