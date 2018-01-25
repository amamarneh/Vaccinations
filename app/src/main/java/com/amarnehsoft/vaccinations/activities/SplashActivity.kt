package com.amarnehsoft.vaccinations.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.amarnehsoft.vaccinations.admin.DashboardActivity
import com.amarnehsoft.vaccinations.constants.VersionConstants
import com.amarnehsoft.vaccinations.database.firebase.FBVacinations

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (VersionConstants.CURRENT_VERSION == VersionConstants.VERSION_USER)
            startActivity(Intent(this, HomeActivity::class.java))
        else
            startActivity(Intent(this,DashboardActivity::class.java))

        finish()
    }
}
