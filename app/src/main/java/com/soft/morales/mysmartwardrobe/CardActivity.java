package com.soft.morales.mysmartwardrobe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class CardActivity extends AppCompatActivity {

    TextView txtName, txtCategory, txtSeason, txtPrice, txtColor, txtSize, txtBrand;
    ImageView imageView;
    Button deleteButton;

    int pos;
    String garmentId;

    private APIService mAPIService;

    String date;
    private User mUser;

    private boolean isDeleting;

    List<Look> looks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.garment_details_layout);

        Gson gson = new Gson();
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mUser = gson.fromJson(sharedPref.getString("user", ""), User.class);

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
        pos = mbundle.getInt("pos");

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

    public void deleteGarment() {

        mAPIService = ApiUtils.getAPIService();

        Call<Garment> call = mAPIService.deleteGarment(garmentId);

        call.enqueue(new Callback<Garment>() {
            @Override
            public void onResponse(Call<Garment> call, Response<Garment> response) {
                Toast.makeText(getApplicationContext(), "DELETED CORRECTLY", Toast.LENGTH_LONG).show();

                Bundle b = new Bundle();
                Intent i = new Intent();

                if (!isDeleting) {
                    getLooks();
                    isDeleting = true;
                } else {
                    isDeleting = false;
                }

                for (int j = 0; j < looks.size(); j++) {

                    if (looks.get(j).getGarmentsIds().size() == 3) {

                        int id1 = looks.get(j).getGarmentsIds().get(0);
                        int id2 = looks.get(j).getGarmentsIds().get(1);
                        int id3 = looks.get(j).getGarmentsIds().get(2);

                        if (garmentId.equalsIgnoreCase(String.valueOf(id1))
                                || garmentId.equalsIgnoreCase(String.valueOf(id2))
                                || garmentId.equalsIgnoreCase(String.valueOf(id3))) {

                            deleteLook(looks.get(j).getId());

                        }

                    }

                }

                b.putInt("pos", pos);
                i.putExtras(b);

                setResult(RESULT_OK, i);

                finish();

            }

            @Override
            public void onFailure(Call<Garment> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private AlertDialog AskOption() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        getLooks();
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

    public void getLooks() {

        mAPIService = ApiUtils.getAPIService();

        HashMap query = new HashMap();
        query.put("username", mUser.getEmail());

        Log.d("mUSER", String.valueOf(mUser.getEmail()));

        Call<List<Look>> call = mAPIService.getLooks(query);

        call.enqueue(new Callback<List<Look>>() {
            @Override
            public void onResponse(Call<List<Look>> call, Response<List<Look>> response) {

                if (response.isSuccessful()) {

                    looks = response.body();

                    for (int i = 0; i < looks.size(); i++) {

                        Log.d("LOOK", String.valueOf(looks.get(i).getId()));

                    }

                    deleteGarment();

                }

            }

            @Override
            public void onFailure(Call<List<Look>> call, Throwable t) {
                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    public void deleteLook(String lookId) {

        mAPIService = ApiUtils.getAPIService();

        Call<Look> call = mAPIService.deleteLook(lookId);

        call.enqueue(new Callback<Look>() {
            @Override
            public void onResponse(Call<Look> call, Response<Look> response) {
                Toast.makeText(getApplicationContext(), "DELETED CORRECTLY", Toast.LENGTH_LONG).show();

                finish();

            }

            @Override
            public void onFailure(Call<Look> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


}