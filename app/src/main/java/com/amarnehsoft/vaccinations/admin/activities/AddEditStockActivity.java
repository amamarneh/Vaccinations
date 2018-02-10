package com.amarnehsoft.vaccinations.admin.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Cat;
import com.amarnehsoft.vaccinations.beans.Stock;
import com.amarnehsoft.vaccinations.database.firebase.FBCat;
import com.amarnehsoft.vaccinations.database.firebase.FBStocks;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddEditStockActivity extends AppCompatActivity {
    private TextView txtName,txtDesc,txtPrice;
    private ImageView img;
    private Button btnSave,btnChangeImage;
    private Stock mStock;
    private String corporationCode;
    private String catCode;
    private Uri mUriImage;
    private static final int PICK_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_stock);

        txtName=findViewById(R.id.txtName);
        txtPrice=findViewById(R.id.txtPrice);
        txtDesc=findViewById(R.id.txtDesc);
        img = findViewById(R.id.img);
        btnSave = findViewById(R.id.btnSave);
        btnChangeImage= findViewById(R.id.btnChangeImage);

        mStock = getIntent().getParcelableExtra("bean");
        corporationCode = getIntent().getStringExtra("corporationCode");
        catCode = getIntent().getStringExtra("catCode");

        if(mStock != null){

        txtName.setText(mStock.getName());
        txtPrice.setText(mStock.getPrice()+"");
        txtDesc.setText(mStock.getDesc());
        if(mStock.getImg() != null){
            Glide.with(this).load(mStock.getImg()).into(img);
        }
        }else {
            mStock = new Stock();
            mStock.setCode(FirebaseDatabase.getInstance().getReference().push().getKey());
            mStock.setCatCode(catCode);
            mStock.setCorporationCode(corporationCode);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareBean();
                save();
            }
        });

        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void prepareBean() {
        mStock.setName(txtName.getText().toString());
        mStock.setDesc(txtDesc.getText().toString());
        mStock.setPrice(Double.parseDouble( txtPrice.getText().toString()));
    }

    private void save() {
        final DatabaseReference mDataStock = FBStocks.getDataRef().child(mStock.getCode());
        if(mUriImage == null){

            mDataStock.setValue(mStock);
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("data",mStock);
            setResult(RESULT_OK,intent);
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


                        mStock.setImg(task.getResult().getDownloadUrl().toString());
                        mDataStock.setValue(mStock);
                        Toast.makeText(AddEditStockActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.putExtra("data",mStock);
                        setResult(RESULT_OK,intent);
                        finish();

                    }else{
                        Toast.makeText(AddEditStockActivity.this, "Error| couldn't upload image", Toast.LENGTH_SHORT).show();
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
    }
    private void openGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        startActivityForResult(getIntent, PICK_IMAGE);
    }
}
