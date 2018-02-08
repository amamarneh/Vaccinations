package com.amarnehsoft.vaccinations.fragments

import android.content.Context
import android.os.Bundle
import android.provider.SyncStateContract
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.activities.KindergartenInfoActivity
import com.amarnehsoft.vaccinations.admin.activities.AddEditKindergartenActivity
import com.amarnehsoft.vaccinations.beans.Ad
import com.amarnehsoft.vaccinations.beans.Kindergarten
import com.amarnehsoft.vaccinations.beans.custome.VacinationForChild
import com.amarnehsoft.vaccinations.constants.VersionConstants
import com.amarnehsoft.vaccinations.controllers.VaccinationsForChildrenController
import com.amarnehsoft.vaccinations.database.firebase.FBAd
import com.amarnehsoft.vaccinations.database.firebase.FBKindergarten
import com.amarnehsoft.vaccinations.database.firebase.FBVacinations
import com.amarnehsoft.vaccinations.utils.StringsUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions


class KindergartensListFragment : Fragment() {
//    private var mListener : OnFragmentInteractionListener? = null
    lateinit var recyclerView: RecyclerView
    lateinit var txtAddress: EditText
    lateinit var mQuery:String
    lateinit var mAddress:String

    internal lateinit var helper: FBKindergarten
    internal lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }

        mQuery = ""
        mAddress=""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_kindergarten_list, container, false)
        recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView)
        txtAddress = v.findViewById<EditText>(R.id.txtAddress)

        recyclerView.layoutManager = LinearLayoutManager(context)
        helper = FBKindergarten(context)
        adapter = Adapter(context, helper.list as List<Kindergarten>)
        recyclerView.adapter = adapter
        helper.setAdapter(adapter)


        txtAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                mAddress = s.toString()
                search(mQuery)
            }
        })

        return v
    }

    fun search(s:String){
        mQuery = s
        var res = ArrayList<Kindergarten>()
        if (TextUtils.isEmpty(s))
            res = helper.list as ArrayList<Kindergarten>
        else{
            for (k in helper.list as List<Kindergarten>){
                if (StringsUtils.like(k.name,"%"+s+"%")){
                    res.add(k)
                }
            }
        }

        var list2 =  ArrayList<Kindergarten>()
        if (!TextUtils.isEmpty(mAddress)){
            for (k in res){
                if (StringsUtils.like(k.address,"%"+mAddress+"%"))
                    list2.add(k)
            }
        }else{
            list2 = res;
        }
        adapter.setList(list2)
        adapter.notifyDataSetChanged()
    }

//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            mListener = context
//        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }

//    override fun onDetach() {
//        super.onDetach()
//        mListener = null
//    }

//    interface OnFragmentInteractionListener {
//    }

    companion object {
        fun newInstance(): KindergartensListFragment {
            val fragment = KindergartensListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    inner class Adapter(internal var c: Context?, internal var beans: List<Kindergarten>) : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val v = LayoutInflater.from(c).inflate(R.layout.row_kindergarten, parent, false)
            return MyViewHolder(v)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val bean = beans[position]
            holder.itemView.setOnClickListener {
                if(VersionConstants.CURRENT_VERSION == VersionConstants.VERSION_USER)
                    startActivity(KindergartenInfoActivity.newIntent(context,bean))
                else
                    startActivity(AddEditKindergartenActivity.newIntent(context,bean))

            }
            holder.txtName.text = bean.name
            holder.txtAddress.text = bean.address
            holder.txtDesc.text = bean.description

            if(bean.imgUrl != null){
                Glide.with(holder.itemView.context).load(bean.imgUrl).apply(RequestOptions.circleCropTransform()).into(holder.imgName)
            }
        }

        override fun getItemCount(): Int {
            return beans.size
        }

        fun setList(beans: List<Kindergarten>) {
            this.beans = beans
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var txtName: TextView
        internal var txtAddress: TextView
        internal var txtDesc: TextView
        internal var imgName: ImageView
        init {
            txtName = itemView.findViewById<View>(R.id.txtName) as TextView
            txtAddress = itemView.findViewById<View>(R.id.txtAddress) as TextView
            txtDesc = itemView.findViewById<View>(R.id.txtDesc) as TextView
            imgName = itemView.findViewById<View>(R.id.imgName) as ImageView

        }
    }
}
