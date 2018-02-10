package com.amarnehsoft.vaccinations.admin.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.admin.activities.AddEditKindergartenActivity;
import com.amarnehsoft.vaccinations.beans.Cat;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.database.firebase.FBCat;
import com.amarnehsoft.vaccinations.database.firebase.FBKindergarten;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class AddEditCatFragment extends Fragment {
    private Cat mBean;
    private Uri mUriImage;
    private static final int PICK_IMAGE = 1;
    private Button btnSave,btnChangeImage;
    private ImageView imageView;
    private EditText txtName;
    public static Fragment newInstance(Cat cat) {
        Fragment fragment = new AddEditCatFragment();
        Bundle args = new Bundle();
        args.putParcelable("bean",cat);
        fragment.setArguments(args);
        return fragment;
    }
    public AddEditCatFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBean = getArguments().getParcelable("bean");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_edit_cat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnSave = view.findViewById(R.id.btnSave);
        imageView = view.findViewById(R.id.imageView);
        txtName = view.findViewById(R.id.txtName);
        btnChangeImage =  view.findViewById(R.id.btnChangeImage);
        if(mBean != null){
            if(mBean.getImg() != null)
                Glide.with(getContext()).load(mBean.getImg()).into(imageView);
            txtName.setText(mBean.getName());
        }else{
            mBean = new Cat();
            mBean.setCode(FirebaseDatabase.getInstance().getReference().push().getKey());
        }

        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save();
            }
        });
    }
    private Cat getBean(){
        Cat cat= new Cat();
        cat.setCode(mBean.getCode());
        cat.setImg(mBean.getImg());
        cat.setName(txtName.getText().toString());
        return cat;
    }

    private void save() {
        DatabaseReference mDataCat = FBCat.getDataRef().child(mBean.getCode());
        if(mUriImage == null){

                mDataCat.setValue(getBean());
                Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("data",getBean());
                getActivity().setResult(RESULT_OK,intent);
                getActivity().finish();

        }else{

            final ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setCancelable(false);
            dialog.setMessage("Uploading image");
            dialog.show();
            StorageReference mReference = FirebaseStorage.getInstance().getReference().child("images");
            mReference.putFile(mUriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    dialog.dismiss();
                    if(task.isSuccessful()){

                        Cat cat = getBean();
                        cat.setImg(task.getResult().getDownloadUrl().toString());
                        FBCat fbCat = new FBCat(getContext());
                        if(fbCat.save(cat,cat.getCode())){
                            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("data",cat);
                            getActivity().setResult(RESULT_OK,intent);
                            getActivity().finish();
                        }
                        else
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(getContext(), "Error| couldn't upload image", Toast.LENGTH_SHORT).show();
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
            Glide.with(this).load(mUriImage).into(imageView);
        }
    }
    private void openGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        startActivityForResult(getIntent, PICK_IMAGE);
    }


}
