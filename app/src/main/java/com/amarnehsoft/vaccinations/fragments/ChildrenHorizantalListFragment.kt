package com.amarnehsoft.vaccinations.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.beans.Child
import android.support.v7.widget.LinearLayoutManager
import com.amarnehsoft.vaccinations.activities.AddChildActivity
import com.amarnehsoft.vaccinations.activities.ChildActivity
import com.amarnehsoft.vaccinations.database.sqlite.ChildDB


class ChildrenHorizantalListFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null
    var recyclerView : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.fragment_children_horizantal_list, container, false)
        val btnAddChild : View
        recyclerView=v.findViewById<RecyclerView>(R.id.recyclerView)
        btnAddChild= v.findViewById(R.id.btnAddChild)

        recyclerView?.layoutManager = (LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))

        btnAddChild.setOnClickListener { startActivity(AddChildActivity.newIntent(context,null))  }

        return v
    }

    override fun onResume() {
        super.onResume()
        val list = ChildDB.getInstance(context).all
        if (list.size==0)
            recyclerView?.visibility=View.GONE
        recyclerView?.adapter=Adapter(context,list)
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
            val fragment = ChildrenHorizantalListFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    inner class Adapter(internal var c: Context?, internal var beans: List<Child>) : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val v = LayoutInflater.from(c).inflate(R.layout.row_child, parent, false)
            return MyViewHolder(v)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val bean = beans[position]
            holder.itemView.setOnClickListener { startActivity(ChildActivity.newIntent(context,bean)) }
            holder.txtName.text = bean.name
        }

        override fun getItemCount(): Int {
            return beans.size
        }

        fun setList(beans: List<Child>) {
            this.beans = beans
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var txtName: TextView

        init {
            txtName = itemView.findViewById<TextView>(R.id.txtName)
        }
    }
}
