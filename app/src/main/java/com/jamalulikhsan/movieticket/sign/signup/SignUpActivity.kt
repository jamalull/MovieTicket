package com.jamalulikhsan.movieticket.sign.signup

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.jamalulikhsan.movieticket.R
import com.jamalulikhsan.movieticket.sign.signin.SignInActivity
import com.jamalulikhsan.movieticket.sign.signin.User
import com.jamalulikhsan.movieticket.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.btn_register
import kotlinx.android.synthetic.main.activity_sign_up.et_password
import kotlinx.android.synthetic.main.activity_sign_up.et_username

class SignUpActivity : AppCompatActivity() {

    lateinit var eFullname:String
    lateinit var eUsername:String
    lateinit var eEmail:String
    lateinit var ePassword:String

    lateinit var mDatabaseReference: DatabaseReference
    lateinit var mFirebaseInstance : FirebaseDatabase
    //lateinit var mDatabase : DatabaseReference

    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()

        mFirebaseInstance = FirebaseDatabase.getInstance("https://movie-ticket-628c9-default-rtdb.asia-southeast1.firebasedatabase.app/")
        //mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = mFirebaseInstance.getReference("User")

        preferences = Preferences(this)

        btn_back.setOnClickListener{
            var intent = Intent(this@SignUpActivity, SignInActivity::class.java)
            startActivity(intent)
        }

        btn_register.setOnClickListener {
            eFullname = et_fullname.text.toString()
            eUsername = et_username.text.toString()
            eEmail = et_email.text.toString()
            ePassword = et_password.text.toString()

            if(eFullname.equals("")) {
                et_fullname.error = "Silahkan tulis Nama Anda"
                et_fullname.requestFocus()
            }else if (eUsername.equals("")) {
                et_username.error = "Silahkan tulis username Anda"
                et_username.requestFocus()
            }else if (eEmail.equals("")) {
                et_email.error = "Silahkan tulis email Anda"
                et_email.requestFocus()
            }else if (ePassword.equals("")) {
                et_password.error = "Silahkan tulis password Anda"
                et_password.requestFocus()
            } else {
                saveUsername (eFullname,eUsername,eEmail,ePassword)
            }
        }
    }

    private fun saveUsername(eFullname: String, eUsername: String, eEmail: String, ePassword: String) {
        var user = User()
        user.nama = eFullname
        user.username = eUsername
        user.email = eEmail
        user.password = ePassword

        if (eUsername != null) {
            checkingUsername(eUsername, user)
        }
    }

    private fun checkingUsername(eUsername: String, data: User) {
        mDatabaseReference.child(eUsername).addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if(user == null) {
                    mDatabaseReference.child(eUsername).setValue(data)

                    preferences.setValues("nama", data.nama.toString())
                    preferences.setValues("user", data.username.toString())
                    preferences.setValues("saldo", "")
                    preferences.setValues("url", "")
                    preferences.setValues("email", data.email.toString())
                    preferences.setValues("status", "1")

                    val photoscreen = Intent(this@SignUpActivity, PhotoScreenActivity::class.java).putExtra("data", data)
                    startActivity(photoscreen)
                } else {
                    Toast.makeText(this@SignUpActivity, "User sudah digunakan !", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })

    }
}
