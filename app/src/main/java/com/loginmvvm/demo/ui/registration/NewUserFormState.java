package com.loginmvvm.demo.ui.registration;

public class NewUserFormState {

    private Integer nameError;
    private Integer passwordError;
    private Integer emailError;
    private Integer confirm_passwordError;
    private boolean isDataValid;

    public NewUserFormState(Integer nameError, Integer passwordError, Integer emailError, Integer confirm_passwordError) {
        this.nameError = nameError;
        this.passwordError = passwordError;
        this.emailError = emailError;
        this.confirm_passwordError = confirm_passwordError;
        this.isDataValid = false;
    }

    NewUserFormState(Boolean isDataValid){
        this.nameError = null;
        this.passwordError = null;
        this.emailError = null;
        this.confirm_passwordError = null;
        this.isDataValid = isDataValid;
    }
    public Integer getNameError() {
        return nameError;
    }

    public Integer getPasswordError() {
        return passwordError;
    }

    public Integer getEmailError() {
        return emailError;
    }

    public Integer getConfirm_passwordError() {
        return confirm_passwordError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
