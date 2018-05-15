package com.soft.morales.mysmartwardrobe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {

    TextView txtView;
//    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitxa_layout);

        txtView = (TextView) findViewById(R.id.tvinfo);
        //    imageView = (ImageView)findViewById(R.id.imageView);

        Bundle mbundle = this.getIntent().getExtras();
        //    String fileName = mbundle.getString("imagename");
        txtView.setText(mbundle.getString("description"));

        /*
        File directoryInternalStorage =
                this.getBaseContext().getApplicationContext().getFilesDir();
        File file =
                new File(directoryInternalStorage.getAbsolutePath() + "/" + fileName);

        if(file.exists()) {
            Uri uri = Uri.fromFile(file);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.getLayoutParams().width = 800;
            imageView.setImageURI(uri);
        }*/

    }
}