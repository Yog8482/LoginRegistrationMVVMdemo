package com.loginmvvm.demo.data;

import androidx.lifecycle.LiveData;

import com.loginmvvm.demo.data.model.NewUser;

import retrofit2.Call;

public class NewUserRepository {

    private static volatile NewUserRepository newuserRepo_instance;
    private DataSource dataSource;

    private NewUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static NewUserRepository getInstance(DataSource dataSource) {
        if (newuserRepo_instance == null) {
            newuserRepo_instance = new NewUserRepository(dataSource);
        }
        return newuserRepo_instance;
    }


    public Call<Result> createNewUser(NewUser new_user) {
        // handle
       return  dataSource.createUser(new_user);

    }
}
