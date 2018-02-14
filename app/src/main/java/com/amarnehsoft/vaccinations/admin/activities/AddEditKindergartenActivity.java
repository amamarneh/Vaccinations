package com.amarnehsoft.vaccinations.admin.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.activities.abstractActivities.EmptyActivity;
import com.amarnehsoft.vaccinations.admin.fragments.KindergartenEditFragment;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.database.db2.DBCorporation;
import com.amarnehsoft.vaccinations.database.db2.DBKindergarten;
import com.amarnehsoft.vaccinations.database.firebase.FBKindergarten;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.UUID;

public class AddEditKindergartenActivity extends EmptyActivity<Kindergarten> {

    @Override
    protected String getBarTitle() {
        return getString(R.string.kindergartens);
    }

    @Override
    public Fragment getFragment() {
        return KindergartenEditFragment.newInstance(mBean);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            save();
            return true;
        }
        if (id == R.id.action_delete) {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.areYouSure))
                    .setContentText(getString(R.string.delete))
                    .setConfirmText(getString(R.string.yes))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            delete();

                        }
                    })
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void delete() {
        if(mBean != null){
//            FBKindergarten fbKindergarten = new FBKindergarten(AddEditKindergartenActivity.this);
//            fbKindergarten.delete(mBean.getCode());
            DBKindergarten.getInstance(this).deleteBean(mBean.getCode());
            finish();
        }
    }

    private void save() {
        Uri uri = ((KindergartenEditFragment)mFragment).getUri();
        if(uri == null){
            Kindergarten kindergarten = ((KindergartenEditFragment)mFragment).getKindergarten();
//            FBKindergarten fbKindergarten = new FBKindergarten(AddEditKindergartenActivity.this);
//            if(fbKindergarten.save(kindergarten,kindergarten.getCode())){
            DBKindergarten.getInstance(this).saveBean(kindergarten);

                Toast.makeText(AddEditKindergartenActivity.this, getString(R.string.done), Toast.LENGTH_SHORT).show();
                finish();
//            }
//            else
//                Toast.makeText(AddEditKindergartenActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }else{
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage(getString(R.string.uploadImage));
            dialog.show();
            StorageReference mReference = FirebaseStorage.getInstance().getReference().child("images").child(UUID.randomUUID().toString());
            mReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    dialog.dismiss();
                    if(task.isSuccessful()){

                        Kindergarten kindergarten = ((KindergartenEditFragment)mFragment).getKindergarten();
                        kindergarten.setImgUrl(task.getResult().getDownloadUrl().toString());
//                        FBKindergarten fbKindergarten = new FBKindergarten(AddEditKindergartenActivity.this);
//                        if(fbKindergarten.save(kindergarten,kindergarten.getCode())){
                        DBKindergarten.getInstance(AddEditKindergartenActivity.this).saveBean(kindergarten);

                            Toast.makeText(AddEditKindergartenActivity.this, getString(R.string.done), Toast.LENGTH_SHORT).show();
                            finish();
//                        }
//                        else
//                            Toast.makeText(AddEditKindergartenActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(AddEditKindergartenActivity.this, getString(R.string.err), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public static Intent newIntent(Context context, Kindergarten kindergarten){
        Intent i = new Intent(context,AddEditKindergartenActivity.class);
        i.putExtra(ARG_BEAN,kindergarten);
        return i;
    }
}
