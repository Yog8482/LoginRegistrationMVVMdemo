package com.loginmvvm.demo.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loginmvvm.demo.data.model.LoggedInUser;
import com.loginmvvm.demo.data.model.NewUser;

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


    static final String BASE_URL = "https://getsandbox.com/";

    private Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(new OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    private FetchDataService serviceApi = retrofit.create(FetchDataService.class);

    public LiveData<Result> createUser(NewUser user) {

        final MutableLiveData<Result> data = new MutableLiveData<>();

        serviceApi.registerUser(user)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        data.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        data.postValue(new Result.Error(new Exception(t.getMessage())));

                    }
                });


        return data;
    }

    public LiveData<Result> login(String username, String password) {
        final MutableLiveData<Result> data = new MutableLiveData<>();
        serviceApi.login(username, password)
                .enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        data.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        data.setValue(new Result.Error(new Exception(t.getMessage())));

                    }
                });
        return data;

    }


    public void logout() {
        // TODO: revoke authentication
    }

}

interface FetchDataService {

    /**
     * Create user or register api call.Here we assume all possible errors and exceptions are handled by server and we received response.
     */

    @GET("register")
    Call<Result> registerUser(@Query("new_user") NewUser new_user);

    @GET("login")
    Call<Result> login(@Query("username") String username,
                       @Query("password") String password);

//    @GET("register")
//    Call<LiveData<UserRegistrationForm>> registerUser(@Query("new_user") UserRegistrationForm new_user);

}
