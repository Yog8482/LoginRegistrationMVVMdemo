package com.loginmvvm.demo.data;

import androidx.lifecycle.LiveData;

import com.loginmvvm.demo.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private DataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        LiveData<Result> result = dataSource.login(username, password);
        if (result.getValue() instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result.getValue()).getData());
        }
        return result.getValue();
    }
}
