package com.loginmvvm.demo.ui.registration;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loginmvvm.demo.R;
import com.loginmvvm.demo.data.NewUserRepository;
import com.loginmvvm.demo.data.Result;
import com.loginmvvm.demo.data.model.NewUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class NewUserViewModel extends ViewModel {
    private MutableLiveData<NewUserFormState> newUserFormState = new MutableLiveData<>();
    private MutableLiveData<Result> newUserResult = new MutableLiveData<>();
    private NewUserRepository newUserRepository;

    NewUserViewModel(NewUserRepository newUserRepository) {
        this.newUserRepository = newUserRepository;

    }

    LiveData<NewUserFormState> getNewUserFormState() {
        return newUserFormState;
    }

    LiveData<Result> getNewUserResult() {
        return newUserResult;
    }

     void createNewUser(NewUser user) {

         newUserRepository.createNewUser(user)
                 .enqueue(new Callback<Result>() {
                     @Override
                     public void onResponse(Call<Result> call, Response<Result> response) {
                         newUserResult.setValue(response.body());

                     }

                     @Override
                     public void onFailure(Call<Result> call, Throwable t) {

                         newUserResult.setValue(new Result.Error(new Exception(t.getMessage())));

                     }
                 });


    }

     void newUserDataChanged(String name, String email, String password, String conf_password) {
        if (!isNameValid(name)) {
            newUserFormState.setValue(new NewUserFormState(R.string.invalid_fullname, null,null,null));
        } else if (!isEmailValid(email)) {
            newUserFormState.setValue(new NewUserFormState(null,null,R.string.invalid_email, null));
        } else if (!isPasswordValid(password)) {
            newUserFormState.setValue(new NewUserFormState(null, R.string.invalid_password,null,null));
        } else if (!isPasswordMatch(password, conf_password)) {
            newUserFormState.setValue(new NewUserFormState(null,null,null, R.string.invalid_match_password));
        } else {
            newUserFormState.setValue(new NewUserFormState(true));
        }
    }

    // A placeholder name validation check
    private boolean isNameValid(String name) {
        if (name == null) {
            return false;
        }
        if (name.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(name).matches();
        } else {
            return !name.trim().isEmpty();
        }
    }

    private boolean isEmailValid(String email) {
        if (email == null || !email.contains("@")) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 0;
    }

    private boolean isPasswordMatch(String password, String conf_pswd) {
        return password != null && password.equals(conf_pswd);
    }
}
