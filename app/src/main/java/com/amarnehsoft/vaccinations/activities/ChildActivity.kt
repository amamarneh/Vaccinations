package com.amarnehsoft.vaccinations.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.beans.Child
import com.amarnehsoft.vaccinations.beans.Vaccination
import com.amarnehsoft.vaccinations.beans.custome.VacinationForChild
import com.amarnehsoft.vaccinations.controllers.VaccinationsForChildrenController
import com.amarnehsoft.vaccinations.database.db2.DBVaccination
import com.amarnehsoft.vaccinations.database.sqlite.ChildDB
import com.amarnehsoft.vaccinations.database.sqlite.VacinationDB
import com.amarnehsoft.vaccinations.fragments.dialogs.ConfirmDialog
import com.amarnehsoft.vaccinations.utils.DateUtils
import java.util.*

class ChildActivity : Base() {
    var txtName : TextView? = null
    var txtAge : TextView? = null
    var txtBirthDate : TextView? = null

    var recyclerView : RecyclerView? = null
    var child : Child? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child)
        child = intent.getParcelableExtra<Child>("child")
        txtName = findViewById(R.id.txtName)
        txtAge = findViewById(R.id.txtAge)
        txtBirthDate = findViewById(R.id.txtBirthdate)
        recyclerView = findViewById(R.id.recyclerView)

        updateUI(child)
        setTitle(child?.name)
        recyclerView?.layoutManager=LinearLayoutManager(this)
        val adapter = Adapter(this,ArrayList())
        recyclerView?.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
//        var helper: FBVacinations
//        helper = object : FBVacinations(this,true){
//            override fun afterChildAdded(bean: Vaccination?,s:String?) {
//                super.afterChildAdded(bean,s)
                try {
                    recyclerView!!.adapter = Adapter(this@ChildActivity, VaccinationsForChildrenController(this).getVaccinationForChild(child))
                }catch (e : Exception){
                    Log.e("Amarneh","exc>>"+e.message)
                    e.printStackTrace()
                }
//            }
//        }
    }

    fun updateUI(child: Child?){
        txtName?.text =child?.name
        var diff = DateUtils.getDiffDays(Date(),child?.birthDate)
        if (diff == 0) diff++
        txtAge?.text = DateUtils.formatAge(this,diff)
        txtBirthDate?.text = DateUtils.formatDateWithoutTime(child?.birthDate)
    }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            val inflater = menuInflater
            inflater.inflate(R.menu.menu_child, menu)
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem?): Boolean {
            if (item?.itemId == R.id.menu_edit){
                startActivityForResult(AddChildActivity.newIntent(this,child),1)
            }
            if (item?.itemId == R.id.menu_add){
                startActivityForResult(AddVaccinationActivity.newIntent(this, null,child),2)
            }
            if(item?.itemId == R.id.menu_delete){
                val o = object : ConfirmDialog(this){
                    override fun title(): String {
                        return getString(R.string.deleteChild)
                    }

                    override fun msg(): String {
                        return getString(R.string.areYouSureYouWantToDeleteThisChild)
                    }

                    override fun onPositive() {
                        ChildDB.getInstance(this@ChildActivity).deleteBean(child?.code)
                        finish()
                    }

                }.create().show()
            }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            child = data?.getParcelableExtra("data")
            updateUI(child)
        }
    }

    inner class Adapter(internal var c: Context, internal var beans: List<VacinationForChild>) : RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val v = LayoutInflater.from(c).inflate(R.layout.row_vaccination, parent, false)
            return MyViewHolder(v)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val bean = beans[position]
            holder.itemView.setOnClickListener {
                if (bean.vaccination.type == Vaccination.TYPE_VACCINATION)
                    startActivity(VaccinationActivity.newIntent(this@ChildActivity,bean.vaccination, child))
                else
                    startActivity(AddVaccinationActivity.newIntent(this@ChildActivity,bean.vaccination,child))
            }

            holder.txtName.text = child?.name
            holder.txtVaccinationName.text = bean.vaccination.name

            val dt = bean.date

            val days = DateUtils.getDiffDays(dt, Date())
//            holder.txtRemainingTime2.text = days.toString() + " " + getString(R.string.days)

            holder.txtRemainingTime2.text = DateUtils.getRelative(dt)

            holder.txtDate.text = DateUtils.formatDateWithoutTime(dt)

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
        internal var img: ImageView

        init {
            txtName = itemView.findViewById<TextView>(R.id.txtName)
            txtRemainingTime = itemView.findViewById<TextView>(R.id.txtRemainingTime)
            txtRemainingTime2 = itemView.findViewById<TextView>(R.id.txtRemainingTime2)
            txtDate = itemView.findViewById<TextView>(R.id.txtDate)
            txtVaccinationName = itemView.findViewById<TextView>(R.id.txtVaccinationName)
            img = itemView.findViewById<ImageView>(R.id.img);
        }
    }

    companion object {
        fun newIntent(context: Context?,child : Child) : Intent {
            val intent = Intent(context,ChildActivity::class.java)
            intent.putExtra("child",child)
            return intent
        }
    }
}
