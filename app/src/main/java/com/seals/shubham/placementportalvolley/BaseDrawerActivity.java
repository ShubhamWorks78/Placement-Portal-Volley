package com.seals.shubham.placementportalvolley;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by shubham on 1/4/2017.
 */

public class BaseDrawerActivity extends Activity {

    @Override
    public void setContentView(int layoutResId){
        setContentView(R.layout.activity_drawer);
        ViewGroup viewGroup = (ViewGroup)findViewById(R.id.frameLayout);
        LayoutInflater.from(this).inflate(layoutResId,viewGroup,true);
    }
}
