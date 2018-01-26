package com.amarnehsoft.vaccinations.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.activities.itemDetail.CorporationsDetailActivity;
import com.amarnehsoft.vaccinations.adapters.Adapter;
import com.amarnehsoft.vaccinations.adapters.Holder;
import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.database.firebase.FBCorporation;

import java.util.List;

public class CorporationsListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private FBCorporation fb;
    public CorporationsListFragment() {
    }

    public static CorporationsListFragment newInstance() {
        CorporationsListFragment fragment = new CorporationsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_corporations_list, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fb = new FBCorporation(getContext());
        mAdapter = new MyAdapter(fb.getList());
        recyclerView.setAdapter(mAdapter);
        fb.setAdapter(mAdapter);
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

    public class MyAdapter extends Adapter
    {

        public MyAdapter(List items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.row_corporation;
        }

        @Override
        public com.amarnehsoft.vaccinations.adapters.Holder getNewHolder(View v) {
            return new MyHolder(v);
        }
    }

    public class MyHolder extends Holder<Corporation>{
        private TextView txtName,txtAddress;

        public MyHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAddress=itemView.findViewById(R.id.txtAddress);
        }

        @Override
        public void onClicked(View v) {
            Log.e("Amarneh","onClicked,mItem="+ (mItem==null));
            startActivity(CorporationsDetailActivity.newIntent(getContext(),mItem));
        }

        @Override
        public void bind(Corporation item) {
            super.bind(item);
            txtName.setText(item.getName());
            txtAddress.setText(item.getAddress());
        }
    }
}
