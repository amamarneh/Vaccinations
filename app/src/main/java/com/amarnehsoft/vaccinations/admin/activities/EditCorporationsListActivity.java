package com.amarnehsoft.vaccinations.admin.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.activities.Base;
import com.amarnehsoft.vaccinations.activities.CorporationsListActivity;
import com.amarnehsoft.vaccinations.fragments.CorporationsListFragment;
import com.amarnehsoft.vaccinations.interfaces.SearchViewExpandListener;

/**
 * Created by alaam on 2/12/2018.
 */

public class EditCorporationsListActivity extends Base implements CorporationsListFragment.OnFragmentInteractionListener{
    private CorporationsListFragment mFragment;
    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,CorporationsListActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corporations_list);
        mFragment = CorporationsListFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mFragment).commit();
        setTitle(getString(R.string.corporations));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_add, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // See above
        MenuItemCompat.setOnActionExpandListener(searchItem, new SearchViewExpandListener(this));
        MenuItemCompat.setActionView(searchItem, searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (mFragment != null)
                    mFragment.onQueryTextChanged(s);
                return false;
            }
        });

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
        Intent i = AddEditCorporationActivity.newIntent(this,null);
        startActivity(i);
    }

}
