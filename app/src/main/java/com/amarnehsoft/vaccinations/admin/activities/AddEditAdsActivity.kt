package com.amarnehsoft.vaccinations.admin.activities

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.amarnehsoft.vaccinations.R
import kotlinx.android.synthetic.main.activity_add_edit_ads.*
import android.provider.MediaStore
import android.content.Intent
import android.graphics.Bitmap
import com.amarnehsoft.vaccinations.beans.Ad
import com.amarnehsoft.vaccinations.beans.Vaccination
import android.R.attr.data
import android.app.ProgressDialog
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import java.io.InputStream
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.OnSuccessListener
import android.support.annotation.NonNull
import android.widget.ImageView
import android.widget.Toast
import com.amarnehsoft.vaccinations.database.db2.DBAd
import com.amarnehsoft.vaccinations.database.db2.DBCats
import com.amarnehsoft.vaccinations.fragments.dialogs.DatePickerFragment
import com.amarnehsoft.vaccinations.utils.DateUtils
import com.amarnehsoft.vaccinations.utils.NumberUtils
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.OnPausedListener
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageMetadata
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import java.io.File
import java.util.*


class AddEditAdsActivity : AppCompatActivity(),DatePickerFragment.IDatePickerFragment {

    override fun onDateSet(reqCode: Int, year: Int, month: Int, day: Int) {
        if (reqCode == 1){
            val date = DateUtils.getDate(year,month,day)
            selectedFromDate = date.time
            btnFromDate.text = DateUtils.formatDateWithoutTime(Date(selectedFromDate))
            ad?.fromDate = selectedFromDate
        }else if (reqCode==2){
            val date = DateUtils.getDate(year,month,day)
            selectedToDate = date.time
            btnToDate.text = DateUtils.formatDateWithoutTime(Date(selectedToDate))
            ad?.toDate = selectedToDate
        }
    }

    val PICK_IMAGE = 1
    var selectedFromDate =0L
    var selectedToDate =0L

    private var mUriImage: Uri? = null
    var ad : Ad? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_ads)

        setTitle(getString(R.string.ads))
        ad = intent.getParcelableExtra<Ad>("bean")

        if(ad != null){
            Glide.with(this).load(ad!!.img).into(img)
            txtTitle.setText(ad?.content)
            btnFromDate.setText(DateUtils.formatDateWithoutTime(Date(ad?.fromDate!!)))
            btnToDate.setText(DateUtils.formatDateWithoutTime(Date(ad?.toDate!!)))
            selectedFromDate = ad?.fromDate!!
            selectedToDate = ad?.toDate!!
            txtSeconds.setText(ad?.seconds!!.toString())
        }else{
            ad = Ad()
            ad!!.code = UUID.randomUUID().toString()
            txtSeconds.setText("5")
            ad!!.seconds = 5
        }
        img.setOnClickListener({openGallery()})
        btnSave.setOnClickListener({
            save()
        })

        btnDelete.setOnClickListener({
            SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.areYouSure))
                    .setContentText(getString(R.string.removeAd))
                    .setConfirmText(getString(R.string.yes))
                    .setConfirmClickListener { sDialog ->
                        sDialog.dismissWithAnimation()
                        delete()
                    }
                    .show()
        })

        btnFromDate.setOnClickListener({
            var fd = Date(selectedFromDate)

            if(selectedFromDate==0L){
                fd = Date()
            }

            DatePickerFragment.newInstance(DateUtils.getCalendarFromDate(fd),1,this,true,true).show(supportFragmentManager,DatePickerFragment.TAG)
        })
        btnToDate.setOnClickListener({
            var td = Date(selectedToDate)
            if (selectedToDate ==0L){
                td = Date()
            }
            DatePickerFragment.newInstance(DateUtils.getCalendarFromDate(td),2,this,true,true).show(supportFragmentManager,DatePickerFragment.TAG)
        })
    }

    private fun delete(){
        if(ad != null){

        DBAd.getInstance(this).deleteBean(ad!!.code)
            finish()
        }

    }

    private fun save() {
        if (selectedFromDate == 0L || selectedToDate == 0L){
            Toast.makeText(this,getString(R.string.pleaseSelectFromDateAndToDate),Toast.LENGTH_LONG).show()
        }else{

            if (NumberUtils.getInteger(txtSeconds.text.toString()) <= 0){
                Toast.makeText(this,getString(R.string.pleaseEnterValidSeconds),Toast.LENGTH_LONG).show()
            }else{
                if (mUriImage == null) {

                    ad!!.content = txtTitle.text.toString()
                    ad!!.seconds = txtSeconds.text.toString().toInt()

                    DBAd.getInstance(this).saveBean(ad)

                    Toast.makeText(this, getString(R.string.done), Toast.LENGTH_SHORT).show()
                    val intent = Intent()
                    intent.putExtra("data", ad)
                    setResult(RESULT_OK, intent)
                    finish()

                } else {

                    val dialog = ProgressDialog(this)
                    dialog.setCancelable(false)
                    dialog.setMessage(getString(R.string.uploadImage))
                    dialog.show()
                    val mReference = FirebaseStorage.getInstance().reference.child("images").child(UUID.randomUUID().toString())
                    mReference.putFile(mUriImage!!).addOnCompleteListener { task ->
                        dialog.dismiss()
                        if (task.isSuccessful) {

                            ad!!.content = txtTitle.text.toString()
                            ad!!.img = task.result.downloadUrl!!.toString()
                            DBAd.getInstance(this).saveBean(ad)

                            Toast.makeText(this, getString(R.string.done), Toast.LENGTH_SHORT).show()
                            val intent = Intent()
                            intent.putExtra("data", ad)
                            setResult(RESULT_OK, intent)
                            finish()
                        } else {
                            Toast.makeText(this, getString(R.string.err), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type = "image/*"
//        intent.putExtra("crop", "true")
//        intent.putExtra("scale", true)
        intent.putExtra("outputX", 250)
        intent.putExtra("outputY", 250)
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("return-data", true)
        startActivityForResult(intent, 1)
//
//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            mUriImage = data!!.data
            Glide.with(this).load(mUriImage).into(img)
        }
    }

    private fun openGallery() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"
        startActivityForResult(getIntent, PICK_IMAGE)
    }

    companion object {
        fun newIntent(context: Context?, ad: Ad?): Intent {
            val intent = Intent(context, AddEditAdsActivity::class.java)
            intent.putExtra("bean",ad)
            return intent
        }
    }
}
