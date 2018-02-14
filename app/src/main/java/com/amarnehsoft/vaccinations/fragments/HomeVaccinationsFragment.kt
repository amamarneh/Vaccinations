package com.amarnehsoft.vaccinations.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.activities.AddVaccinationActivity
import com.amarnehsoft.vaccinations.activities.VaccinationActivity
import com.amarnehsoft.vaccinations.beans.Vaccination
import com.amarnehsoft.vaccinations.beans.custome.VacinationForChild
import com.amarnehsoft.vaccinations.controllers.VaccinationsForChildrenController

import com.amarnehsoft.vaccinations.utils.DateUtils
import org.w3c.dom.Text
import java.util.*


class HomeVaccinationsFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null
    lateinit var recyclerView: RecyclerView
    lateinit var txtName:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_home_vaccinations, container, false)
        recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView);
        txtName=v.findViewById<TextView>(R.id.txtName)
        recyclerView.layoutManager=LinearLayoutManager(context)
        return v
    }

    override fun onResume() {
        super.onResume()
        txtName.visibility=View.INVISIBLE
        recyclerView.adapter = Adapter(context, VaccinationsForChildrenController(context).notifications)

    }

    override fun onAttach(context: Context) {
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
            val fragment = HomeVaccinationsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    inner class Adapter(internal var c: Context?, internal var beans: List<VacinationForChild>) : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val v = LayoutInflater.from(c).inflate(R.layout.row_vaccination, parent, false)
            return MyViewHolder(v)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            txtName.visibility=View.VISIBLE
            val bean = beans[position]
            holder.itemView.setOnClickListener {
                if (bean.vaccination.type==Vaccination.TYPE_VACCINATION)
                    startActivity(VaccinationActivity.newIntent(context,bean.vaccination,bean.child))
                else
                    startActivity(AddVaccinationActivity.newIntent(context,bean.vaccination,bean.child))
            }
            holder.txtName.text = bean.child.name
            holder.txtVaccinationName.text = bean.vaccination.name
//            val diff = bean.vaccination.age - DateUtils.getAgeInDays(bean.child.birthDate)
//            if (diff == 0){
//                holder.txtRemainingTime.text = getString(R.string.today)
//                holder.txtRemainingTime2.text = getString(R.string.today)
//            }
//            else{
//                holder.txtRemainingTime.text = diff.toString() + " " + getString(R.string.days)
//                holder.txtRemainingTime2.text = diff.toString() + " " + getString(R.string.days)
//            }
//            val d = DateUtils.incrementDateByDays(Date(),diff)

            val dt = bean.date

            val days = DateUtils.getDiffDays(dt, Date())

//            holder.txtRemainingTime2.text = days.toString() + " " + getString(R.string.days)
            holder.txtDate.text = DateUtils.formatDateWithoutTime(dt)

            holder.txtRemainingTime2.text = DateUtils.getRelative(dt)


            Log.d("tag","bean.child.name="+bean.child.name.toString())
            Log.d("tag","bean.child.birthDate.toString()="+bean.child.birthDate.toString())

            Log.d("tag","dt.toString()="+dt.toString())
            if(bean.vaccination.type==Vaccination.TYPE_VACCINATION){
                holder.img.setImageDrawable(resources.getDrawable(R.drawable.vaccination2))
            }else{
                holder.img.setImageDrawable(resources.getDrawable(R.drawable.ic_date_range_black_36dp))
            }
        }

        override fun getItemCount(): Int {
            return beans.size
        }

        fun setList(beans: List<VacinationForChild>) {
            this.beans = beans
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var txtName: TextView
        internal var txtRemainingTime: TextView
        internal var txtRemainingTime2: TextView
        internal var txtDate: TextView
        internal var txtVaccinationName: TextView
        internal var img:ImageView

        init {
            txtName = itemView.findViewById<TextView>(R.id.txtName)
            txtRemainingTime = itemView.findViewById<TextView>(R.id.txtRemainingTime)
            txtRemainingTime2 = itemView.findViewById<TextView>(R.id.txtRemainingTime2)
            txtDate = itemView.findViewById<TextView>(R.id.txtDate)
            txtVaccinationName = itemView.findViewById<TextView>(R.id.txtVaccinationName)
            img = itemView.findViewById<ImageView>(R.id.img);
        }
    }
}
