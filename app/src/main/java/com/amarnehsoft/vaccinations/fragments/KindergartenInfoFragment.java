package com.amarnehsoft.vaccinations.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.utils.DateUtils;

public class KindergartenInfoFragment extends Fragment {

    private TextView txtName,txtAddress,txtDesc,txtFromDay,txtToDay,txtFromTime,txtToTime;
    private OnFragmentInteractionListener mListener;
    private Kindergarten mBean;

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
        txtFromDay= view.findViewById(R.id.txtFromDay);
        txtToDay = view.findViewById(R.id.txtToDay);
        txtFromTime = view.findViewById(R.id.txtFromTime);
        txtToTime = view.findViewById(R.id.txtToTime);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mBean != null){
            txtName.setText(mBean.getName());
            txtDesc.setText(mBean.getDescription());
            txtAddress.setText(mBean.getAddress());
            txtFromTime.setText(mBean.getFromTime());
            txtToTime.setText(mBean.getToTime());
            txtFromDay.setText(DateUtils.getDayStr(getContext(),mBean.getFromDay()));
            txtToDay.setText(DateUtils.getDayStr(getContext(),mBean.getToDay()));
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
}
