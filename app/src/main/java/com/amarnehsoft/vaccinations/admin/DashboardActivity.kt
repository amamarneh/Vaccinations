package com.amarnehsoft.vaccinations.admin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.activities.CorporationsListActivity
import com.amarnehsoft.vaccinations.admin.activities.AdsListActivity
import com.amarnehsoft.vaccinations.admin.activities.CatListActivity
import com.amarnehsoft.vaccinations.admin.activities.KindergartenListActivity
import com.amarnehsoft.vaccinations.admin.activities.VaccinationsListActivity
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_home.*

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val vaccinationsLayout = findViewById<View>(R.id.vaccinationsLayout)
        val adsLayout = findViewById<View>(R.id.adsLayout)
        val btnKG = findViewById<View>(R.id.btnKindergarten)
        val btnCat = findViewById<View>(R.id.btnCat)

        btnCorporations.setOnClickListener({startActivity(Intent(this,CorporationsListActivity::class.java))})
        val i = Intent(this,KindergartenListActivity::class.java)
        btnKG.setOnClickListener({startActivity( i)})

        vaccinationsLayout.setOnClickListener({startActivity(VaccinationsListActivity.newIntent(this))})
        adsLayout.setOnClickListener({startActivity(AdsListActivity.newIntent(this))})

        btnCat.setOnClickListener({startActivity(Intent(this,CatListActivity::class.java))})
    }
}
