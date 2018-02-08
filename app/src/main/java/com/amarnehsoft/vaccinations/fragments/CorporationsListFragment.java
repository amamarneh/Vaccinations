package com.amarnehsoft.vaccinations.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.activities.CorporationInfoActivity;
import com.amarnehsoft.vaccinations.activities.itemDetail.CorporationsDetailActivity;
import com.amarnehsoft.vaccinations.adapters.Adapter;
import com.amarnehsoft.vaccinations.adapters.CatsAdapter;
import com.amarnehsoft.vaccinations.adapters.Holder;
import com.amarnehsoft.vaccinations.beans.Cat;
import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.database.firebase.FBCat;
import com.amarnehsoft.vaccinations.database.firebase.FBCorporation;
import com.amarnehsoft.vaccinations.utils.StringsUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CorporationsListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private FBCorporation fb;
    private Spinner mSpinner;
    private EditText txtAddress;
    private String mCat=null,mQuery="",mAddress="";

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
        mSpinner=v.findViewById(R.id.spinner);
        txtAddress=v.findViewById(R.id.txtAddress);

        final List<String> list = new ArrayList<>();
        FBCat fb = new FBCat(getContext(),null){
            @Override
            protected void afterChildAdded(Cat bean, String code) {
                super.afterChildAdded(bean, code);
                list.add(bean.getName());
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, list);
                mSpinner.setAdapter(arrayAdapter);
                mCat=null;
                onQueryTextChanged(mQuery);
            }
        };
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCat = mSpinner.getSelectedItem().toString();
                onQueryTextChanged(mQuery);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAddress = s.toString();
                onQueryTextChanged(mQuery);
            }
        });
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

    public void onQueryTextChanged(String s) {
        mQuery = s;
        List<Corporation> list = fb.search(s);
        List<Corporation> list2=new ArrayList<>();
        if (mCat != null){
            for (Corporation bean : list){
                if (StringsUtils.like(bean.getCats(),"%"+mCat+"%")){
                    list2.add(bean);
                }
            }
        }else {
            list2 = list;
        }

        List<Corporation> list3 = new ArrayList<>();
        if (!TextUtils.isEmpty(mAddress)){
            for (Corporation bean : list2){
                if (StringsUtils.like(bean.getAddress(),"%"+mAddress+"%")){
                    list3.add(bean);
                }
            }
        }else {
            list3 = list2;
        }

        mAdapter.setList(list3);
        mAdapter.notifyDataSetChanged();
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
        private TextView txtName,txtAddress,txtCats;
        private ImageView img;

        public MyHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAddress=itemView.findViewById(R.id.txtAddress);
            txtCats = itemView.findViewById(R.id.txtCats);
            img = itemView.findViewById(R.id.img);
        }

        @Override
        public void onClicked(View v) {
            Log.e("Amarneh","onClicked,mItem="+ (mItem==null));
            startActivity(CorporationInfoActivity.newIntent(getContext(),mItem));
        }

        @Override
        public void bind(Corporation item) {
            super.bind(item);
            txtName.setText(item.getName());
            txtAddress.setText(item.getAddress());
            txtCats.setText(item.getCats());
            if (!TextUtils.isEmpty(item.getImg())){
                Glide.with(itemView).load(item.getImg()).into(img);
            }
        }
    }
}
