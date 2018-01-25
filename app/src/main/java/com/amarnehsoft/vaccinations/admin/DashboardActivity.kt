package com.amarnehsoft.vaccinations.admin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.admin.activities.AdsListActivity
import com.amarnehsoft.vaccinations.admin.activities.VaccinationsListActivity
import kotlinx.android.synthetic.main.activity_home.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val vaccinationsLayout = findViewById<View>(R.id.vaccinationsLayout)
        val adsLayout = findViewById<View>(R.id.adsLayout)

        vaccinationsLayout.setOnClickListener({startActivity(VaccinationsListActivity.newIntent(this))})
        adsLayout.setOnClickListener({startActivity(AdsListActivity.newIntent(this))})
    }
}
