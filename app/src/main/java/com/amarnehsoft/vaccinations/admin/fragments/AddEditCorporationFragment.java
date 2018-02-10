package com.amarnehsoft.vaccinations.admin.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.adapters.CatsAdapter;
import com.amarnehsoft.vaccinations.adapters.StockAdapter;
import com.amarnehsoft.vaccinations.admin.activities.StocksListActivity;
import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.database.firebase.FBCat;
import com.amarnehsoft.vaccinations.database.firebase.FBStocks;
import com.amarnehsoft.vaccinations.database.firebase.FirebaseHelper;
import com.amarnehsoft.vaccinations.fragments.itemDetail.CorporationDetailFragment;
import com.amarnehsoft.vaccinations.fragments.itemDetail.ItemDetailFragment;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditCorporationFragment extends ItemDetailFragment<Corporation> implements CatsAdapter.Listener{

    private RecyclerView catsRecyclerView,stocksRecyclerView;
    private ArrayList<String> mCats;
    public static AddEditCorporationFragment newInstance(Corporation bean){
        AddEditCorporationFragment fragment = new AddEditCorporationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ala","ala");
        bundle.putParcelable("bean",bean);
        Log.e("Amarneh","CorporationDetailFragment.newInstance,bean="+(bean==null));

        fragment.setArguments(bundle);
        return fragment;
    }

    public AddEditCorporationFragment() {
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
        mCats = getCats();
        Log.e("Amarneh","corporationDetailFragment.setBean , bean="+(mItem==null));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_edit_corporation, container, false);
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
            FBCat fbCat = new FBCat(getContext(),mItem.getCatCodes());
            CatsAdapter adapter = new CatsAdapter(fbCat.getList(),this);
            catsRecyclerView.setAdapter(adapter);
            fbCat.setAdapter(adapter);
        }else {
            Log.e("Amarneh","CorporationDetailFragment.onResume , mItem = null");
        }
    }
    public ArrayList<String> getCats(){
        if(mItem == null)
            return new ArrayList<>(1);
        String[] cats = Corporation.getCatsFromString(mItem.getCatCodes());
        List<String> list = Arrays.asList(cats);
        return new ArrayList<>(list);
    }

    public void addCat(String catCode){
        boolean found = false;
        for (String cat :
                mCats) {
            if (cat.equals(catCode))
                found = true;
        }
        if(!found){
            mCats.add(catCode);
        }
        refreshView();

        mItem.setCatCodes(catsListToString(mCats));
    }
    public void deleteCat(String catCode){
        int i=0;
        for (String cat :
                mCats) {

            if (cat.equals(catCode)){
                mCats.remove(i);
                break;
            }
            i++;
        }

        mItem.setCatCodes(catsListToString(mCats));
    }

    public String catsListToString(ArrayList<String> cats){
        return TextUtils.join(",",cats);

    }

    @Override
    protected void refreshView() {
        //nothing to refresh
        if (mItem != null){
            Log.e("Amarneh","CorporationDetailFragment.onResume , mItem != null");
            FBCat fbCat = new FBCat(getContext(),mItem.getCatCodes());
            final CatsAdapter adapter = new CatsAdapter(fbCat.getList(),this);
            fbCat.setListener(new FirebaseHelper.Listiner() {
                @Override
                public void onChildAdded(Object bean, String code) {
                    adapter.notifyDataSetChanged();
                }
            });
            catsRecyclerView.setAdapter(adapter);
            fbCat.setAdapter(adapter);
        }else {
            Log.e("Amarneh","CorporationDetailFragment.onResume , mItem = null");
        }
    }

    @Override
    public void onCatClicked(String catCode) {
//        FBStocks fbStocks = new FBStocks(getContext(),catCode,mItem.getCode());
//        StockAdapter adapter = new StockAdapter(fbStocks.getList());
//        stocksRecyclerView.setAdapter(adapter);
//        fbStocks.setAdapter(adapter);
        Intent i = new Intent(getContext(), StocksListActivity.class);
        i.putExtra("corporationCode",mItem.getCode());
        i.putExtra("catCode",catCode);
    startActivity(i);
    }

    @Override
    public void onLongClicked(final String catCode) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Remove this Cat")
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        deleteCat(catCode);
                        refreshView();

                    }
                })
                .show();
    }
}
