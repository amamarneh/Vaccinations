package com.amarnehsoft.vaccinations.admin.activities;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.activities.CorporationInfoActivity;
import com.amarnehsoft.vaccinations.admin.fragments.AddEditCatFragment;
import com.amarnehsoft.vaccinations.admin.fragments.AddEditCorporationFragment;
import com.amarnehsoft.vaccinations.admin.fragments.CatListFragment;
import com.amarnehsoft.vaccinations.admin.fragments.KindergartenEditFragment;
import com.amarnehsoft.vaccinations.beans.Cat;
import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.database.firebase.FBCorporation;
import com.amarnehsoft.vaccinations.database.firebase.FBKindergarten;
import com.amarnehsoft.vaccinations.fragments.itemDetail.CorporationDetailFragment;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddEditCorporationActivity extends AppCompatActivity {

    private static final int CAT_CODE = 2;
    private Corporation mBean;
    private EditText txtName,txtDesc,txtContact,txtAddress;
    private ImageView img;
    private View nameLayout,layout;
    private Uri mUriImage;
    private static final int PICK_IMAGE = 1;
    private Button btnChangeImage,btnAddCat,btnSave;
    private AddEditCorporationFragment mFragment;
    public static Intent newIntent(Context context, Corporation bean){
        Intent intent = new Intent(context,AddEditCorporationActivity.class);
        intent.putExtra("bean",bean);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_corporation);
        mBean = getIntent().getParcelableExtra("bean");

        txtName=findViewById(R.id.txtName);
        txtDesc=findViewById(R.id.txtDesc);
        txtAddress=findViewById(R.id.txtAddress);
        txtContact=findViewById(R.id.txtContact);
        img=findViewById(R.id.imgName);
        nameLayout=findViewById(R.id.nameLayout);
        layout=findViewById(R.id.layout);
        btnChangeImage=findViewById(R.id.btnChangeImage);
        btnAddCat=findViewById(R.id.btnAddCat);
        btnSave=findViewById(R.id.btnSave);


        mFragment = AddEditCorporationFragment.newInstance(mBean);
        if (mBean != null){
            txtName.setText(mBean.getName());
            txtDesc.setText(mBean.getDesc());
            txtContact.setText(mBean.getContact());
            txtAddress.setText(mBean.getAddress());

                Glide.with(this).load(mBean.getImg()).into(img);


            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mFragment).commit();

            nameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (layout.getVisibility()==View.VISIBLE){
                        layout.setVisibility(View.GONE);
                    }else {
                        layout.setVisibility(View.VISIBLE);
                    }
                }
            });
        }else{
            mBean = new Corporation();
            mBean.setCode(FirebaseDatabase.getInstance().getReference().push().getKey());
        }


        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        btnAddCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddEditCorporationActivity.this,CatListActivity.class);
                i.putExtra("mode", CatListFragment.MODE_SELECT);
                startActivityForResult(i,CAT_CODE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                mBean.setAddress(txtAddress.getText().toString());
                mBean.setContact(txtContact.getText().toString());
                mBean.setDesc(txtDesc.getText().toString());
                mBean.setName(txtName.getText().toString());

                save();
            }
        });
    }

    private void save() {
        if(mUriImage == null){

            DatabaseReference mDataCor = FBCorporation.getDataRef().child(mBean.getCode());
            mDataCor.setValue(mBean);
            Toast.makeText(AddEditCorporationActivity.this, "Done", Toast.LENGTH_SHORT).show();
            finish();

        }else{
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage("Uploading image");
            dialog.show();
            StorageReference mReference = FirebaseStorage.getInstance().getReference().child("images");
            mReference.putFile(mUriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    dialog.dismiss();
                    if(task.isSuccessful()){


                        mBean.setImg(task.getResult().getDownloadUrl().toString());
                        DatabaseReference mDataCor = FBCorporation.getDataRef().child(mBean.getCode());
                        mDataCor.setValue(mBean);

                        Toast.makeText(AddEditCorporationActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        finish();

                    }else{
                        Toast.makeText(AddEditCorporationActivity.this, "Error| couldn't upload image", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            mUriImage = data.getData();
            Glide.with(this).load(mUriImage).into(img);
        }
        if (requestCode == CAT_CODE && resultCode == RESULT_OK) {
            Cat cat = data.getParcelableExtra("data");
            mFragment.addCat(cat.getCode());
        }
    }
    private void openGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        startActivityForResult(getIntent, PICK_IMAGE);
    }

}