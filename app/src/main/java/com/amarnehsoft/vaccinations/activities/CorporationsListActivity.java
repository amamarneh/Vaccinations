package com.amarnehsoft.vaccinations.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.fragments.CorporationsListFragment;

public class CorporationsListActivity extends AppCompatActivity implements CorporationsListFragment.OnFragmentInteractionListener{

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,CorporationsListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corporations_list);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,CorporationsListFragment.newInstance()).commit();
    }
}
