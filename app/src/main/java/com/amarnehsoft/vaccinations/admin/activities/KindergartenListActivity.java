package com.amarnehsoft.vaccinations.admin.activities;

import android.support.v4.app.Fragment;

import com.amarnehsoft.vaccinations.activities.abstractActivities.EmptyActivity;
import com.amarnehsoft.vaccinations.beans.Kindergarten;
import com.amarnehsoft.vaccinations.fragments.KindergartensListFragment;

/**
 * Created by alaam on 1/26/2018.
 */

public class KindergartenListActivity extends EmptyActivity<Kindergarten> {
    @Override
    protected String getBarTitle() {
        return "Kindergarten";
    }

    @Override
    public Fragment getFragment() {
        return  KindergartensListFragment.Companion.newInstance();
    }
}
