package com.amarnehsoft.vaccinations.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.beans.Child
import com.amarnehsoft.vaccinations.fragments.AddChildFragment

class AddChildActivity : Base(),AddChildFragment.OnFragmentInteractionListener {
    override fun afterSaved(child: Child?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val i = Intent()
        i.putExtra("data",child)
        setResult(Activity.RESULT_OK,i)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        setTitle("Child")
        val c = intent.getParcelableExtra<Child>("child")
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,AddChildFragment.newInstance(c)).commit()
    }


    companion object {
        fun newIntent(context: Context?, child: Child?): Intent{
            val intent = Intent(context,AddChildActivity::class.java)
            intent.putExtra("child",child);
            return intent
        }
    }
}
