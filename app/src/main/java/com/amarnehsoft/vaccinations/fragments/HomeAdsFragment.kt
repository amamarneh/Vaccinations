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
import com.amarnehsoft.vaccinations.beans.custome.VacinationForChild
import com.amarnehsoft.vaccinations.controllers.VaccinationsForChildrenController
import com.amarnehsoft.vaccinations.database.firebase.FBAd
import com.amarnehsoft.vaccinations.database.firebase.FBVacinations
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference




class HomeAdsFragment : Fragment() {
    private var mStorageRef: StorageReference? = null
    private var mListener: OnFragmentInteractionListener? = null
    lateinit var recyclerView: RecyclerView

    internal lateinit var helper: FBAd
    internal lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }

        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_home_ads, container, false)
        recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView);
        recyclerView.layoutManager = LinearLayoutManager(context)
        return v
    }

    override fun onResume() {
        super.onResume()
        helper = FBAd(context)
        adapter = Adapter(context, helper.list as List<Ad>)
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
        fun newInstance(): HomeAdsFragment {
            val fragment = HomeAdsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    inner class Adapter(internal var c: Context?, internal var beans: List<Ad>) : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val v = LayoutInflater.from(c).inflate(R.layout.row_ad, parent, false)
            return MyViewHolder(v)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val bean = beans[position]
            holder.itemView.setOnClickListener {  }
            holder.txtContent.text = bean.content

            Glide.with(this@HomeAdsFragment).load(bean?.img).into(holder.img)
        }

        override fun getItemCount(): Int {
            return beans.size
        }

        fun setList(beans: List<Ad>) {
            this.beans = beans
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var txtContent: TextView
        internal var img: ImageView

        init {
            txtContent = itemView.findViewById<View>(R.id.txtContent) as TextView
            img = itemView.findViewById<ImageView>(R.id.img)
        }
    }
}
