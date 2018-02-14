package com.amarnehsoft.vaccinations.admin.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.amarnehsoft.vaccinations.R
import com.amarnehsoft.vaccinations.beans.Vaccination
import com.amarnehsoft.vaccinations.database.db2.DBVaccination
import com.amarnehsoft.vaccinations.utils.NumberUtils
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
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

//            val fb = FBVacinations(this,false)
//            val saved = fb.save(vaccination,vaccination.code)
//            if (saved){
            DBVaccination.getInstance(this).saveBean(vaccination)
                finish()
//            }else{
//                Toast.makeText(this,getString(R.string.errorWhileSavingTheCavvination),Toast.LENGTH_SHORT).show()
//            }
        })
        if(vaccination != null){
        txtVaccinationName.setText(vaccination?.name)
        txtDesc.setText(vaccination?.desc)
        txtForAge.setText(vaccination?.age.toString())
        }

        btnDelete.setOnClickListener({

                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getString(R.string.areYouSure))
                        .setContentText(getString(R.string.delete))
                        .setConfirmText(getString(R.string.yes))
                        .setConfirmClickListener { sDialog ->
                            sDialog.dismissWithAnimation()
                            val deleted = DBVaccination.getInstance(this).deleteBean(vaccination.code)
                            if (deleted){
                                finish()
                            }else{
                                Toast.makeText(this,getString(R.string.errorWhileDeletingTheVaccination),Toast.LENGTH_SHORT).show()
                            }
                        }
                        .show()

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
