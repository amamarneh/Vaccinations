package com.amarnehsoft.vaccinations.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.activities.CorporationsListActivity
import com.amarnehsoft.vaccinations.activities.KindergartnsListActivity
import com.amarnehsoft.vaccinations.activities.StocksListActivity
import com.amarnehsoft.vaccinations.beans.Stock
import java.util.*

class ShoppingFragment : Fragment(){

    private var mListener: OnFragmentInteractionListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_shopping, container, false)
//        val toys = v.findViewById<View>(R.id.toysLayout)
//        val clothes = v.findViewById<View>(R.id.clothesLayout)
//        val food = v.findViewById<View>(R.id.foodLayout)
        val kindergartens = v.findViewById<View>(R.id.kindergartenLayout);
        val corporations = v.findViewById<View>(R.id.corporationsLayout);

//        toys.setOnClickListener({startActivity(StocksListActivity.newIntent(context,Stock.TYPE_TOY))})
//        clothes.setOnClickListener({startActivity(StocksListActivity.newIntent(context,Stock.TYPE_CLOTH))})
//        food.setOnClickListener({startActivity(StocksListActivity.newIntent(context,Stock.TYPE_FOOD))})
        kindergartens.setOnClickListener({startActivity(KindergartnsListActivity.newIntent(context))})
        corporations.setOnClickListener({startActivity(CorporationsListActivity.newIntent(context))})

        return v
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
    }

    companion object {
        fun newInstance(): Fragment {
            val fragment = ShoppingFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
