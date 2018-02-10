package com.amarnehsoft.vaccinations.admin.activities;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.activities.abstractActivities.EmptyActivity;
import com.amarnehsoft.vaccinations.admin.fragments.CatListFragment;
import com.amarnehsoft.vaccinations.beans.Cat;

/**
 * Created by alaam on 2/10/2018.
 */

public class CatListActivity extends EmptyActivity<Cat> {
    @Override
    protected String getBarTitle() {
        return "Cats";
    }


    @Override
    public Fragment getFragment() {
        return  CatListFragment.newInstance(getIntent().getIntExtra("mode",CatListFragment.MODE_NORMAL));
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
        startActivity(AddEditCatActivity.newIntent(this,null));
    }
}
