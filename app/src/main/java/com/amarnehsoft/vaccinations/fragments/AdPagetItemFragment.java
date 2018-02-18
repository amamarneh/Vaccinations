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
import android.widget.ImageView;
import android.widget.TextView;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Ad;
import com.bumptech.glide.Glide;

public class AdPagetItemFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Ad mBean;

    public AdPagetItemFragment() {
    }

    public static AdPagetItemFragment newInstance(Ad bean) {
        AdPagetItemFragment fragment = new AdPagetItemFragment();
        Bundle args = new Bundle();
        args.putParcelable("bean",bean);
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
        return inflater.inflate(R.layout.row_ad, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView img = view.findViewById(R.id.img);
        TextView content = view.findViewById(R.id.txtContent);
        if (mBean != null){
            content.setText(mBean.getContent());
            Glide.with(view).load(mBean.getImg()).into(img);
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
