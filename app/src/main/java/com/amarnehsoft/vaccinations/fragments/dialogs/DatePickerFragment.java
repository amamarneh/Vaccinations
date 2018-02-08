package com.amarnehsoft.vaccinations.fragments.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jcc on 8/22/2017.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public static final String TAG="DatePickerFragment";
    public static final String ARG_CALENDAR = "calendar";
    public static final String ARG_REQ_CODE = "reqCode";

    private int mReqCode=-1;
    private IDatePickerFragment mListener;

    public void setmListener(IDatePickerFragment mListener) {
        this.mListener = mListener;
    }

//    public static DialogFragment newInstance(Calendar calendar, int requestCode){
//        DialogFragment fragment = new DatePickerFragment();
//        Bundle bundle = new Bundle();
//        bundle.putLong(ARG_CALENDAR,calendar.getTimeInMillis());
//        bundle.putInt(ARG_REQ_CODE,requestCode);
//        fragment.setArguments(bundle);
//        return fragment;
//    }
    public static DialogFragment newInstance(Calendar calendar, int requestCode, IDatePickerFragment mListener,boolean allowOld,boolean allowFuture){
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setmListener(mListener);
        Bundle bundle = new Bundle();
        bundle.putLong(ARG_CALENDAR,calendar.getTimeInMillis());
        bundle.putInt(ARG_REQ_CODE,requestCode);
        bundle.putBoolean("allowOld",allowOld);
        bundle.putBoolean("allowFuture",allowFuture);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        Long timeInMs = getArguments().getLong(ARG_CALENDAR);
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeInMs);
        int year= c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        mReqCode = getArguments().getInt(ARG_REQ_CODE);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog a = new DatePickerDialog(getActivity(), this, year, month, day);
        boolean allowOld = getArguments().getBoolean("allowOld");
        if (!allowOld)
            a.getDatePicker().setMinDate(System.currentTimeMillis());
        boolean allowFuture = getArguments().getBoolean("allowFuture");
        if (!allowFuture)
            a.getDatePicker().setMaxDate(System.currentTimeMillis());
        return a;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (IDatePickerFragment)context;
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        mListener.onDateSet(mReqCode,year,month,day);
    }

    public interface IDatePickerFragment{
        void onDateSet(int reqCode, int year, int month, int day);
    }
}