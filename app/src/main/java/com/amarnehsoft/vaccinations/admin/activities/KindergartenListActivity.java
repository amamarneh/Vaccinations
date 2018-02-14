package com.amarnehsoft.vaccinations.admin.activities;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.activities.abstractActivities.EmptyActivity;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.fragments.KindergartensListFragment;

/**
 * Created by alaam on 1/26/2018.
 */

public class KindergartenListActivity extends EmptyActivity<Kindergarten> {
    @Override
    protected String getBarTitle() {
        return getString(R.string.kindergartens);
    }

    @Override
    public Fragment getFragment() {
        return  KindergartensListFragment.Companion.newInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            add();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void add() {
        startActivity(AddEditKindergartenActivity.newIntent(this,null));
    }

}
