package com.amarnehsoft.vaccinations.admin.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.admin.fragments.VaccinationsListFragment
import com.amarnehsoft.vaccinations.beans.Child
import com.amarnehsoft.vaccinations.fragments.AddChildFragment

class VaccinationsListActivity : AppCompatActivity(),VaccinationsListFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,VaccinationsListFragment.newInstance()).commit()
    }


    companion object {
        fun newIntent(context: Context): Intent{
            val intent = Intent(context, VaccinationsListActivity::class.java)
            return intent
        }
    }
}
