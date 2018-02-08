package com.amarnehsoft.vaccinations.activities.itemDetail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.activities.Base;
import com.amarnehsoft.vaccinations.fragments.itemDetail.ItemDetailFragment;

public abstract class ItemDetailActivity<B> extends Base {
    //b : bean type

    public static final int REQ_EDIT =222;

    protected ItemDetailFragment mFragment;
    protected CollapsingToolbarLayout mAppBarLayout;

    protected B mBean;
    public void refresh(B item){
        mBean = item;
        refreshView();
        mFragment.refresh(item);
    }

    public static final String ARG_ITEM_ID = "item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBean = getIntent().getParcelableExtra(ARG_ITEM_ID);

        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabClicked();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mAppBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        if (mAppBarLayout != null) {
            mAppBarLayout.setTitle(getBarTitle());
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            //Bundle arguments = new Bundle();
            //arguments.putParcelable(ARG_ITEM_ID, (Parcelable) mBean);
            ItemDetailFragment fragment = getFragment();
            mFragment = fragment;

            //fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if(id == R.id.menu_delete){
            onDeleteMeniItemClicked(mBean);
        }
        return super.onOptionsItemSelected(item);
    }

    private void onDeleteMeniItemClicked(final B bean)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onDeleteMenuItemClicked(bean);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }
    protected void ShowSnackbar(String msg){
        View layout = findViewById(R.id.coordinatorlayout);
        Snackbar.make(layout,msg,Snackbar.LENGTH_SHORT).show();
    }
    protected abstract void onFabClicked();
    protected abstract ItemDetailFragment getFragment();
    protected abstract String getBarTitle();
    protected abstract void refreshView();
    protected abstract void onDeleteMenuItemClicked(B bean);
}
