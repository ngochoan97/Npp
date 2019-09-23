package com.example.nppproject;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.nppproject.Fragment.HomeFragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import com.example.nppproject.Fragment.SaveFragment;
import com.example.nppproject.Fragment.VideoHomeFragment;
import com.example.nppproject.Fragment.WeatherFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvLogLat, tvCity;
    LocationManager locationManager;
    private double longit, latit;
    boolean runHumi = true;
    Handler handlerHumi;
    NavigationView navigationView;
    View header_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.nav_view);
        header_view = navigationView.getHeaderView(0);
        tvCity = header_view.findViewById(R.id.tvCity);
        tvLogLat = header_view.findViewById(R.id.tvLogLat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        new getJson().execute();

        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences("isNightMode", Context.MODE_PRIVATE);
        isNightMode = sharedPreferences.getBoolean("isNightModeVar", false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_HOME);
        fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
        Intent intent = new Intent(getBaseContext(), ServiceNotification.class);
        startService(intent);
//        new getJson().execute();
        handlerHumi = new Handler();
        humin();
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
popAllBackStack();
FragmentManager fragmentManager=getSupportFragmentManager();
HomeFragment homeFragment=HomeFragment.newInstance(Globals.URL_HOME);
fragmentManager.beginTransaction().replace(R.id.container,homeFragment,homeFragment.getTag()).commit();
new AlertDialog.Builder(this).setIcon(R.drawable.ic_exit).setTitle("Thoát ?").setMessage("Bạn có muốn thoát ").setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
finish();
    }
})
.setNegativeButton("Quay lại",null).show();
        } else {
            getSupportFragmentManager().popBackStack();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            SaveFragment saveFragment = SaveFragment.newInstance();
            fragmentManager.beginTransaction().replace(R.id.container, saveFragment).addToBackStack("stack").commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Log.d("menu", "onNavigationItemSelected: 1111 ");
            // Handle the camera action
//            AllPopBackStack();
            HomeFragment homeFragment;

            homeFragment = HomeFragment.newInstance(Globals.URL_HOME);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_home);

        } else if (id == R.id.nav_news) {

            HomeFragment homeFragment;
            homeFragment = HomeFragment.newInstance(Globals.URL_NEWS);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_news);
        } else if (id == R.id.nav_video) {

            VideoHomeFragment videoHomeFragment;
            videoHomeFragment = VideoHomeFragment.newInstance(Globals.URL_HOTVIDEO);
            fragmentManager.beginTransaction().replace(R.id.container, videoHomeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_video);

        } else if (id == R.id.nav_world) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_WORLD);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_world);

        } else if (id == R.id.nav_business) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_BUSINESS);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_business);

        } else if (id == R.id.nav_startup) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_STARTUP);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_startup);
        } else if (id == R.id.nav_entertainment) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_ENTERTAINMENT);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_entertainment);

        } else if (id == R.id.nav_sport) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_SPORT);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_sport);
        } else if (id == R.id.nav_law) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_LAW);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_law);
        } else if (id == R.id.nav_education) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_EDUCATION);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
        } else if (id == R.id.nav_health) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_HEALTH);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_health);
        } else if (id == R.id.nav_life) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_LIFE);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_life);
        } else if (id == R.id.nav_travel) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_TRAVEL);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_travel);
        } else if (id == R.id.nav_science) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_SCIENCE);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_science);
        } else if (id == R.id.nav_digitizing) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_DIGITALIZE);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_digitalize);
        } else if (id == R.id.nav_car) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_CAR);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_car);
        } else if (id == R.id.nav_comment) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_COMMENT);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_comment);
        } else if (id == R.id.nav_talk) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_TALK);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_talk);
        } else if (id == R.id.nav_laugh) {

            HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_LAUGH);
            fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
            getSupportActionBar().setTitle(R.string.menu_laugh);

        } else if (id == R.id.NightMode) {
            if (isNightMode == true) {
                SharedPreferences sharedPreferences = getSharedPreferences("isNightMode", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isNightModeVar", false).commit();
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                SharedPreferences sharedPreferences = getSharedPreferences("isNightMode", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isNightModeVar", true).commit();
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }

    } else if (id == R.id.nav_wearther) {
            WeatherFragment weatherFragment =new WeatherFragment();

        fragmentManager.beginTransaction().replace(R.id.container, weatherFragment).addToBackStack("stack").commit();
        getSupportActionBar().setTitle(R.string.menu_weather);

    }
        else if (id == R.id.nav_send) {
            AllPopBackStack();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    boolean isNightMode;

    public void AllPopBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                fragmentManager.popBackStack();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GPS gps = new GPS(this);
        if (gps.canGetLocation()) {
            gps.getLatitude();
            gps.getLongitude();
     //       Toast.makeText(this, "" + gps.getLongitude(), Toast.LENGTH_SHORT).show();
            Log.d("TAG123", "onCreate: " + gps.getLongitude());
//            tvLogLat.setText(gps.getLocation() + " " + gps.getLatitude());
            longit = gps.getLongitude();
            latit = gps.getLatitude();

        }

    }

    private void loc_func(Location gps) {
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = null;
            addresses = geocoder.getFromLocation(latit, longit, 1);

            String city = addresses.get(0).getLocality();
            tvCity.setText(city);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    String json;
    int TempC;

    public void doGetData() {
        String JsonURL = Globals.URL_LINKWT + "hanoi" + Globals.URL_KEYAPI;
        try {
            URL url = new URL(JsonURL);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            int bytechar;
            while ((bytechar = inputStream.read()) != -1) {
                json += (char) bytechar;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "doGetData: " + json);
    }

    public class getJson extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            doGetData();
            getMain();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TempC = Integer.parseInt(Math.round(((Double.parseDouble(main.getTemp()) - 273.15) * 10) / 10) + "");
            tvCity.setText(TempC+"°C");
            Log.d("lol123", "onPostExecute: " + main.getHumidity());
        }
    }

    MainWT main;

    public void getMain() {
        main = new MainWT();
        try {
            String jsonNew = json.substring(4);
            JSONObject jsonObject = new JSONObject(jsonNew);

            JSONObject mainEntity = jsonObject.getJSONObject("main");
            main.setTemp(mainEntity.getString("temp"));
            main.setPressure(mainEntity.getString("pressure"));
            main.setHumidity(mainEntity.getString("humidity"));
            main.setTemp_max(mainEntity.getString("temp_max"));
            main.setTemp_min(mainEntity.getString("temp_min"));
            Log.d(TAG, ": getMain : " + main.getHumidity());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void humin() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (runHumi) {
                    try {
                        Thread.sleep(1000);
                        handlerHumi.post(new Runnable() {
                            @Override
                            public void run() {

                                Calendar calendar = Calendar.getInstance();
                                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                int minute = calendar.get(Calendar.MINUTE);
                                tvLogLat.setText(hour + " : " + minute);
                                // new getJson().execute();
//                                tvCity.setText(main.getTemp());
//                                Log.d(TAG, "run: "+main.getHumidity());
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public void popAllBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                fragmentManager.popBackStack();
            }
        }
    }
    private static final String TAG = "MainActivity123";
}
