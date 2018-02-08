package com.amarnehsoft.vaccinations.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.beans.Ad
import com.amarnehsoft.vaccinations.beans.Kindergarten
import com.amarnehsoft.vaccinations.beans.Stock
import com.amarnehsoft.vaccinations.beans.custome.VacinationForChild
import com.amarnehsoft.vaccinations.controllers.VaccinationsForChildrenController
import com.amarnehsoft.vaccinations.database.firebase.FBAd
import com.amarnehsoft.vaccinations.database.firebase.FBKindergarten
import com.amarnehsoft.vaccinations.database.firebase.FBStocks
import com.amarnehsoft.vaccinations.database.firebase.FBVacinations


class StocksListFragment : Fragment() {
    private var mListener: OnFragmentInteractionListener? = null
    lateinit var recyclerView: RecyclerView
    var catCode : String=""
    var corporationCode:String=""
    internal lateinit var helper: FBStocks
    internal lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            catCode = arguments!!.getString("catCode")
            corporationCode=arguments!!.getString("corporationCode");
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_stocks_list, container, false)
        recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView);
        recyclerView.layoutManager = LinearLayoutManager(context)
        helper = FBStocks(context,catCode,corporationCode)
        adapter = Adapter(context, helper.list as List<Stock>)
        recyclerView.adapter = adapter
        helper.setAdapter(adapter)
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
        fun newInstance(catCode:String,corporationCode:String): StocksListFragment {
            val fragment = StocksListFragment()
            val args = Bundle()
            args.putString("catCode",catCode)
            args.putString("corporationCode",corporationCode);
            fragment.arguments = args
            return fragment
        }
    }

    inner class Adapter(internal var c: Context?, internal var beans: List<Stock>) : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val v = LayoutInflater.from(c).inflate(R.layout.row_stock, parent, false)
            return MyViewHolder(v)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val bean = beans[position]
            holder.itemView.setOnClickListener {  }
            holder.txtName.text = bean.name
        }

        override fun getItemCount(): Int {
            return beans.size
        }

        fun setList(beans: List<Stock>) {
            this.beans = beans
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var txtName: TextView
        init {
            txtName = itemView.findViewById<View>(R.id.txtName) as TextView
        }
    }
}
