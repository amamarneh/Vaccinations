package com.amarnehsoft.vaccinations.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.beans.Stock
import com.amarnehsoft.vaccinations.fragments.StocksListFragment

class StocksListActivity : AppCompatActivity(),StocksListFragment.OnFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        val catCode = intent.getStringExtra("catCode")
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,StocksListFragment.newInstance(catCode)).commit()
    }

    companion object {
        fun newIntent(context: Context?,catCode:String): Intent{
            val intent = Intent(context, StocksListActivity::class.java)
            intent.putExtra("catCode",catCode)
            return intent
        }
    }
}
