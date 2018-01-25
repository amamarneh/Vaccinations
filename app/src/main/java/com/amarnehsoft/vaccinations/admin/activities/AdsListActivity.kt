package com.amarnehsoft.vaccinations.admin.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.fragments.HomeAdsFragment
import kotlinx.android.synthetic.main.activity_ads_list.*

class AdsListActivity : AppCompatActivity(),HomeAdsFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ads_list)

        btnAdd.setOnClickListener({startActivity(AddEditAdsActivity.newIntent(this,null))})

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,HomeAdsFragment.newInstance()).commit()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, AdsListActivity::class.java)
            return intent
        }
    }
}
