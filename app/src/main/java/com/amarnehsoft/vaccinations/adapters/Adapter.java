package com.amarnehsoft.vaccinations.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Corporation;
import com.amarnehsoft.vaccinations.fragments.CorporationsListFragment;

import java.util.List;

/**
 * Created by jcc on 1/25/2018.
 */


public abstract class Adapter<T> extends RecyclerView.Adapter<Holder>
{
    private List<T> mItems;

    public Adapter(List<T> items)
    {
        mItems = items;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(getLayoutId(), parent, false);
        return getNewHolder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position)
    {
        T item = mItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
    public void setList(List<T> items){
        mItems=items;
    }

    public abstract int getLayoutId();
    public abstract Holder getNewHolder(View v);
}