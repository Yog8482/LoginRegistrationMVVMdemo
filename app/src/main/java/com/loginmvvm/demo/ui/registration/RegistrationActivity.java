package com.loginmvvm.demo.ui.registration;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loginmvvm.demo.R;
import com.loginmvvm.demo.data.model.NewUser;


public class RegistrationActivity extends AppCompatActivity {
    private NewUserViewModel newUserViewModel;
    ProgressBar registrationProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        newUserViewModel = new ViewModelProvider(this, new NewUserViewModelFactory())
                .get(NewUserViewModel.class);


        final TextView gotologin = findViewById(R.id.txtSigninLink);

        final EditText nameEditText = findViewById(R.id.txtDisplayName);
        final EditText emailEditText = findViewById(R.id.txtEmail);
        final EditText mobileEditText = findViewById(R.id.txtMobile);
        final EditText addressEditText = findViewById(R.id.txtAddress);
        final EditText cityEditText = findViewById(R.id.txtCity);
        final EditText pincodeEditText = findViewById(R.id.txtPincode);
        final EditText passwordEditText = findViewById(R.id.txtPassword);
        final EditText confPasswordEditText = findViewById(R.id.txtConfirmPassword);
        registrationProgressBar = findViewById(R.id.spinner);

        final Button signupButton = findViewById(R.id.btnSignup);

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NewUser newUser = new NewUser(nameEditText.getText().toString(), emailEditText.getText().toString(),
                        mobileEditText.getText().toString(), passwordEditText.getText().toString(),
                        addressEditText.getText().toString(), pincodeEditText.getText().toString(),
                        cityEditText.getText().toString());


                newUserViewModel.createNewUser(newUser);

            }
        });

        // show the spinner when [spinner] is true
        newUserViewModel.spinner.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean showLoading) {
                if (showLoading)
                    registrationProgressBar.setVisibility(View.VISIBLE);
                else
                    registrationProgressBar.setVisibility(View.GONE);
            }
        });


        newUserViewModel.getNewUserFormState().observe(this, new Observer<NewUserFormState>() {
            @Override
            public void onChanged(NewUserFormState newUserFormState) {
                if (newUserFormState == null) return;

                signupButton.setEnabled(newUserFormState.isDataValid());

                if (newUserFormState.getNameError() != null) {
                    nameEditText.setError(getString(newUserFormState.getNameError()));
                }
                if (newUserFormState.getEmailError() != null) {
                    emailEditText.setError(getString(newUserFormState.getEmailError()));
                }
                if (newUserFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(newUserFormState.getPasswordError()));
                }
                if (newUserFormState.getConfirm_passwordError() != null) {
                    confPasswordEditText.setError(getString(newUserFormState.getConfirm_passwordError()));
                }
            }
        });


        newUserViewModel.getNewUserResult().observe(this, new Observer<NewUserResult>() {
            @Override
            public void onChanged(NewUserResult newUserResult) {

                if (newUserResult == null) {
                    return;
                }
                if (newUserResult.getError() != null) {
                    showLoginFailed(newUserResult.getError());
                }
                if (newUserResult.getSuccess()) {
                    updateUiWithUser(R.string.registration_success);
                    finish();

                }
//                setResult(Activity.RESULT_OK);


            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                newUserViewModel.newUserDataChanged(nameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        confPasswordEditText.getText().toString());
            }
        };
        nameEditText.addTextChangedListener(afterTextChangedListener);
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        confPasswordEditText.addTextChangedListener(afterTextChangedListener);
    }

    private void updateUiWithUser(@StringRes Integer successString) {
        String welcome = getString(successString);
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}