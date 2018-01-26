package com.amarnehsoft.vaccinations.admin.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.activities.abstractActivities.EmptyActivity;
import com.amarnehsoft.vaccinations.admin.fragments.KindergartenEditFragment;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.database.firebase.FBKindergarten;

public class AddEditKindergartenActivity extends EmptyActivity<Kindergarten> {

    @Override
    protected String getBarTitle() {
        return "Kindergarten";
    }

    @Override
    public Fragment getFragment() {
        return KindergartenEditFragment.newInstance(mBean);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        Kindergarten kindergarten = ((KindergartenEditFragment)mFragment).getKindergarten();
        FBKindergarten fbKindergarten = new FBKindergarten(this);
        fbKindergarten.save(kindergarten,kindergarten.getCode());
    }

    public static Intent newIntent(Context context, Kindergarten kindergarten){
        Intent i = new Intent(context,AddEditKindergartenActivity.class);
        i.putExtra(ARG_BEAN,kindergarten);
        return i;
    }
}
