package com.amarnehsoft.vaccinations.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.amarnehsoft.vaccinations.Dummy
import com.amarnehsoft.vaccinations.admin.DashboardActivity
import com.amarnehsoft.vaccinations.beans.Cat
import com.amarnehsoft.vaccinations.constants.VersionConstants
import com.amarnehsoft.vaccinations.controllers.DBController
import com.amarnehsoft.vaccinations.controllers.LanguageController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashActivity : AppCompatActivity() {
    val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LanguageController.arabic(this)

//        if (VersionConstants.CURRENT_VERSION == VersionConstants.VERSION_USER)
//            startActivity(Intent(this, HomeActivity::class.java))
//        else
//            startActivity(Intent(this,DashboardActivity::class.java))


            CheckPermissions()
    }

    private fun CheckPermissions() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                Log.d("Amarneh", "P true")
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)

            } else {
                Log.d("Amarneh", "P false")
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else{
            DBController().check(this, DBController.DBListener {

                startActivity(Intent(this, MainActivity::class.java))
                finish()

            })
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                CheckPermissions()
                } else {
                    finish()
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }
}
