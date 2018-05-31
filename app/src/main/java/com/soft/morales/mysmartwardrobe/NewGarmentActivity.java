package com.soft.morales.mysmartwardrobe;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.soft.morales.mysmartwardrobe.model.Garment;
import com.soft.morales.mysmartwardrobe.model.User;
import com.soft.morales.mysmartwardrobe.model.persist.APIService;
import com.soft.morales.mysmartwardrobe.model.persist.ApiUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewGarmentActivity extends AppCompatActivity {

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "My Smart Wardrobe";

    private Uri fileUri; // file url to store image/video

    private ImageView imgPreview; // img preview

    private boolean isUploading = false;

    private Button buttonSend;
    private EditText textName, textBrand, textPrice, textColor;
    private Spinner spinnerCategory, spinnerSeason, spinnerSize;

    private APIService mAPIService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_garment);

        setupToolbar();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        mAPIService = ApiUtils.getAPIService();

        imgPreview = (ImageView) findViewById(R.id.backdrop);

        buttonSend = (Button) findViewById(R.id.button_send);
        textName = (EditText) findViewById(R.id.input_name);
        textBrand = (EditText) findViewById(R.id.input_brand);
        textPrice = (EditText) findViewById(R.id.input_price);
        textColor = (EditText) findViewById(R.id.input_color);

        FloatingActionButton buttonOne = (FloatingActionButton) findViewById(R.id.buttonAdd);
        buttonOne.setOnClickListener(new FloatingActionButton.OnClickListener() {
            public void onClick(View v) {
                requestStoragePermission();
            }
        });

        setupSpinners();
        testingPost();

    }

    private void testingPost() {

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate() && !isUploading) {
                    isUploading = true;
                    String brand = textBrand.getText().toString().trim();
                    createBrand(brand);
                } else {
                    isUploading = false;
                }
            }
        });

    }

    public void createBrand(String name) {

        mAPIService.createBrand(name).enqueue(new Callback<Garment>() {
            @Override
            public void onResponse(Call<Garment> call, Response<Garment> response) {

                if (response.isSuccessful()) {
                    String name = textName.getText().toString().trim();
                    String photo = fileUri.toString();
                    String category = spinnerCategory.getSelectedItem().toString();
                    String season = spinnerSeason.getSelectedItem().toString();
                    String price = textPrice.getText().toString().trim();
                    String color = textColor.getText().toString().trim();
                    String size = spinnerSize.getSelectedItem().toString();
                    String brand = textBrand.getText().toString().trim();

                    sendPost(name, photo, category, season, price, color, size, brand);
                }
            }

            @Override
            public void onFailure(Call<Garment> call, Throwable t) {
                String name = textName.getText().toString().trim();
                String photo = fileUri.toString();
                String category = spinnerCategory.getSelectedItem().toString();
                String season = spinnerSeason.getSelectedItem().toString();
                String price = textPrice.getText().toString().trim();
                String color = textColor.getText().toString().trim();
                String size = spinnerSize.getSelectedItem().toString();
                String brand = textBrand.getText().toString().trim();

                sendPost(name, photo, category, season, price, color, size, brand);

            }
        });

    }

    public void sendPost(String name, String photo, String category, String season, String price,
                         String color, String size, String brand) {

        Gson gson = new Gson();
        SharedPreferences shared = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        User user = gson.fromJson(shared.getString("user", ""), User.class);

        mAPIService.savePost(name, photo, category, season, price, user.getEmail(), color, size, brand).enqueue(new Callback<Garment>() {
            @Override
            public void onResponse(Call<Garment> call, Response<Garment> response) {

                if (response.isSuccessful()) {
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Garment> call, Throwable t) {
                Log.d("NOOK: ", "Unable to submit post to API.");
                isUploading = false;
            }
        });

    }


    private void setupToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getTitle());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupSpinners() {

        spinnerCategory = (Spinner) findViewById(R.id.spinner_category);

        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };

        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCategory.add("Camiseta");
        adapterCategory.add("Pantalón");
        adapterCategory.add("Jersey");
        adapterCategory.add("Chaqueta");
        adapterCategory.add("Calzado");
        adapterCategory.add("Accesorio");
        adapterCategory.add("Categoria");

        spinnerCategory.setAdapter(adapterCategory);
        spinnerCategory.setSelection(adapterCategory.getCount()); //display hint

        spinnerSeason = (Spinner) findViewById(R.id.spinner_season);

        ArrayAdapter<String> adapterSeason = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };

        adapterSeason.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSeason.add("Invierno");
        adapterSeason.add("Primavera");
        adapterSeason.add("Verano");
        adapterSeason.add("Otoño");
        adapterSeason.add("Temporada");

        spinnerSeason.setAdapter(adapterSeason);
        spinnerSeason.setSelection(adapterSeason.getCount()); //display hint

        spinnerSize = (Spinner) findViewById(R.id.spinner_size);

        ArrayAdapter<String> adapterSize = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };

        adapterSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterSize.add("");

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (spinnerCategory.getSelectedItem().toString().equalsIgnoreCase("Calzado")) {
                    adapterSize.clear();
                    adapterSize.add("34");
                    adapterSize.add("35");
                    adapterSize.add("36");
                    adapterSize.add("37");
                    adapterSize.add("38");
                    adapterSize.add("39");
                    adapterSize.add("40");
                    adapterSize.add("41");
                    adapterSize.add("42");
                    adapterSize.add("43");
                    adapterSize.add("44");
                    adapterSize.add("45");
                    adapterSize.add("46");
                    adapterSize.add("47");
                    adapterSize.add("48");
                    adapterSize.add("49");
                    adapterSize.add("50");
                    adapterSize.add("51");
                    adapterSize.add("52");
                    adapterSize.add("53");
                    adapterSize.add("Talla");
                } else if (spinnerCategory.getSelectedItem().toString().equalsIgnoreCase("")) {
                    adapterSize.add("Talla");
                } else {
                    adapterSize.clear();
                    adapterSize.add("XXL");
                    adapterSize.add("XL");
                    adapterSize.add("L");
                    adapterSize.add("M");
                    adapterSize.add("S");
                    adapterSize.add("Talla");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinnerSize.setAdapter(adapterSize);
        spinnerSize.setSelection(adapterSize.getCount()); //display hint

    }


    /*
     * Capturing Camera Image will lauch camera app requrest image capture
     */
    private void captureImage() throws IOException {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

    }

    /**
     * Receiving activity result method will be called after closing the camera
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // successfully captured the image
                // display it in image view
                try {
                    previewCapturedImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /*
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() throws IOException {
        try {

            imgPreview.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;


            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            imgPreview.setImageBitmap(rotateImageIfRequired(this, bitmap, fileUri));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rotate an image if required.
     *
     * @param img           The image bitmap
     * @param selectedImage Image URI
     * @return The resulted Bitmap after manipulation
     */
    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }


    /**
     * Here we store the file url as it will be null after returning from camera
     * app
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on scren orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    /*
     * Here we restore the fileUri again
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    /**
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    /**
     * Requesting multiple permissions (storage and location) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            try {
                                captureImage();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewGarmentActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    /**
     * private EditText textName, textBrand, textPrice, textColor;
     * private Spinner spinnerCategory, spinnerSeason, spinnerSize;
     *
     * @return
     */
    public boolean validate() {
        boolean valid = true;

        String name = textName.getText().toString();
        String brand = textBrand.getText().toString();
        String price = textPrice.getText().toString();
        String color = textColor.getText().toString();

        if (name.isEmpty()) {
            textName.setError("enter a valid name");
            valid = false;
        } else {
            textName.setError(null);
        }
        if (brand.isEmpty()) {
            textBrand.setError("enter a valid brand");
            valid = false;
        } else {
            textBrand.setError(null);
        }
        if (price.isEmpty() || !TextUtils.isDigitsOnly(price)) {
            textPrice.setError("enter a valid price");
            valid = false;
        } else {
            textPrice.setError(null);
        }
        if (color.isEmpty()) {
            textColor.setError("enter a valid color");
            valid = false;
        } else {
            textColor.setError(null);
        }

        if (fileUri == null) {
            Toast.makeText(this, "Take a photo", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

}


