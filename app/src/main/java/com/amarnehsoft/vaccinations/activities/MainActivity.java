package com.amarnehsoft.vaccinations.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.admin.DashboardActivity;
import com.amarnehsoft.vaccinations.controllers.DBController;

import static com.amarnehsoft.vaccinations.constants.VersionConstants.CURRENT_VERSION;
import static com.amarnehsoft.vaccinations.constants.VersionConstants.VERSION_ADMIN;
import static com.amarnehsoft.vaccinations.constants.VersionConstants.VERSION_USER;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openUser(View view) {
        CURRENT_VERSION  = VERSION_USER;
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void openAdmin(View view) {
        CURRENT_VERSION  = VERSION_ADMIN;
        startActivity(new Intent(this, DashboardActivity.class));

    }
}
