package com.loginmvvm.demo.ui.registration;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.loginmvvm.demo.data.DataSource;
import com.loginmvvm.demo.data.NewUserRepository;

public class NewUserViewModelFactory implements ViewModelProvider.Factory {

    /**
     * ViewModel provider factory to instantiate NewUserViewModel.
     * Required given NewUserViewModel has a non-empty constructor
     */

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(NewUserViewModel.class))
            return (T) new NewUserViewModel(NewUserRepository.getInstance(new DataSource()));
        else
            throw new IllegalArgumentException("Unknown ViewModel class");

    }
}
