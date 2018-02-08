package com.amarnehsoft.vaccinations.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.view.Menu
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.fragments.KindergartensListFragment
import com.amarnehsoft.vaccinations.interfaces.SearchViewExpandListener

class KindergartnsListActivity : Base(){

    lateinit var mFragment:KindergartensListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        mFragment = KindergartensListFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,mFragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        // See above
        MenuItemCompat.setOnActionExpandListener(searchItem, SearchViewExpandListener(this))
        MenuItemCompat.setActionView(searchItem, searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                mFragment.search(s)
                return false
            }
        })

        return true
    }

    companion object {
        fun newIntent(context: Context?): Intent{
            val intent = Intent(context, KindergartnsListActivity::class.java)
            return intent
        }
    }
}
