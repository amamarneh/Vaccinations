package com.amarnehsoft.vaccinations.activities

import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.controllers.SPController
import com.amarnehsoft.vaccinations.database.sqlite.ChildDB
import com.amarnehsoft.vaccinations.fragments.*
import com.amarnehsoft.vaccinations.fragments.dialogs.ConfirmDialog
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : Base()
                    ,ChildrenHorizantalListFragment.OnFragmentInteractionListener
                    ,HomeAdsFragment.OnFragmentInteractionListener
                    ,HomeAdsPagerFragment.OnFragmentInteractionListener
                    ,AdPagetItemFragment.OnFragmentInteractionListener
                    ,ShoppingFragment.OnFragmentInteractionListener
                    ,HomeVaccinationsFragment.OnFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction().replace(R.id.childrenLayout,ChildrenHorizantalListFragment.newInstance()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.notificationsLayout,HomeVaccinationsFragment.newInstance()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.shoppingLayout,ShoppingFragment.newInstance()).commit()
        supportFragmentManager.beginTransaction().replace(R.id.adsLayout,HomeAdsPagerFragment.newInstance()).commit()
    }

    override fun onResume() {
        super.onResume()
        setTitle(SPController.getInstance(this).title)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_settings){
            startActivity(SettingsActivity.newIntent(this))
        }
        if (item?.itemId == R.id.menu_language){

        }
        return super.onOptionsItemSelected(item)
    }


}
