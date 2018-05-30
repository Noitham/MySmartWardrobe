package com.soft.morales.mysmartwardrobe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.soft.morales.mysmartwardrobe.model.User;
import com.soft.morales.mysmartwardrobe.model.persist.APIService;
import com.soft.morales.mysmartwardrobe.model.persist.ApiUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @InjectView(R.id.input_name)
    EditText nameText;
    @InjectView(R.id.input_email)
    EditText emailText;
    @InjectView(R.id.input_password)
    EditText passwordText;
    @InjectView(R.id.btn_signup)
    Button signupButton;
    @InjectView(R.id.link_login)
    TextView loginLink;
    @InjectView(R.id.spinner_age)
    Spinner spinnerAge;

    private APIService mAPIService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        setupSpinners();

        mAPIService = ApiUtils.getAPIService();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_NoActionBar);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String age = spinnerAge.getSelectedItem().toString();

        // TODO: Implement your own signup logic here.
        createAccount(name, email, password, age);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        Bundle bundle = new Bundle();

        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);

        bundle.putString("email", emailText.getText().toString());

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String age = spinnerAge.getSelectedItem().toString();

        TextView errorText = (TextView) spinnerAge.getSelectedView();

        if (age.equalsIgnoreCase("edad")) {
            errorText.setError("anything here, just to add the icon");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("please, choose an option");//changes the selected item text to this
            valid = false;
        } else {

        }

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            passwordText.setError("between 4 and 15 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    private void setupSpinners() {

        spinnerAge = (Spinner) findViewById(R.id.spinner_age);

        ArrayAdapter<String> adapterAge = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };

        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterAge.add("<10");
        adapterAge.add("11");
        adapterAge.add("12");
        adapterAge.add("13");
        adapterAge.add("14");
        adapterAge.add("15");
        adapterAge.add("16");
        adapterAge.add("17");
        adapterAge.add("18");
        adapterAge.add("19");
        adapterAge.add("20");
        adapterAge.add("21");
        adapterAge.add("22");
        adapterAge.add("23");
        adapterAge.add("24");
        adapterAge.add("25");
        adapterAge.add("26");
        adapterAge.add("27");
        adapterAge.add("28");
        adapterAge.add("29");
        adapterAge.add("30");
        adapterAge.add("31");
        adapterAge.add("32");
        adapterAge.add("33");
        adapterAge.add("34");
        adapterAge.add("35");
        adapterAge.add("36");
        adapterAge.add("37");
        adapterAge.add("38");
        adapterAge.add("39");
        adapterAge.add("40");
        adapterAge.add("41");
        adapterAge.add("42");
        adapterAge.add("43");
        adapterAge.add("44");
        adapterAge.add("45");
        adapterAge.add("46");
        adapterAge.add("47");
        adapterAge.add("48");
        adapterAge.add("49");
        adapterAge.add("50");
        adapterAge.add("51");
        adapterAge.add("52");
        adapterAge.add("53");
        adapterAge.add("54");
        adapterAge.add("55");
        adapterAge.add("56");
        adapterAge.add("57");
        adapterAge.add("58");
        adapterAge.add("59");
        adapterAge.add("60");
        adapterAge.add("61");
        adapterAge.add("62");
        adapterAge.add("63");
        adapterAge.add("64");
        adapterAge.add(">65");
        adapterAge.add("Edad");

        spinnerAge.setAdapter(adapterAge);
        spinnerAge.setSelection(adapterAge.getCount()); //display hint

    }

    public void createAccount(String name, String email, String password, String age) {

        mAPIService.createAccount(name, email, password, age).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    Log.d("OK: ", "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("NOOK: ", "Unable to submit post to API.");
            }
        });
    }

}