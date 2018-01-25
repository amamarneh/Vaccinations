package com.amarnehsoft.vaccinations.activities

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.database.firebase.FBVacinations
import com.amarnehsoft.vaccinations.fragments.ChildrenHorizantalListFragment
import com.amarnehsoft.vaccinations.fragments.HomeAdsFragment
import com.amarnehsoft.vaccinations.fragments.HomeVaccinationsFragment
import com.amarnehsoft.vaccinations.fragments.ShoppingFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity()
                    ,ChildrenHorizantalListFragment.OnFragmentInteractionListener
                    ,HomeAdsFragment.OnFragmentInteractionListener
                    ,ShoppingFragment.OnFragmentInteractionListener
                    ,HomeVaccinationsFragment.OnFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction().replace(R.id.childrenLayout,ChildrenHorizantalListFragment.newInstance()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.notificationsLayout,HomeVaccinationsFragment.newInstance()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.shoppingLayout,ShoppingFragment.newInstance()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.adsLayout,HomeAdsFragment.newInstance()).commit()
    }
}
