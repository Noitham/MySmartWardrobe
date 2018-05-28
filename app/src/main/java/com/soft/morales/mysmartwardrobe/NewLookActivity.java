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

public class NewLookActivity extends AppCompatActivity {

    ImageView imgTorso, imgLegs, imgFeets;

    private String uriTorso, uriLegs, uriFeets;

    private String myTorso, myLegs, myFeets;

    String myString = null;

    android.support.design.widget.FloatingActionButton buttonFavourite, butonDelete;

    private String foto;
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

        if (bundle != null) {
            foto = bundle.getString("Foto", "");
            part = bundle.getString("Part", "");
            Log.d("URI:", foto);

            BitmapFactory.Options options = new BitmapFactory.Options();

            final Bitmap bitmap = BitmapFactory.decodeFile(Uri.parse(foto).getPath(), options);
            Log.d("PART:", part);


            if (myTorso != null) {
                myTorso = savedInstanceState.getString("uriTorso");

                final Bitmap bitmap2 = BitmapFactory.decodeFile(Uri.parse(myTorso).getPath(), options);
                imgTorso.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imgTorso.setImageBitmap(bitmap2);
            }

            if (myLegs != null) {
                myLegs = savedInstanceState.getString("uriLegs");

                final Bitmap bitmap2 = BitmapFactory.decodeFile(Uri.parse(myLegs).getPath(), options);
                imgLegs.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imgLegs.setImageBitmap(bitmap2);
            }

            if (myFeets != null) {
                myFeets = savedInstanceState.getString("uriFeets");

                final Bitmap bitmap2 = BitmapFactory.decodeFile(Uri.parse(myFeets).getPath(), options);
                imgFeets.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imgFeets.setImageBitmap(bitmap2);
            }


            if (part.equalsIgnoreCase("Camiseta")) {
                uriTorso = foto;
                imgTorso.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imgTorso.setImageBitmap(bitmap);
            } else if (part.equalsIgnoreCase("Pantalones")) {
                uriLegs = foto;
                imgLegs.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imgLegs.setImageBitmap(bitmap);
            } else if (part.equalsIgnoreCase("Bambas")) {
                uriFeets = foto;
                imgFeets.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imgFeets.setImageBitmap(bitmap);
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Bundle vacío", Toast.LENGTH_SHORT)
                    .show();
        }


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
        Intent intent2 = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ok", 1);
        intent2.putExtras(bundle);
        startActivity(intent2);
        if (myString != null) {

            Log.d("YOU MUST RUN:", myString);

        } else {
            Log.d("NOT ENTERING:", "MIAU");

        }
        finish();

    }


    public void setLegs() {
        Intent intent2 = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ok", 1);
        intent2.putExtras(bundle);
        startActivity(intent2);
        finish();

    }

    public void setFeets() {

        Intent intent2 = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ok", 1);
        intent2.putExtras(bundle);
        startActivity(intent2);
        finish();
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

    @Override
    public void onResume() {
        super.onResume();
        BitmapFactory.Options options = new BitmapFactory.Options();
        /*
        if (lookPrueba.getTorso() != null) {
            myTorso = lookPrueba.getTorso();
            Log.d("TORSO:", String.valueOf(myTorso));
            final Bitmap bitmap2 = BitmapFactory.decodeFile(Uri.parse(myTorso).getPath(), options);
            imgTorso.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgTorso.setImageBitmap(bitmap2);
        } else if (lookPrueba.getLegs() != null) {
            myLegs = lookPrueba.getLegs();
            Log.d("LEGS:", String.valueOf(myLegs));
            final Bitmap bitmap2 = BitmapFactory.decodeFile(Uri.parse(myLegs).getPath(), options);
            imgLegs.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgLegs.setImageBitmap(bitmap2);
        } else if (lookPrueba.getFeets() != null) {
            myFeets = lookPrueba.getFeets();
            Log.d("FEETS:", String.valueOf(myFeets));
            final Bitmap bitmap2 = BitmapFactory.decodeFile(Uri.parse(myFeets).getPath(), options);
            imgFeets.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgFeets.setImageBitmap(bitmap2);
        }
            */
    }

    private AlertDialog askAddFavourite()
    {
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

    private AlertDialog askDelete()
    {
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



