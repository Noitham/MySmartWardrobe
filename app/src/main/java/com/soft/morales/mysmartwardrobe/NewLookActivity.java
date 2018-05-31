package com.soft.morales.mysmartwardrobe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.soft.morales.mysmartwardrobe.model.Look;
import com.soft.morales.mysmartwardrobe.model.User;
import com.soft.morales.mysmartwardrobe.model.persist.APIService;
import com.soft.morales.mysmartwardrobe.model.persist.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewLookActivity extends AppCompatActivity {

    private static final int FOTO_REQUEST = 456;

    ImageView imgTorso, imgLegs, imgFeets;

    Intent intent2;

    Intent intent3;

    String myString = null;

    android.support.design.widget.FloatingActionButton buttonCreateLook, butonDelete;

    private String foto;
    private String idShirt, idLegs, idFeet;

    private String type;

    private APIService mAPIService;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_look);

        setupToolbar();

        mAPIService = ApiUtils.getAPIService();

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Bundle bundle = this.getIntent().getExtras();

        imgTorso = (ImageView) findViewById(R.id.torso_hombre);
        imgLegs = (ImageView) findViewById(R.id.pantalones_hombres);
        imgFeets = (ImageView) findViewById(R.id.pies_hombre);

        butonDelete = (android.support.design.widget.FloatingActionButton) findViewById(R.id.deleteLook);
        buttonCreateLook = (android.support.design.widget.FloatingActionButton) findViewById(R.id.addLook);


        imgTorso.setOnClickListener(new FloatingActionButton.OnClickListener() {
            public void onClick(View v) {

                setTorso();

            }
        });


        imgLegs.setOnClickListener(new FloatingActionButton.OnClickListener() {
            public void onClick(View v) {
                setLegs();
            }
        });


        imgFeets.setOnClickListener(new FloatingActionButton.OnClickListener() {
            public void onClick(View v) {
                setFeets();
            }
        });

        buttonCreateLook.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                AlertDialog diaBox = askCreateLook();

                diaBox.show();

            }
        });

        butonDelete.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                AlertDialog diaBox = askDelete();
                diaBox.show();
            }
        });

    }


    public void setTorso() {
        Bundle bundle = new Bundle();
        bundle.putInt("ok", 1);

        intent2 = new Intent(this, MainActivity.class);
        intent2.putExtras(bundle);

        startActivityForResult(intent2, FOTO_REQUEST);
    }


    public void setLegs() {
        Bundle bundle = new Bundle();
        bundle.putInt("ok", 1);

        intent2 = new Intent(this, MainActivity.class);
        intent2.putExtras(bundle);

        startActivityForResult(intent2, FOTO_REQUEST);

    }

    public void setFeets() {
        Bundle bundle = new Bundle();
        bundle.putInt("ok", 1);

        intent2 = new Intent(this, MainActivity.class);
        intent2.putExtras(bundle);

        startActivityForResult(intent2, FOTO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FOTO_REQUEST && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();

            intent3 = data;

            if (bundle != null) {
                type = bundle.getString("garmentType", "");
                foto = bundle.getString("Foto", "");

                if (type.equalsIgnoreCase("Shirt")) {
                    Glide.with(this).load(Uri.parse(foto)).into(imgTorso);
                } else if (type.equalsIgnoreCase("Legs")) {
                    Glide.with(this).load(Uri.parse(foto)).into(imgLegs);
                } else if (type.equalsIgnoreCase("Feet")) {
                    Glide.with(this).load(Uri.parse(foto)).into(imgFeets);
                }

            } else {
                Toast.makeText(getApplicationContext(),
                        "Bundle vacío", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        myString = savedInstanceState.getString("MyString");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        savedInstanceState.putString("MyString", "Welcome back to Android");
        // etc.
        super.onSaveInstanceState(savedInstanceState);

    }

    private AlertDialog askCreateLook() {

        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Confirm")
                .setMessage("Do you want to save this look to current day?")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    public void onClick(DialogInterface dialog, int whichButton) {

                        createLookPost(intent3);

                    }

                })

                .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;

    }

    private AlertDialog askDelete() {

        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Confirm")
                .setMessage("Are you sure you want to delete the current look?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Glide.with(getApplication()).load(R.drawable.torso_hombre).into(imgTorso);
                        Glide.with(getApplication()).load(R.drawable.pantalon_hombre).into(imgLegs);
                        Glide.with(getApplication()).load(R.drawable.pies_hombre).into(imgFeets);
                        dialog.dismiss();
                    }

                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;

    }

    private void setupToolbar() {

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void createLookPost(Intent data) {

        Log.d("POST:", "ENTRA");

        if (data != null && data.getExtras() != null) {
            Bundle bundle = data.getExtras();

            Log.d("BUNDLE:", "ENTRA");
            type = bundle.getString("garmentType", "");
            foto = bundle.getString("Foto", "");

            SharedPreferences shared = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            idShirt = shared.getString("idShirt", "");
            idLegs = shared.getString("idLegs", "");
            idFeet = shared.getString("idFeet", "");

            List<Integer> myGarments = new ArrayList<>();

            if (type.equalsIgnoreCase("Camiseta")) {
                Glide.with(this).load(Uri.parse(foto)).into(imgTorso);
            } else if (type.equalsIgnoreCase("Legs")) {
                Glide.with(this).load(Uri.parse(foto)).into(imgLegs);
            } else if (type.equalsIgnoreCase("Feet")) {
                Glide.with(this).load(Uri.parse(foto)).into(imgFeets);
            }

            if (!idShirt.equals("")) {
                myGarments.add(Integer.parseInt(idShirt));
            } else {
                Toast.makeText(getApplicationContext(),
                        "Por favor, complete su look.", Toast.LENGTH_SHORT)
                        .show();
            }
            if (!idLegs.equals("")) {
                myGarments.add(Integer.parseInt(idLegs));
            } else {
                Toast.makeText(getApplicationContext(),
                        "Por favor, complete su look.", Toast.LENGTH_SHORT)
                        .show();
            }
            if (!idFeet.equals("")) {
                myGarments.add(Integer.parseInt(idFeet));
            } else {
                Toast.makeText(getApplicationContext(),
                        "Por favor, complete su look.", Toast.LENGTH_SHORT)
                        .show();
            }

            if (myGarments.size() == 3) {

                Gson gson = new Gson();
                shared = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                User user = gson.fromJson(shared.getString("user", ""), User.class);

                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);
                Log.d("Current time => ", String.valueOf(c));

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = df.format(c);

                Log.d("Current time => ", String.valueOf(formattedDate));

                mAPIService.sendLook(myGarments, user.getEmail(), formattedDate).enqueue(new Callback<Look>() {
                    @Override
                    public void onResponse(Call<Look> call, Response<Look> response) {

                        if (response.isSuccessful()) {
                            Log.d("OK: ", "post submitted to API." + response.body().toString());
                            finish();
                        }
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Look> call, Throwable t) {
                        Log.d("NOOK: ", "Unable to submit post to API.");
                    }
                });

                // Clean the sharedPreferences
                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                sharedPref.edit().remove("idShirt").apply();
                sharedPref.edit().remove("idLegs").apply();
                sharedPref.edit().remove("idFeet").apply();

                finish();

            } else {
                Log.d("POST:", "VACÍO");
                Toast.makeText(getApplicationContext(),
                        "Por favor, complete su look.", Toast.LENGTH_SHORT)
                        .show();
            }

        }

    }


}

