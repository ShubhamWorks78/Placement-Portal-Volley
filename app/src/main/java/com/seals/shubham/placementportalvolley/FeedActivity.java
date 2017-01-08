package com.seals.shubham.placementportalvolley;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class FeedActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_feed);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(R.string.str_feed);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.feed_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"SnackBar used",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                Intent intent = new Intent(FeedActivity.this,UploadActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        final NavigationView mNavigationView = (NavigationView)findViewById(R.id.vNavigation);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_log_out:
                        logout();
                        break;
                    case R.id.menu_settings:
                        break;
                    case R.id.menu_stats:
                        Intent intenSt = new Intent(FeedActivity.this, PlacementStats.class);
                        startActivity(intenSt);
                        finish();
                        break;
                    case R.id.menu_about:
                        Intent intentAb = new Intent(FeedActivity.this,About_Description.class);
                        startActivity(intentAb);
                        finish();
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawerLayout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)menu.findItem(R.id.search_btn).getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    public void logout(){
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle(R.string.logout_alert_title);
        ab.setMessage(R.string.alert_question_logout);
        ab.setPositiveButton(R.string.alert_postive_logout, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(FeedActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ab.setNegativeButton(R.string.alert_negative_logout, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onBackPressed();
            }
        });
    }
}

