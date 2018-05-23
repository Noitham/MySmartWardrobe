package com.soft.morales.mysmartwardrobe;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.soft.morales.mysmartwardrobe.model.Garment;

import java.util.List;

public class NewLookActivity extends AppCompatActivity {

    ImageView imgTorso, imgLegs, imgFeets;

    List<Garment> myGarments, myShirts, myJerseys, myJackets, myJeans, myShoes, myAccessories;

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_look);

        imgTorso = (ImageView) findViewById(R.id.torso_hombre);
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
