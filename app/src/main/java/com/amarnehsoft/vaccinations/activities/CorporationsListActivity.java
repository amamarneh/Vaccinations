package com.amarnehsoft.vaccinations.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.fragments.CorporationsListFragment;
import com.amarnehsoft.vaccinations.interfaces.SearchViewExpandListener;

public class CorporationsListActivity extends Base implements CorporationsListFragment.OnFragmentInteractionListener{
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
        inflater.inflate(R.menu.search_menu, menu);

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
}
