package com.amarnehsoft.vaccinations.activities;

import android.support.v7.app.AppCompatActivity;

import com.amarnehsoft.vaccinations.controllers.SPController;

/**
 * Created by jcc on 2/6/2018.
 */

public class Base extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
//        setTitle(SPController.getInstance(this).getTitle());
    }
}
