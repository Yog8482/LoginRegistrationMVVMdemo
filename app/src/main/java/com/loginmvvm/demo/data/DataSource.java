package com.loginmvvm.demo.data;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loginmvvm.demo.data.model.LoggedInUser;
import com.loginmvvm.demo.data.model.NewUser;
import com.loginmvvm.demo.ui.registration.NewUserResult;

import java.io.IOException;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class DataSource {


    static final String BASE_URL = "http://www.mocky.io/v2/";

    private Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(new OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    private FetchDataService serviceApi = retrofit.create(FetchDataService.class);

    public Call<Result> createUser(NewUser user) {

       return serviceApi.registerUser(user);

    }

    public Call<Result> login(String username, String password) {
      return serviceApi.login(username, password);
    }


    public void logout() {
        // TODO: revoke authentication
    }

}

interface FetchDataService {

    /**
     * Create user or register api call.Here we assume all possible errors and exceptions are handled by server and we received response.
     */

    @GET("5ec7d8972f0000660b427536")
    Call<Result> registerUser(@Query("new_user") NewUser new_user);

    @GET("5ec7d8972f0000660b427536")
    Call<Result> login(@Query("username") String username,
                       @Query("password") String password);

//    @GET("register")
//    Call<LiveData<UserRegistrationForm>> registerUser(@Query("new_user") UserRegistrationForm new_user);

}
