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
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import java.io.InputStream
import com.google.firebase.storage.UploadTask
import com.google.android.gms.tasks.OnSuccessListener
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.OnPausedListener
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageMetadata
import java.io.File


class AddEditAdsActivity : AppCompatActivity() {

    lateinit var inputStream : InputStream
    lateinit var uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_ads)

        img.setOnClickListener({pickImage()})
        btnSave.setOnClickListener({
            //upload the image to firebase
            val storageRef = FirebaseStorage.getInstance().getReference()

//            val file = File(uri.path)

// Create the file metadata
//            val metadata = StorageMetadata.Builder()
//                    .setContentType("image/jpeg")
//                    .build()

// Upload file and metadata to the path 'images/mountains.jpg'
            val uploadTask = storageRef.child("images/1.jpg").putFile(uri)

// Listen for state changes, errors, and completion of the upload.
            uploadTask.addOnProgressListener(OnProgressListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                Log.e("Amarneh","Upload is $progress% done")
            }).addOnPausedListener(OnPausedListener<UploadTask.TaskSnapshot> { Log.e("Amarneh","Upload is paused") }).addOnFailureListener(OnFailureListener {
                // Handle unsuccessful uploads
            }).addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                // Handle successful uploads on complete
                val downloadUrl = taskSnapshot.metadata!!.downloadUrl
            }).addOnFailureListener({Log.e("Amarneh","faild !!!")})
        })
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == 1) {
            val extras = data.extras
            if (extras != null) {
                //Get image
                val newProfilePic = extras.getParcelable<Bitmap>("data")
                img.setImageBitmap(newProfilePic)
            }
            uri = data.data
            inputStream = getContentResolver().openInputStream(data.data)
            Log.e("Amarneh","inputStream is null = "+(inputStream==null).toString())
            Log.e("Amarneh","uri = " + uri.lastPathSegment)
            val b = BitmapFactory.decodeStream(inputStream)
            img.setImageBitmap(b)
        }
    }

    companion object {
        fun newIntent(context: Context?, ad: Ad?): Intent {
            val intent = Intent(context, AddEditAdsActivity::class.java)
            intent.putExtra("bean",ad)
            return intent
        }
    }
}
