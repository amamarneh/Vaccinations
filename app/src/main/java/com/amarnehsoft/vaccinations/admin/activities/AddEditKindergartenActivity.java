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
import com.amarnehsoft.vaccinations.database.firebase.FBKindergarten;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
        Uri uri = ((KindergartenEditFragment)mFragment).getUri();
        if(uri == null){
            Kindergarten kindergarten = ((KindergartenEditFragment)mFragment).getKindergarten();
            FBKindergarten fbKindergarten = new FBKindergarten(AddEditKindergartenActivity.this);
            if(fbKindergarten.save(kindergarten,kindergarten.getCode())){
                Toast.makeText(AddEditKindergartenActivity.this, "Done", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
                Toast.makeText(AddEditKindergartenActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }else{
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage("Uploading image");
            dialog.show();
            StorageReference mReference = FirebaseStorage.getInstance().getReference().child("images");
            mReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    dialog.dismiss();
                    if(task.isSuccessful()){

                        Kindergarten kindergarten = ((KindergartenEditFragment)mFragment).getKindergarten();
                        kindergarten.setImgUrl(task.getResult().getDownloadUrl().toString());
                        FBKindergarten fbKindergarten = new FBKindergarten(AddEditKindergartenActivity.this);
                        if(fbKindergarten.save(kindergarten,kindergarten.getCode())){
                            Toast.makeText(AddEditKindergartenActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                            Toast.makeText(AddEditKindergartenActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(AddEditKindergartenActivity.this, "Error| couldn't upload image", Toast.LENGTH_SHORT).show();
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
