package com.amarnehsoft.vaccinations.admin.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.activities.abstractActivities.EmptyActivity;
import com.amarnehsoft.vaccinations.admin.fragments.AddEditCatFragment;
import com.amarnehsoft.vaccinations.beans.Cat;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.database.firebase.FBCat;
import com.google.firebase.database.DatabaseReference;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by alaam on 2/9/2018.
 */

public class AddEditCatActivity extends EmptyActivity<Cat>{
    @Override
    protected String getBarTitle() {
        return "Cat";
    }

    @Override
    public Fragment getFragment() {
        return AddEditCatFragment.newInstance(mBean);
    }
    public static Intent newIntent(Context context, Cat cat){
        Intent i = new Intent(context,AddEditCatActivity.class);
        i.putExtra(ARG_BEAN,cat);
        return i;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(mBean == null)
            return super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            delete();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void delete() {
        if(mBean != null){

            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Delete this Category?")
                    .setConfirmText("Yes, delete it")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            DatabaseReference mDataCat = FBCat.getDataRef().child(mBean.getCode());
                            mDataCat.removeValue();
                            Toast.makeText(AddEditCatActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    })
                    .show();


        }
    }

}
