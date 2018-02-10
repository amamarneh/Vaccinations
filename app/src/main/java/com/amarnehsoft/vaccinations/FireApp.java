package com.amarnehsoft.vaccinations;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by alaam on 2/10/2018.
 */

public class FireApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true); // enable offline
        FirebaseDatabase.getInstance().getReference().keepSynced(true);
    }
}
