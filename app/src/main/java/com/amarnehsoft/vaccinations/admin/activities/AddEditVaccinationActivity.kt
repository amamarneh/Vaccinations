package com.amarnehsoft.vaccinations.admin.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.beans.Vaccination
import com.amarnehsoft.vaccinations.database.firebase.FBVacinations
import com.amarnehsoft.vaccinations.utils.NumberUtils
import kotlinx.android.synthetic.main.activity_add_edit_vaccination.*
import java.util.*

class AddEditVaccinationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_vaccination)
        var vaccination = intent.getParcelableExtra<Vaccination>("bean")
        btnSave.setOnClickListener({
            if (vaccination == null){
                vaccination = Vaccination()
                vaccination.code = UUID.randomUUID().toString()
            }
            vaccination.name=txtVaccinationName.text.toString()
            vaccination.desc=txtDesc.text.toString()
            vaccination.age=NumberUtils.getInteger(txtForAge.text.toString())

            val fb = FBVacinations(this,false)
            val saved = fb.save(vaccination,vaccination.code)
            if (saved){
                finish()
            }else{
                Toast.makeText(this,getString(R.string.errorWhileSavingTheCavvination),Toast.LENGTH_SHORT).show()
            }
        })
        txtVaccinationName.setText(vaccination?.name)
        txtDesc.setText(vaccination?.desc)
        txtForAge.setText(vaccination?.age.toString())

        btnDelete.setOnClickListener({
            val fb = FBVacinations(this,false)
            val deleted = fb.delete(vaccination.code)
            if (deleted){
                finish()
            }else{
                Toast.makeText(this,getString(R.string.errorWhileDeletingTheVaccination),Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        fun newIntent(context: Context?,vaccination:Vaccination?): Intent {
            val intent = Intent(context, AddEditVaccinationActivity::class.java)
            intent.putExtra("bean",vaccination)
            return intent
        }
    }
}
