package com.jamalulikhsan.movieticket.sign.signup

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.jamalulikhsan.movieticket.home.HomeScreenActivity
import com.jamalulikhsan.movieticket.R
import com.jamalulikhsan.movieticket.sign.signin.SignInActivity
import com.jamalulikhsan.movieticket.sign.signin.User
import com.jamalulikhsan.movieticket.utils.Preferences
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_photoscreen.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_photoscreen.btn_back
import java.util.*


class PhotoScreenActivity : AppCompatActivity(), PermissionListener {

    //val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd:Boolean = false
    lateinit var filePath : Uri

    lateinit var storage : FirebaseStorage
    lateinit var storageReferensi : StorageReference
    lateinit var preferences: Preferences

    lateinit var user : User
    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photoscreen)
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance("gs://movie-ticket-628c9.appspot.com")//"gs://movie-ticket-628c9.appspot.com/"
        storageReferensi = storage.getReference("images")

        mFirebaseInstance = FirebaseDatabase.getInstance("https://movie-ticket-628c9-default-rtdb.asia-southeast1.firebasedatabase.app/")
        mFirebaseDatabase = mFirebaseInstance.getReference("User")

        user = intent.getParcelableExtra("data")
        photo_name.text = user.nama

        btn_add_img.setOnClickListener{
            if(statusAdd) {
                statusAdd = false
                btn_save.visibility = View.INVISIBLE
                btn_add_img.setImageResource(R.drawable.ic_add_circle)
                img_profile.setImageResource(R.drawable.ic_pic_profile)
            } else {
//                Dexter.withActivity(this)
//                    .withPermission(Manifest.permission.CAMERA)
//                    .withListener(this)
//                    .check()
                ImagePicker.with(this)
                    .cameraOnly()
                    .start()
            }
        }

        btn_skip.setOnClickListener {
            finishAffinity()

            val intent = Intent(this@PhotoScreenActivity, HomeScreenActivity::class.java)
            startActivity(intent)
        }

        btn_save.setOnClickListener {
            if(filePath != null) {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading ...")
                progressDialog.show()

                Log.v("wadah", "file uri upload 2"+ filePath)

                val ref = storageReferensi.child("images/" + UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this@PhotoScreenActivity, "Uploaded", Toast.LENGTH_LONG).show()

                        ref.downloadUrl.addOnSuccessListener {
                            saveToFirebase(it.toString())
//                            preferences.setValues("url", it.toString())
//
//                            Log.v("wadah", "url$it")
//
//                            finishAffinity()
//                            val home = Intent(this@PhotoScreenActivity, HomeScreenActivity::class.java)
//                            startActivity(home)
                        }
                    }
                    .addOnFailureListener{ e->
                        progressDialog.dismiss()
                        Toast.makeText(this, "Failed"+ e.message, Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener {
                        taskSnapshot -> val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload" + progress.toInt() + "%")
                    }

            } //else { }
        }

        btn_back.setOnClickListener{
            val intent = Intent(this@PhotoScreenActivity, SignInActivity::class.java)
            startActivity(intent)
        }

    }

    private fun saveToFirebase(url: String) {
        mFirebaseDatabase.child(user.username!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                user.url = url
                mFirebaseDatabase.child(user.username!!).setValue(user)

                preferences.setValues("nama", user.nama.toString())
                preferences.setValues("user", user.username.toString())
                preferences.setValues("saldo", "")
                preferences.setValues("url", "")
                preferences.setValues("email", user.email.toString())
                preferences.setValues("status", "1")
                preferences.setValues("url", url)

                finishAffinity()
                val intent = Intent(this@PhotoScreenActivity, HomeScreenActivity::class.java)
                startActivity(intent)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@PhotoScreenActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
//            takePictureIntent ->
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//            }
//        }
        ImagePicker.with(this)
            .cameraOnly()
            .start()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        TODO("Not yet implemented")
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Anda tidak dapat upload foto", Toast.LENGTH_LONG).show()
    }


    override fun onBackPressed() {
        Toast.makeText(this, "Terburu-buru ? Upload Nanti Saja", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            //var bitmap = data?.extras?.get("data") as Bitmap
            statusAdd = true

            filePath = data?.data!!
            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(img_profile)

            Log.v("wadah", "file uri upload$filePath")

            btn_save.visibility = View.VISIBLE
            btn_add_img.setImageResource(R.drawable.ic_delete_imgprofile)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Task Cancelled.", Toast.LENGTH_LONG).show()
        }
    }


}
