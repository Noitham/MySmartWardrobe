package com.soft.morales.mysmartwardrobe;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.soft.morales.mysmartwardrobe.fragments.CalendarFragment;
import com.soft.morales.mysmartwardrobe.fragments.MyClosetFragment;
import com.soft.morales.mysmartwardrobe.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    TextView nameDrawer, emailDrawer;

    FragmentManager fragmentManager;
    NavigationView navigationView;
    FrameLayout frameLayout;
    String name;
    String email;
    String foto1;
    String foto2;
    String foto3;
    int id;
    int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();

        if (getIntent().getExtras() != null) {

            value = getIntent().getExtras().getInt("ok");
            id = getIntent().getExtras().getInt("id");
            name = getIntent().getExtras().getString("name");
            email = getIntent().getExtras().getString("email");
            foto1=getIntent().getExtras().getString("foto1");
            foto2=getIntent().getExtras().getString("foto2");
            foto3=getIntent().getExtras().getString("foto3");

            Log.d("NICE: ", "BUNDKE NOT EMPTY");
            Log.d("NICE: ", String.valueOf(name));

            nameDrawer.setText(name);
            emailDrawer.setText(email);

        }else{
            Log.d("ERROR: ", "BUNDKE EMPTY");
        }

        fragmentManager = getSupportFragmentManager();



        if (savedInstanceState == null) showHome();

        if (value == 1) {
            boolean specialToolbarBehaviour;
            Class fragmentClass;
            fragmentClass = MyClosetFragment.class;
            Bundle bundle = new Bundle();
            bundle.putInt("mode", 1);
            bundle.putString("foto1", foto1);
            bundle.putString("foto2", foto2);
            bundle.putString("foto3", foto3);

            if(foto1!=null){
                Log.d("Happy","Happy");
            }else{
                Log.d("SOC EN BOBOBO","    .");
            }


            if(foto2!=null){
                Log.d("Happy","Happy");
            }else{
                Log.d("SOC EN BOBOBO","    .");
            }

            specialToolbarBehaviour = true;

            Fragment fragment = null;

            try {
                fragment = (Fragment) fragmentClass.newInstance();
                fragment.setArguments(bundle);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            setToolbarElevation(specialToolbarBehaviour);
            setTitle("Gallery");
            drawerLayout.closeDrawer(Gravity.START, false);

        }

    }

    @Override
    public void onResume(){
        super.onResume();

        if (getIntent().getExtras() != null) {

            id = getIntent().getExtras().getInt("id");
            name = getIntent().getExtras().getString("name");
            email = getIntent().getExtras().getString("email");

            nameDrawer.setText(name);
            emailDrawer.setText(email);
            Log.d("NICE: ", "BUNDKE NOT EMPTY");

        }else{
            Log.d("ERROR: ", "BUNDKE EMPTY");
        }

    }

    @Override
    public void onStart(){
        super.onStart();

        if (this.getIntent().getExtras() != null) {
            Log.d("NICE: ", "BUNDKE NOT EMPTY");

            id = getIntent().getExtras().getInt("id");
            name = getIntent().getExtras().getString("name");
            email = getIntent().getExtras().getString("email");
            Log.d("NICE: ", "BUNDKE NOT EMPTY");

            nameDrawer.setText(name);
            emailDrawer.setText(email);

        }else{
            Log.d("ERROR: ", "BUNDKE EMPTY");
        }

    }

    private void setupView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        nameDrawer = (TextView) headerView.findViewById(R.id.name);
        emailDrawer = (TextView) headerView.findViewById(R.id.email);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
            }
        });

    }

    private void showHome() {
        selectDrawerItem(navigationView.getMenu().getItem(0));
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void selectDrawerItem(MenuItem menuItem) {
        boolean specialToolbarBehaviour = false;
        Class fragmentClass;

        switch (menuItem.getItemId()) {
            case R.id.drawer_gallery:
                fragmentClass = MyClosetFragment.class;
                specialToolbarBehaviour = true;
                break;
            case R.id.add_new_garment:
                Intent intent = new Intent(this, NewGarmentActivity.class);
                startActivity(intent);
                return;
            case R.id.drawer_calendar:
                fragmentClass = CalendarFragment.class;
                break;
            case R.id.nav_manage:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.add_new_look:
                Intent intent2 = new Intent(this, NewLookActivity.class);
                startActivity(intent2);
                return;
            default:
                fragmentClass = MyClosetFragment.class;
                break;
        }

        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setToolbarElevation(specialToolbarBehaviour);
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setToolbarElevation(boolean specialToolbarBehaviour) {
        if (specialToolbarBehaviour) {
            toolbar.setElevation(0.0f);
            frameLayout.setElevation(getResources().getDimension(R.dimen.elevation_toolbar));
        } else {
            toolbar.setElevation(getResources().getDimension(R.dimen.elevation_toolbar));
            frameLayout.setElevation(0.0f);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        drawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void gotoLogin(View view) {

        if(nameDrawer.getText().equals("Unregistered user")){
            Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(mainIntent);
        }else{

        }
    }
}