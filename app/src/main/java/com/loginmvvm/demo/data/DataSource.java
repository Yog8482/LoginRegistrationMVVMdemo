package com.loginmvvm.demo.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loginmvvm.demo.data.model.LoggedInUser;
import com.loginmvvm.demo.data.model.NewUser;

import java.io.IOException;

import okhttp3.OkHttpClient;
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

    public Result<NewUser> createUser(NewUser user) {
        try {
            // TODO: handle loggedInUser authentication
//            LoggedInUser fakeUser =
//                    new LoggedInUser(
//                            java.util.UUID.randomUUID().toString(),
//                            "Yog B");
//            return new Result.Success<>(fakeUser);
            Result result = serviceApi.registerUser(user);
            if (result instanceof Result.Success)
                return (Result<NewUser>) ((Result.Success) result).getData();
            else
                return new Result.Error(((Result.Error) result).getError());

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }

        /*Call<LiveData<UserRegistrationForm>> call = serviceApi.registerUser(user);


        call.enqueue(new Callback<LiveData<UserRegistrationForm>>() {
            @Override
            public void onResponse(Call<LiveData<UserRegistrationForm>> call, Response<LiveData<UserRegistrationForm>> response) {

                //finally we are setting the list to our MutableLiveData

            }

            @Override
            public void onFailure(Call<LiveData<UserRegistrationForm>> call, Throwable t) {

            }
        });*/
    }

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
//            LoggedInUser fakeUser =
//                    new LoggedInUser(
//                            java.util.UUID.randomUUID().toString(),
//                            "Yog B");
//            return new Result.Success<>(fakeUser);
            Result result = serviceApi.login(username, password);
            if (result instanceof Result.Success)
                return (Result<LoggedInUser>) ((Result.Success) result).getData();
            else
                return new Result.Error(((Result.Error) result).getError());

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
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
    Result registerUser(@Query("new_user") NewUser new_user);

    @GET("login")
    Result login(@Query("username") String username,
                 @Query("password") String password);

//    @GET("register")
//    Call<LiveData<UserRegistrationForm>> registerUser(@Query("new_user") UserRegistrationForm new_user);

}
