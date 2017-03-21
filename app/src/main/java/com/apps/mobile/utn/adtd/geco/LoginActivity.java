package com.apps.mobile.utn.adtd.geco;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;


import android.os.Build;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.mobile.utn.adtd.geco.Api.ApiClient;
import com.apps.mobile.utn.adtd.geco.Api.ApiInterface;
import com.apps.mobile.utn.adtd.geco.Api.PersonResponse;
import com.apps.mobile.utn.adtd.geco.Api.TokenResponse;
import com.apps.mobile.utn.adtd.geco.Model.Profile;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity{


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private TextView mErrorTextView;
    private View mProgressView;
    private View mLoginFormView;
    private CheckBox mRememberMeCBox;
    private boolean logged=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mErrorTextView = (TextView) findViewById(R.id.error_message) ;
        mRememberMeCBox = (CheckBox) findViewById(R.id.checkbox_remember_me);
        mErrorTextView.setText("");
        mPasswordView = (EditText) findViewById(R.id.password);
        restoreSavedCredentials();
        FloatingActionButton mEmailSignInButton = (FloatingActionButton) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

       /* Button mExitButton = (Button) findViewById(R.id.login_exit_button);
        mExitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    @Override
    protected void onResume() {
        super.onResume();
        showProgress(false);
        mErrorTextView.setText("");

    }

    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            if(mRememberMeCBox.isChecked())
            {
                saveCredentials();
            }
            else
            {
                clearSavedCredentials();
            }

            loginProcess(email, password);



        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void restoreSavedCredentials()
    {
        SharedPreferences prefs = getSharedPreferences("Credentials",Context.MODE_PRIVATE);

        String email = prefs.getString("email", "");
        String password = prefs.getString("password", "");

        if(!email.isEmpty() && !password.isEmpty())
        {

            mEmailView.setText(email);
            mPasswordView.setText(password);
            mRememberMeCBox.setChecked(true);
        }




    }
    private void saveCredentials()
    {
        SharedPreferences prefs = getSharedPreferences("Credentials",Context.MODE_PRIVATE);
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();



        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.commit();

    }

    private void clearSavedCredentials()
    {
        SharedPreferences settings = getSharedPreferences("Credentials", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
        mRememberMeCBox.setChecked(false);
    }

    private void loginProcess(final String email, String password){


        ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TokenResponse> mService = mApiService.login(email, password);
        mService.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {



                int statusCode = response.code();

                switch (statusCode)
                {
                    case 200: //OK

                        Log.d("JLA","Logueado");
                        Profile.getInstance().setTOKEN(response.body().getToken());
                        Profile.getInstance().setEmail(email);
                        Log.d("JLA","Pre Profile");
                        showProgress(true);
                        getProfile(Profile.getInstance().getTOKEN());



                        break;
                    case 401: //Unauthorized

                        mErrorTextView.setText(getString(R.string.error_incorrect_email_password));
                        showProgress(false);


                        break;
                    default: //Server Error
                        mErrorTextView.setText(getString(R.string.error_server));
                        showProgress(false);
                        break;
                }


            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                call.cancel();
                showProgress(false);
                Toast.makeText(LoginActivity.this, getString(R.string.error_connection_failed), Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getProfile(String TOKEN){


        ApiInterface mApiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PersonResponse> mService = mApiService.getProfile(TOKEN);
        mService.enqueue(new Callback<PersonResponse>() {
            @Override
            public void onResponse(Call<PersonResponse> call, Response<PersonResponse> response) {

                int statusCode = response.code();

                switch (statusCode)
                {
                    case 200: //OK

                        Profile.getInstance().setPerson(response.body().getProfile());

                        Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainActivity);

                        break;
                    case 401: //Unauthorized

                        mErrorTextView.setText(getString(R.string.error_incorrect_email_password));

                        break;
                    default: //Server Error
                        mErrorTextView.setText(getString(R.string.error_server));

                        break;
                }
                showProgress(false);


            }

            @Override
            public void onFailure(Call<PersonResponse> call, Throwable t) {
                call.cancel();
                showProgress(false);
                Toast.makeText(LoginActivity.this, getString(R.string.error_connection_failed), Toast.LENGTH_LONG).show();
            }
        });
    }
}

