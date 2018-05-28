package com.soft.morales.mysmartwardrobe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class NewLookActivity extends AppCompatActivity {

    private static final int FOTO_REQUEST = 456;

    ImageView imgTorso, imgLegs, imgFeets;

    Intent intent2;

    String myString = null;

    android.support.design.widget.FloatingActionButton buttonFavourite, butonDelete;

    private String foto;

    String foto1;
    String foto2;
    String foto3;

    private String part;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_look);

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Bundle bundle = this.getIntent().getExtras();

        imgTorso = (ImageView) findViewById(R.id.torso_hombre);
        imgLegs = (ImageView) findViewById(R.id.pantalones_hombres);
        imgFeets = (ImageView) findViewById(R.id.pies_hombre);

        butonDelete = (android.support.design.widget.FloatingActionButton) findViewById(R.id.deleteLook);
        buttonFavourite = (android.support.design.widget.FloatingActionButton) findViewById(R.id.addLook);


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

        buttonFavourite.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                AlertDialog diaBox = askAddFavourite();
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

            if (bundle != null) {
                part = bundle.getString("Part", "");
                foto = bundle.getString("Foto", "");

                BitmapFactory.Options options = new BitmapFactory.Options();

                if (part.equalsIgnoreCase("Camiseta")) {
                    Glide.with(this).load(Uri.parse(foto)).into(imgTorso);
                } else if (part.equalsIgnoreCase("Pantalones")) {
                    Glide.with(this).load(Uri.parse(foto)).into(imgLegs);
                } else if (part.equalsIgnoreCase("Bambas")) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
/*
        lookPrueba.setTorso(myTorso);
        lookPrueba.setLegs(myLegs);
        lookPrueba.setFeets(myFeets);
*/

    }

    private AlertDialog askAddFavourite() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Confirm")
                .setMessage("Do you want to save this look?")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        dialog.dismiss();
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
                .setMessage("Are you sure you want to delete?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
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


}

