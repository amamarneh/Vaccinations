package com.amarnehsoft.vaccinations.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.fragments.CorporationInfoFragment;
import com.amarnehsoft.vaccinations.fragments.CorporationsListFragment;
import com.amarnehsoft.vaccinations.fragments.KindergartenInfoFragment;

public class CorporationInfoActivity extends AppCompatActivity implements CorporationsListFragment.OnFragmentInteractionListener{

    public static Intent newIntent(Context context, Corporation bean){
        Intent intent = new Intent(context,CorporationInfoActivity.class);
        intent.putExtra("bean",bean);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Corporation bean = getIntent().getParcelableExtra("bean");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, CorporationInfoFragment.newInstance(bean)).commit();
    }
}
