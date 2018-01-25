package com.amarnehsoft.vaccinations.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.beans.Child
import com.amarnehsoft.vaccinations.database.sqlite.ChildDB
import com.amarnehsoft.vaccinations.fragments.dialogs.DatePickerFragment
import com.amarnehsoft.vaccinations.utils.DateUtils
import kotlinx.android.synthetic.main.fragment_add_child.*
import java.util.*

class AddChildFragment : Fragment(),DatePickerFragment.IDatePickerFragment{
    var mChild : Child? = null

    override fun onDateSet(reqCode: Int, year: Int, month: Int, day: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        selectedDate = DateUtils.getDate(year,month,day)
        val str = DateUtils.formatDateWithoutTime(selectedDate)
        txtBirthDate.text=str
    }

    private var mListener: OnFragmentInteractionListener? = null
    lateinit var txtBirthDate : TextView
    var selectedDate : Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mChild = arguments!!.getParcelable("child")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_add_child, container, false)
        val btnAdd =v.findViewById<View>(R.id.btnAdd)
        val btnChange =v.findViewById<View>(R.id.btnChange)
        txtBirthDate = v.findViewById<TextView>(R.id.txtBirthdate)

        btnAdd.setOnClickListener {
            if(mChild == null){
                mChild = Child()
                mChild?.code=UUID.randomUUID().toString()
            }
            mChild?.name=txtName.text.toString()
            mChild?.birthDate=selectedDate
            ChildDB.getInstance(context).saveBean(mChild)
            mListener?.afterSaved(mChild)
        }

        btnChange.setOnClickListener({
            var c = Calendar.getInstance();
            if (selectedDate != null){
                c = DateUtils.getCalendarFromDate(selectedDate)
            }
            DatePickerFragment.newInstance(c,1,this).show(fragmentManager,DatePickerFragment.TAG)
        })
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtName.setText(mChild?.name)
        txtBirthDate.setText(DateUtils.formatDateWithoutTime(mChild?.birthDate))
        selectedDate = mChild?.birthDate

        if (selectedDate == null){
            selectedDate = Date()
            txtBirthDate.text = DateUtils.formatDateWithoutTime(selectedDate)
        }
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
        fun afterSaved(child: Child?)
    }

    companion object {
        fun newInstance(child : Child?): Fragment {
            val fragment = AddChildFragment()
            val args = Bundle()
            args.putParcelable("child",child)
            fragment.arguments = args
            return fragment
        }
    }
}
