package com.example.nppproject;

import android.content.Intent;
import android.os.Bundle;

import com.example.nppproject.Fragment.HomeFragment;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.MenuItem;

import com.example.nppproject.Fragment.SaveFragment;
import com.example.nppproject.Fragment.VideoHomeFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeFragment homeFragment = HomeFragment.newInstance(Globals.URL_HOME);
        fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();
        Intent intent = new Intent(getBaseContext(), ServiceNotification.class);
        startService(intent);

    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
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
            videoHomeFragment=VideoHomeFragment.newInstance(Globals.URL_HOTVIDEO);
            fragmentManager.beginTransaction().replace(R.id.container,videoHomeFragment).commit();
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
        } else if (id == R.id.nav_send) {
            AllPopBackStack();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
    }
}
