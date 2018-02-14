package com.amarnehsoft.vaccinations.admin.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.adapters.Adapter;
import com.amarnehsoft.vaccinations.adapters.Holder;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.fragments.KindergartenInfoFragment;
import com.amarnehsoft.vaccinations.fragments.dialogs.DatePickerFragment;
import com.amarnehsoft.vaccinations.utils.DateUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class KindergartenEditFragment extends Fragment {

    private static final int PICK_IMAGE = 1;
        private EditText txtName,txtAddress,txtDesc,txtFromDay,txtToDay,txtFromTime,txtToTime,txtFromYear,txtToYear,txtContact;
        private Button btnChangeImage;
        private Kindergarten mBean;
        private ImageView imgName;
//        private RecyclerView recyclerView;
        private View layoutExtra;
        private EditText txtExtra;
    private Uri mUriImage;
    private String imageUrl = null;
    OnFragmentInteractionListener mListener;
    public KindergartenEditFragment() {
        }

    public static KindergartenEditFragment newInstance(Kindergarten kindergarten) {
        KindergartenEditFragment fragment = new KindergartenEditFragment();
        Bundle args = new Bundle();
        args.putParcelable("bean",kindergarten);
        fragment.setArguments(args);
        return fragment;
    }


    public Kindergarten getKindergarten(){
        Kindergarten kindergarten = new Kindergarten();
        kindergarten.setCode(mBean.getCode());
        kindergarten.setAddress(txtAddress.getText().toString());
        kindergarten.setContactInfo(txtContact.getText().toString());
        kindergarten.setDescription(txtDesc.getText().toString());
        kindergarten.setFromDay(Integer.parseInt(txtFromDay.getText().toString()));
        kindergarten.setToDay(Integer.parseInt(txtToDay.getText().toString()));
        kindergarten.setFromTime(txtFromTime.getText().toString());
        kindergarten.setFromYear(Integer.parseInt(txtFromYear.getText().toString()));
        kindergarten.setToYear(Integer.parseInt(txtToYear.getText().toString()));
        kindergarten.setName(txtName.getText().toString().trim());
        kindergarten.setToTime(txtToTime.getText().toString());
        kindergarten.setImgUrl(imageUrl==null?mBean.getImgUrl():imageUrl);
        kindergarten.setExtra(txtExtra.getText().toString().trim());
        return kindergarten;

    }
    public Uri getUri(){
        return mUriImage;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kindergarten_edit, container, false);
        txtName = view.findViewById(R.id.txtName);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtDesc = view.findViewById(R.id.txtDesc);
        txtFromDay= view.findViewById(R.id.txtFromDay);
        txtToDay= view.findViewById(R.id.txtToDay);

        txtFromTime = view.findViewById(R.id.txtFromTime);
        txtToTime = view.findViewById(R.id.txtToTime);


        txtFromYear = view.findViewById(R.id.txtFromYear);
        txtToYear = view.findViewById(R.id.txtToYear);

        txtContact = view.findViewById(R.id.txtContact);
        imgName = view.findViewById(R.id.imgName);
        txtExtra = view.findViewById(R.id.txtExtra);
        layoutExtra = view.findViewById(R.id.layoutExtra);

        btnChangeImage =  view.findViewById(R.id.btnChangeImage);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage();
            }
        });
        return view;
    }

    private void changeImage() {
        openGallery();
    }
    private void openGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        startActivityForResult(getIntent, PICK_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            mUriImage = data.getData();
            Glide.with(this).load(mUriImage).into(imgName);

        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mBean != null){
            txtName.setText(mBean.getName());
            txtDesc.setText(mBean.getDescription());
            txtAddress.setText(mBean.getAddress());


            txtFromDay.setText(mBean.getFromDay()+"");
            txtFromTime.setText(mBean.getFromTime());
            txtFromYear.setText(mBean.getFromYear()+"");
            txtContact.setText(mBean.getContactInfo());
            txtToDay.setText(mBean.getToDay()+"");
            txtToTime.setText(mBean.getToTime());
            txtToYear.setText(mBean.getToYear()+"");

            if(mBean.getImgUrl() != null){
                Glide.with(view).load(mBean.getImgUrl()).into(imgName);
            }

            setListExtra(mBean.getExtra());

        }else{
            // new
            mBean = new Kindergarten();
            mBean.setCode(FirebaseDatabase.getInstance().getReference().push().getKey());
        }
    }

    private void setListExtra(String txt) {
        if(TextUtils.isEmpty(txt)){
            layoutExtra.setVisibility(View.GONE);
        }else{
            txtExtra.setText(txt);
        }
    }

    public interface OnFragmentInteractionListener {
        void onSaveClick(Kindergarten kindergarten, Uri uri);
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try {
//            mListener = (DatePickerFragment.IDatePickerFragment)context;
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    class MyHolder extends Holder<String> {
        private TextView txtExtra;
        public MyHolder(View itemView) {
            super(itemView);
            txtExtra = itemView.findViewById(R.id.txtExtra);
        }

        @Override
        public void onClicked(View v) {

        }

        @Override
        public void bind(String item) {
            super.bind(item);
            txtExtra.setText(mItem);
        }
    }
    class MyAdapter extends Adapter<String> {

        public MyAdapter(List<String> items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.row_extra;
        }

        @Override
        public Holder getNewHolder(View v) {
            return new MyHolder(v);
        }
    }
}
