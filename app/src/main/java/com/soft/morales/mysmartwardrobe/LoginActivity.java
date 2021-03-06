package com.soft.morales.mysmartwardrobe;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.soft.morales.mysmartwardrobe.model.User;
import com.soft.morales.mysmartwardrobe.model.persist.APIService;
import com.soft.morales.mysmartwardrobe.model.persist.ApiUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A login screen that offers login via email/passwords.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_email)
    EditText emailText;
    @InjectView(R.id.input_password)
    EditText passwordText;
    @InjectView(R.id.btn_login)
    Button loginButton;
    @InjectView(R.id.link_signup)
    TextView signupLink;

    User user;
    String emailUser = null;
    String email;
    String password;

    boolean check = false;

    private APIService mAPIService;

    List<User> myUsers;

    List<String> emails = new ArrayList<>();
    List<String> passwords = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        if (getIntent().getExtras() != null) {

            emailUser = getIntent().getExtras().getString("email");

            emailText.setText(emailUser);

        } else {
            Log.d("LOGIN: ", "BUNDLE EMPTY");
        }

        mAPIService = ApiUtils.getAPIService();

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void login() throws NoSuchAlgorithmException {
        Log.d(TAG, "Login");

        if (!validate()) {
            loginButton.setEnabled(true);
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_NoActionBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        email = emailText.getText().toString();
        password = passwordText.getText().toString();

        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(password.getBytes(), 0, password.length());

        password = new BigInteger(1, m.digest()).toString(16);

        // TODO: Implement your own authentication logic here.

        getAllUsers();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (myUsers != null) {

                            for (int i = 0; i < myUsers.size(); i++) {

                                if (email.equalsIgnoreCase(emails.get(i).toString()) && password.equalsIgnoreCase(passwords.get(i).toString())) {
                                    check = true;

                                    user = myUsers.get(i);
                                    onLoginSuccess();

                                    break;
                                }
                            }

                            if (!check) {
                                onLoginFailed();
                            }
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);


    }

    public void getAllUsers() {

        mAPIService = ApiUtils.getAPIService();

        Call<List<User>> call = mAPIService.loginUser();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                List<User> users = response.body();

                myUsers = new ArrayList<>();

                for (int i = 0; i < users.size(); i++) {

                    myUsers.add(new User(users.get(i)));

                    emails.add(myUsers.get(i).getEmail());
                    passwords.add(myUsers.get(i).getPassword());
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                Bundle bun = new Bundle();
                bun.putString("email", emailUser);

                Intent intent2 = new Intent(this, MainActivity.class);
                intent2.putExtras(bun);

                startActivity(intent2);
                finish();
            } else {
                loginButton.setEnabled(true);
            }
        }

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        loginButton.setEnabled(true);

        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        sharedPref.edit().putString("user", userJson).apply();

        Intent intent2 = new Intent(this, MainActivity.class);

        check = true;

        startActivity(intent2);
        finish();
    }

    public void onLoginFailed() {

        Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}
