package com.loginmvvm.demo.ui.registration;

import androidx.annotation.Nullable;


public class NewUserResult {

    @Nullable
    private boolean success;
    @Nullable
    private Integer error;

    NewUserResult( Integer error) {
        this.error = error;
    }

    public NewUserResult(@Nullable boolean success) {
        this.success = success;
    }

    @Nullable
    boolean getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
