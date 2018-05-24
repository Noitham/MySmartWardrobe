package com.soft.morales.mysmartwardrobe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.soft.morales.mysmartwardrobe.model.Garment;

import java.util.List;

public class NewLookActivity extends AppCompatActivity {

    ImageView imgTorso, imgLegs, imgFeets;

    List<Garment> myGarments, myShirts, myJerseys, myJackets, myJeans, myShoes, myAccessories;

    private ListView listView;

    private String foto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_look);

        Bundle bundle = this.getIntent().getExtras();

        imgTorso = (ImageView) findViewById(R.id.torso_hombre);

        if (bundle != null) {
            foto = bundle.getString("Foto", "");

            Log.d("URI:", foto);

            BitmapFactory.Options options = new BitmapFactory.Options();

            final Bitmap bitmap = BitmapFactory.decodeFile(Uri.parse(foto).getPath(), options);

            imgTorso.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imgTorso.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Bundle vacío", Toast.LENGTH_SHORT)
                    .show();
        }


        imgLegs = (ImageView) findViewById(R.id.pantalones_hombres);
        imgFeets = (ImageView) findViewById(R.id.pies_hombre);

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

    }


    public void setTorso() {
        Intent intent2 = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("ok", 1);
        intent2.putExtras(bundle);
        startActivity(intent2);

    }


    public void setLegs() {


    }

    public void setFeets() {


    }


}
