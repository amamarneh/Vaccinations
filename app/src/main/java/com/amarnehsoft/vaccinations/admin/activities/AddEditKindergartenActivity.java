package com.amarnehsoft.vaccinations.admin.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Kindergarten;

public class AddEditKindergartenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_kindergarten);
    }
    public static Intent newIntent(Context context, Kindergarten kindergarten){
        Intent i = new Intent(context,AddEditKindergartenActivity.class);
        i.putExtra("bean",kindergarten);
        return i;
    }
}
