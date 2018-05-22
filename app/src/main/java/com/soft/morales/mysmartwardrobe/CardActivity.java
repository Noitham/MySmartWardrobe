package com.soft.morales.mysmartwardrobe;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {

    TextView txtName, txtCategory, txtSeason, txtPrice, txtColor, txtSize, txtBrand;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garment_details_layout);

        txtName = (TextView) findViewById(R.id.tvName);
        txtCategory = (TextView) findViewById(R.id.tvCategory);
        txtSeason = (TextView) findViewById(R.id.tvSeason);
        txtPrice = (TextView) findViewById(R.id.tvPrice);
        txtColor = (TextView) findViewById(R.id.tvColor);
        txtSize = (TextView) findViewById(R.id.tvSize);
        txtBrand = (TextView) findViewById(R.id.tvBrand);

        imageView = (ImageView) findViewById(R.id.imageView);

        Bundle mbundle = this.getIntent().getExtras();

        String URI = mbundle.getString("Foto");

        txtName.setText("Nombre: " + mbundle.getString("Nombre"));
        txtCategory.setText("Categoría: " + mbundle.getString("Categoria"));
        txtSeason.setText("Temporada: " + mbundle.getString("Temporada"));
        txtPrice.setText("Precio: " + mbundle.getString("Precio"));
        txtColor.setText("Color: " + mbundle.getString("Color"));
        txtSize.setText("Talla: " + mbundle.getString("Talla"));
        txtBrand.setText("Marca: " + mbundle.getString("Marca"));


        Uri myUri = Uri.parse(URI);

        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.getLayoutParams().width = 800;
        imageView.setImageURI(myUri);

    }
}