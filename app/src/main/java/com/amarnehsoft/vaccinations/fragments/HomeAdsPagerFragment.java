package com.amarnehsoft.vaccinations.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amarnehsoft.vaccinations.R;
import com.amarnehsoft.vaccinations.beans.Ad;
import com.amarnehsoft.vaccinations.controllers.SPController;
import com.amarnehsoft.vaccinations.database.db2.DBAd;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeAdsPagerFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<Ad> mList = new ArrayList<>();
    private CountDownTimer mCounter;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;


    public HomeAdsPagerFragment() {
    }

    public static HomeAdsPagerFragment newInstance() {
        HomeAdsPagerFragment fragment = new HomeAdsPagerFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_ads_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mList = DBAd.getInstance(getContext()).getAll(new Date().getTime());
        if (mList.size() == 0){
            getView().setVisibility(View.GONE);
        }else {
            getView().setVisibility(View.VISIBLE);
        }

        mPagerAdapter.notifyDataSetChanged();


        final int[] position = {SPController.getInstance(getContext()).getLastAdPosition()};
        int seconds = 5000;
        mCounter = new CountDownTimer(mList.size() * seconds * 100,seconds) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (position[0] > mList.size())
                    position[0] = 0;
                mPager.setCurrentItem(position[0],true);
                SPController.getInstance(getContext()).setLastAdPosition(position[0]);
                position[0]++;
            }

            @Override
            public void onFinish() {

            }
        }.start();
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

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("Amarneh","getItem,position="+position);
            Ad bean = mList.get(position);
            return AdPagetItemFragment.newInstance(bean);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }

    public interface OnFragmentInteractionListener {
    }
}
