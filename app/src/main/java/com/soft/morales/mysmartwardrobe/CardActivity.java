package com.soft.morales.mysmartwardrobe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.soft.morales.mysmartwardrobe.model.Garment;
import com.soft.morales.mysmartwardrobe.model.persist.APIService;
import com.soft.morales.mysmartwardrobe.model.persist.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardActivity extends AppCompatActivity {

    TextView txtName, txtCategory, txtSeason, txtPrice, txtColor, txtSize, txtBrand;
    ImageView imageView;
    Button deleteButton;

    String garmentId;

    private APIService mAPIService;

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

        deleteButton = (Button) findViewById(R.id.deleteButton);

        imageView = (ImageView) findViewById(R.id.imageView);

        Bundle mbundle = this.getIntent().getExtras();

        String URI = mbundle.getString("Foto");

        garmentId = mbundle.getString("ID");

        txtName.setText("Nombre: " + mbundle.getString("Nombre"));
        txtCategory.setText("Categoría: " + mbundle.getString("Categoria"));
        txtSeason.setText("Temporada: " + mbundle.getString("Temporada"));
        txtPrice.setText("Precio: " + mbundle.getString("Precio"));
        txtColor.setText("Color: " + mbundle.getString("Color"));
        txtSize.setText("Talla: " + mbundle.getString("Talla"));
        txtBrand.setText("Marca: " + mbundle.getString("Marca"));

        Uri myUri = Uri.parse(URI);

        Glide.with(this).load(myUri).into(imageView);

        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                AlertDialog diaBox = AskOption();
                diaBox.show();
            }
        });

    }

    public void deleteGarment(){

        mAPIService = ApiUtils.getAPIService();

        Call<Garment> call = mAPIService.deleteGarment(garmentId);

        call.enqueue(new Callback<Garment>() {
            @Override
            public void onResponse(Call<Garment> call, Response<Garment> response) {
                Toast.makeText(getApplicationContext(), "DELETED CORRECTLY", Toast.LENGTH_LONG).show();

                finish();

            }

            @Override
            public void onFailure(Call<Garment> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        deleteGarment();
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