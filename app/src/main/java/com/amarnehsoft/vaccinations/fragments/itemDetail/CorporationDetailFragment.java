package com.amarnehsoft.vaccinations.fragments.itemDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.adapters.CatsAdapter;
import com.amarnehsoft.vaccinations.adapters.StockAdapter;
import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.database.db2.DBCorCat;
import com.amarnehsoft.vaccinations.database.db2.DBStock;
import com.amarnehsoft.vaccinations.database.firebase.FBCat;
import com.amarnehsoft.vaccinations.database.firebase.FBStocks;


public class CorporationDetailFragment extends ItemDetailFragment<Corporation> implements CatsAdapter.Listener{

    private RecyclerView catsRecyclerView,stocksRecyclerView;

    public static CorporationDetailFragment newInstance(Corporation bean){
        CorporationDetailFragment fragment = new CorporationDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ala","ala");
        bundle.putParcelable("bean",bean);
        Log.e("Amarneh","CorporationDetailFragment.newInstance,bean="+(bean==null));

        fragment.setArguments(bundle);
        return fragment;
    }

    public CorporationDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (String key : getArguments().keySet()){
            Log.e("Amarneh","key:"+key +"="+getArguments().get(key));
        }
        if (savedInstanceState != null){
            for (String key : savedInstanceState.keySet()){
                Log.e("Amarneh","savedInstanceState.key:"+key +"="+savedInstanceState.get(key));
            }
        }

        setBean();

        String args = getArguments().toString();
        Log.e("Amarneh","args="+args);
        String test = getArguments().getString("ala");
        Log.e("Amarneh","ala="+test);
        Log.e("Amarneh","corporationDetailFragment.onCreate");
    }

    private void setBean(){
        mItem = getArguments().getParcelable("bean");
        Log.e("Amarneh","corporationDetailFragment.setBean , bean="+(mItem==null));
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
            Log.e("Amarneh","CorporationDetailFragment.onResume , mItem != null");
//            FBCat fbCat = new FBCat(getContext(),mItem.getCatCodes());
            CatsAdapter adapter = new CatsAdapter(DBCorCat.getInstance(getContext()).getCatFromCorporation(mItem.getCode()),this);
            catsRecyclerView.setAdapter(adapter);
//            fbCat.setAdapter(adapter);
        }else {
            Log.e("Amarneh","CorporationDetailFragment.onResume , mItem = null");
        }
    }

    @Override
    protected void refreshView() {
        //nothing to refresh
    }

    @Override
    public void onCatClicked(String catCode) {
//        FBStocks fbStocks = new FBStocks(getContext(),catCode,mItem.getCode());
        StockAdapter adapter = new StockAdapter(DBStock.getInstance(getContext()).getAll(catCode,mItem.getCode()));
        stocksRecyclerView.setAdapter(adapter);
//        fbStocks.setAdapter(adapter);
    }

    @Override
    public void onLongClicked(String catCode) {
        //d o nothing
    }
}
