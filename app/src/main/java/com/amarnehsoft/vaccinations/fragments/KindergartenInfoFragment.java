package com.amarnehsoft.vaccinations.fragments;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.adapters.Adapter;
import com.amarnehsoft.vaccinations.adapters.Holder;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.utils.DateUtils;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KindergartenInfoFragment extends Fragment {

    private TextView txtName,txtAddress,txtDesc,txtDays,txtTimes,txtYears,txtContact;
    private OnFragmentInteractionListener mListener;
    private Kindergarten mBean;
    private ImageView imgName;
    private RecyclerView recyclerView;
    private View layoutExtra;
    public KindergartenInfoFragment() {
    }

    public static KindergartenInfoFragment newInstance(Kindergarten kindergarten) {
        KindergartenInfoFragment fragment = new KindergartenInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("bean",kindergarten);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_kindergarten_info, container, false);
        txtName = view.findViewById(R.id.txtName);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtDesc = view.findViewById(R.id.txtDesc);
        txtDays= view.findViewById(R.id.txtDays);
        txtTimes = view.findViewById(R.id.txtTimes);
        txtYears = view.findViewById(R.id.txtYears);
        txtContact = view.findViewById(R.id.txtContact);
        imgName = view.findViewById(R.id.imgName);
        recyclerView = view.findViewById(R.id.recyclerView);
        layoutExtra = view.findViewById(R.id.layoutExtra);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mBean != null){
            txtName.setText(mBean.getName());
            txtDesc.setText(mBean.getDescription());
            txtAddress.setText(mBean.getAddress());

            String days = getString(R.string.from) +" " + DateUtils.getDayStr(getContext(),mBean.getFromDay()) + " " + getString(R.string.to) + " " + DateUtils.getDayStr(getContext(),mBean.getToDay());
            String times = getString(R.string.at)+" " + mBean.getFromTime() + " " + getString(R.string.to) + " " + mBean.getToTime();
            String years = getString(R.string.from) +" " + mBean.getFromYear() + " " + "-" + " " + mBean.getToYear() + " " + getString(R.string.years);

            txtDays.setText(days);
            txtTimes.setText(times);
            txtYears.setText(years);
            txtContact.setText(mBean.getContactInfo());


            if(mBean.getImgUrl() != null){
                Glide.with(view).load(mBean.getImgUrl()).into(imgName);
            }


            String[] extra = mBean.getExtra().split("\n");

            List<String> extraList = null;
            if(!TextUtils.isEmpty(mBean.getExtra())){
                extraList =   Arrays.asList(extra);

            }
            setListExtra(extraList);





        }
    }

    private void setListExtra(List<String> list) {
        if(list == null){
            layoutExtra.setVisibility(View.GONE);
        }else{
            layoutExtra.setVisibility(View.VISIBLE);
            MyAdapter adapter = new MyAdapter(list);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
    }

    class MyHolder extends Holder<String>{
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
    class MyAdapter extends Adapter<String>{

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
