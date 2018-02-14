package com.amarnehsoft.vaccinations.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.beans.Stock
import com.amarnehsoft.vaccinations.fragments.StocksListFragment

class StocksListActivity : Base(),StocksListFragment.OnFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        val catCode = intent.getStringExtra("catCode")
        val corporationCode = intent.getStringExtra("corporationCode")
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,StocksListFragment.newInstance(catCode,corporationCode)).commit()

        setTitle(getString(R.string.stocks))
    }

    companion object {
        fun newIntent(context: Context?,catCode:String,corporationCode:String): Intent{
            val intent = Intent(context, StocksListActivity::class.java)
            intent.putExtra("catCode",catCode)
            intent.putExtra("corporationCode",corporationCode)
            return intent
        }
    }
}
