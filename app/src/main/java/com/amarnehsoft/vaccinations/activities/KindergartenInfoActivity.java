package com.amarnehsoft.vaccinations.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.fragments.KindergartenInfoFragment;

public class KindergartenInfoActivity extends Base implements KindergartenInfoFragment.OnFragmentInteractionListener{

    public static Intent newIntent(Context context, Kindergarten kindergarten){
        Intent intent = new Intent(context,KindergartenInfoActivity.class);
        intent.putExtra("bean",kindergarten);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kindergarten_info);
        Kindergarten kindergarten = getIntent().getParcelableExtra("bean");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, KindergartenInfoFragment.newInstance(kindergarten)).commit();


    }
}
