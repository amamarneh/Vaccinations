package com.amarnehsoft.vaccinations.fragments.itemDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.adapters.CatsAdapter;
import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.database.firebase.FBCat;
import com.amarnehsoft.vaccinations.database.firebase.FBStocks;


public class CorporationDetailFragment extends ItemDetailFragment<Corporation> implements CatsAdapter.Listener{

    private RecyclerView catsRecyclerView,stocksRecyclerView;

    public static ItemDetailFragment newInstance(Corporation bean){
        ItemDetailFragment fragment = new CorporationDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("bean",bean);
        fragment.setArguments(bundle);
        return fragment;
    }

    public CorporationDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBean();
    }

    private void setBean(){
        mItem = getArguments().getParcelable("bean");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_corporation_details, container, false);
        catsRecyclerView = rootView.findViewById(R.id.hRecyclerView);
        stocksRecyclerView = rootView.findViewById(R.id.recyclerView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        catsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true));
        stocksRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mItem != null){
            FBCat fbCat = new FBCat(getContext(),mItem.getCode());
            CatsAdapter adapter = new CatsAdapter(fbCat.getList(),this);
            catsRecyclerView.setAdapter(adapter);
            fbCat.setAdapter(adapter);

        }
    }

    @Override
    protected void refreshView() {
        //nothing to refresh
    }

    @Override
    public void onCatClicked(String catCode) {
        FBStocks fbStocks = new FBStocks(getContext(),catCode);

    }
}
