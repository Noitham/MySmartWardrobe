package com.soft.morales.mysmartwardrobe;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.soft.morales.mysmartwardrobe.model.Garment;
import com.soft.morales.mysmartwardrobe.model.Look;
import com.soft.morales.mysmartwardrobe.model.User;
import com.soft.morales.mysmartwardrobe.model.persist.APIService;
import com.soft.morales.mysmartwardrobe.model.persist.ApiUtils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckLookActivity extends AppCompatActivity {

    public static final int CHECK_RESULT_ACTIVITY = 4001;

    private APIService mAPIService;
    SharedPreferences sharedPref;
    Gson gson;

    ImageView imgTorso, imgLegs, imgFeets;

    android.support.design.widget.FloatingActionButton buttonCreateLook, butonDelete;

    Look look;
    List<Look> looks;

    private User mUser;

    Garment Torso, Piernas, Pies;

    String date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_look);

        setupToolbar();

        // Declare new Gson
        gson = new Gson();
        // Declare SharedPreferences variable so we can acced to our SharedPreferences
        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);

        // We build a User from our given information from the sharedPref file (User in Gson format)
        mUser = gson.fromJson(sharedPref.getString("user", ""), User.class);

        // Set our components
        imgTorso = (ImageView) findViewById(R.id.torso_hombre);
        imgLegs = (ImageView) findViewById(R.id.pantalones_hombres);
        imgFeets = (ImageView) findViewById(R.id.pies_hombre);

        butonDelete = (android.support.design.widget.FloatingActionButton) findViewById(R.id.deleteLook);
        buttonCreateLook = (android.support.design.widget.FloatingActionButton) findViewById(R.id.addLook);

        butonDelete.hide();
        buttonCreateLook.hide();

        mAPIService = ApiUtils.getAPIService();

        date = getIntent().getExtras().getString("date");
        Log.d("DATE: ", String.valueOf(date));

        getLook();

    }

    public void getLook() {

        mAPIService = ApiUtils.getAPIService();

        HashMap query = new HashMap();
        query.put("date", date);
        query.put("username", mUser.getEmail());

        HashMap query1 = new HashMap();
        HashMap query2 = new HashMap();
        HashMap query3 = new HashMap();

        Log.d("mUSER", String.valueOf(mUser.getEmail()));
        Log.d("date", String.valueOf(date));

        Call<List<Look>> call = mAPIService.getLooks(query);

        call.enqueue(new Callback<List<Look>>() {
            @Override
            public void onResponse(Call<List<Look>> call, Response<List<Look>> response) {

                looks = response.body();

                if (looks.size() > 0 && looks.get(0).getGarmentsIds().size() == 3) {
                    look = looks.get(0);

                    Call<Garment> call1 = mAPIService.getGarment(look.getGarmentsIds().get(0));
                    Call<Garment> call2 = mAPIService.getGarment(look.getGarmentsIds().get(1));
                    Call<Garment> call3 = mAPIService.getGarment(look.getGarmentsIds().get(2));

                    query1.put("id", look.getGarmentsIds().get(0));
                    query2.put("id", look.getGarmentsIds().get(1));
                    query3.put("id", look.getGarmentsIds().get(2));

                    call1.enqueue(new Callback<Garment>() {
                        @Override
                        public void onResponse(Call<Garment> call, Response<Garment> response) {

                            Torso = response.body();

                            if (Torso != null) {
                                Glide.with(getApplication()).load(Uri.parse(String.valueOf(Torso.getPhoto()))).into(imgTorso);

                                Log.d("photo1", String.valueOf(Torso.getPhoto()));
                            } else {
                                Toast.makeText(getApplication(), "There's no look for the current day", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Garment> call, Throwable t) {
                            Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                    call2.enqueue(new Callback<Garment>() {
                        @Override
                        public void onResponse(Call<Garment> call, Response<Garment> response) {

                            Piernas = response.body();

                            if (Piernas != null) {
                                Glide.with(getApplication()).load(Uri.parse(String.valueOf(Piernas.getPhoto()))).into(imgLegs);

                                Log.d("photo2", String.valueOf(Piernas.getPhoto()));
                            } else {
                                Toast.makeText(getApplication(), "There's no look for the current day", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Garment> call, Throwable t) {
                            Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


                    call3.enqueue(new Callback<Garment>() {
                        @Override
                        public void onResponse(Call<Garment> call, Response<Garment> response) {

                            Pies = response.body();

                            if (Pies != null) {
                                Glide.with(getApplication()).load(Uri.parse(String.valueOf(Pies.getPhoto()))).into(imgFeets);

                                Log.d("photo3", String.valueOf(Pies.getPhoto()));
                            } else {
                                Toast.makeText(getApplication(), "There's no look for the current day", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Garment> call, Throwable t) {
                            Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    setResult(RESULT_CANCELED);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<List<Look>> call, Throwable t) {
                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


    private void setupToolbar() {

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
