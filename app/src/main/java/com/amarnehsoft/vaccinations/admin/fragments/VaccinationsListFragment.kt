package com.amarnehsoft.vaccinations.admin.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.activities.VaccinationActivity
import com.amarnehsoft.vaccinations.admin.activities.AddEditVaccinationActivity
import com.amarnehsoft.vaccinations.beans.Ad
import com.amarnehsoft.vaccinations.beans.Vaccination
import com.amarnehsoft.vaccinations.beans.custome.VacinationForChild
import com.amarnehsoft.vaccinations.controllers.VaccinationsForChildrenController
import com.amarnehsoft.vaccinations.database.firebase.FBAd
import com.amarnehsoft.vaccinations.database.firebase.FBVacinations
import com.amarnehsoft.vaccinations.database.sqlite.ChildDB
import com.amarnehsoft.vaccinations.database.sqlite.VacinationDB
import com.amarnehsoft.vaccinations.fragments.HomeAdsFragment
import com.amarnehsoft.vaccinations.utils.DateUtils
import kotlinx.android.synthetic.main.row_vaccination.*
import java.util.*


class VaccinationsListFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null
    lateinit var recyclerView: RecyclerView
    lateinit var btnAdd:Button

    internal lateinit var helper: FBVacinations
    internal lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_vaccinations_list, container, false)
        btnAdd=v.findViewById(R.id.btnAdd)
        recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView);
        recyclerView.layoutManager=LinearLayoutManager(context)

        btnAdd.setOnClickListener({startActivity(AddEditVaccinationActivity.newIntent(context,null))})
        return v
    }

    override fun onResume() {
        super.onResume()
        helper = FBVacinations(context,true)
        adapter = Adapter(context, helper.list as List<Vaccination>)
        recyclerView.adapter = adapter
        helper.setAdapter(adapter)
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
        fun newInstance(): VaccinationsListFragment {
            val fragment = VaccinationsListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    inner class Adapter(internal var c: Context?, internal var beans: List<Vaccination>) : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val v = LayoutInflater.from(c).inflate(R.layout.row_vaccination_admin, parent, false)
            return MyViewHolder(v)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val bean = beans[position]
            holder.itemView.setOnClickListener {
                startActivity(AddEditVaccinationActivity.newIntent(context,bean))
            }
            holder.txtVaccinationName.text = bean.name
            holder.txtAge.text = bean.age.toString()
        }

        override fun getItemCount(): Int {
            return beans.size
        }

        fun setList(beans: List<Vaccination>) {
            this.beans = beans
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var txtAge: TextView
        internal var txtVaccinationName: TextView
        internal var img:ImageView

        init {
            txtAge = itemView.findViewById(R.id.txtAge)
            txtVaccinationName = itemView.findViewById<TextView>(R.id.txtVaccinationName)
            img = itemView.findViewById<ImageView>(R.id.img);
        }
    }
}
