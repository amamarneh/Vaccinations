package com.amarnehsoft.vaccinations.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.beans.Child
import com.amarnehsoft.vaccinations.beans.Vaccination
import com.amarnehsoft.vaccinations.controllers.AlarmsController
import com.amarnehsoft.vaccinations.database.sqlite.VacinationDB
import com.amarnehsoft.vaccinations.fragments.dialogs.DatePickerFragment
import com.amarnehsoft.vaccinations.utils.DateUtils
import java.util.*

class VaccinationActivity : AppCompatActivity(),DatePickerFragment.IDatePickerFragment {

    override fun onDateSet(reqCode: Int, year: Int, month: Int, day: Int) {
        val dat = DateUtils.getDate(year,month,day)
        val oDiff = vaccination?.age!! - DateUtils.getAgeInDays(child?.birthDate)
        val oDat = DateUtils.incrementDateByDays(oDiff)
        val r = DateUtils.getDiffDays(dat,oDat)
        val newAge = vaccination?.age!! + r

        vaccination?.newAge = newAge
        vaccination?.manuallySet=1
        VacinationDB.getInstance(this).saveBean(vaccination)

        val calendar = Calendar.getInstance()
        calendar.time=DateUtils.incrementDateByDays(newAge)
        calendar.set(Calendar.HOUR_OF_DAY,12)
        calendar.set(Calendar.MINUTE,0)

        AlarmsController.addFixedAlarm(this,calendar.timeInMillis, vaccination?.notificationId!!,vaccination,child)

        txtDate2?.text = DateUtils.formatDateWithoutTime(dat)
        val d = vaccination?.newAge!! - DateUtils.getAgeInDays(child?.birthDate)
        txtRemaingTime2?.text= d.toString() + " " + getString(R.string.days)
    }

    var txtVaccinationName : TextView? = null
    var txtChildName : TextView? = null
    var txtDesc : TextView? = null
    var txtAge : TextView? = null
    var txtRemaingTime : TextView? = null
    var txtRemaingTime2 : TextView? = null
    var txtDate : TextView? = null
    var txtDate2 : TextView? = null
    var btnChange : Button? = null
    var vaccination : Vaccination? = null
    var child : Child? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vaccination)
        vaccination = intent.getParcelableExtra<Vaccination>("vaccination")
        child = intent.getParcelableExtra<Child>("child")
        val age = DateUtils.getAgeInDays(child?.birthDate)
        txtVaccinationName = findViewById(R.id.txtVaccinationName)
        txtChildName = findViewById(R.id.txtChildName)
        txtDesc = findViewById(R.id.txtDesc)
        txtRemaingTime = findViewById(R.id.txtRemainingTime)
        txtRemaingTime2 = findViewById(R.id.txtRemainingTime2)
        txtDate = findViewById(R.id.txtDate)
        txtDate2 = findViewById(R.id.txtDate2)
        btnChange= findViewById(R.id.btnChange)
        txtAge = findViewById(R.id.txtAge)

        txtVaccinationName?.text =vaccination?.name
        txtChildName?.text = child?.name
        txtAge?.text = vaccination?.age.toString() + " " + getString(R.string.days)
        txtDesc?.text = vaccination?.desc
        val diff = (vaccination?.age!! - age )
        txtRemaingTime?.text = diff.toString() + " " + getString(R.string.days)
        txtDate?.text = DateUtils.formatDateWithoutTime(DateUtils.incrementDateByDays(diff))
        if(vaccination?.manuallySet==0){
            txtRemaingTime2?.text = txtRemaingTime?.text
            txtDate2?.text = txtDate?.text
        }else{
            val d = vaccination?.newAge!! - age
            val dat = DateUtils.incrementDateByDays(d)
            txtDate2?.text = DateUtils.formatDateWithoutTime(dat)
            txtRemaingTime2?.text = d.toString() + " " +getString(R.string.days)
        }

        btnChange?.setOnClickListener({
            var diff = 0
            if (vaccination?.manuallySet==0){
                diff = vaccination?.age!! - DateUtils.getAgeInDays(child?.birthDate)
            }else{
                diff = vaccination?.newAge!! - DateUtils.getAgeInDays(child?.birthDate)
            }
            val d = DateUtils.incrementDateByDays(diff)
            val c = Calendar.getInstance()
            c.time=d
            DatePickerFragment.newInstance(c,1,this).show(supportFragmentManager,DatePickerFragment.TAG)
        })
    }

    companion object {
        fun newIntent(context: Context?,vacination : Vaccination,child : Child?) : Intent {
            val intent = Intent(context, VaccinationActivity::class.java)
            intent.putExtra("vaccination",vacination)
            intent.putExtra("child",child)
            return intent
        }
    }
}
