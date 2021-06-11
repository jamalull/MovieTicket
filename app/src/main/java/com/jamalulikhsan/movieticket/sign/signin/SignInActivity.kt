package com.jamalulikhsan.movieticket.sign.signin

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.jamalulikhsan.movieticket.home.HomeScreenActivity
import com.jamalulikhsan.movieticket.R
import com.jamalulikhsan.movieticket.sign.signup.SignUpActivity
import com.jamalulikhsan.movieticket.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    lateinit var iUsername:String
    lateinit var iPassword:String
    //lateinit var bRegister:String

    lateinit var mDatabase : DatabaseReference
    lateinit var preference : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()

        // Untuk menghubungkan Database Firebase dari Url ke Android Studio
        mDatabase = FirebaseDatabase.getInstance("https://movie-ticket-628c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")

        // kondisional disini untuk membuat onboarding hanya tampil 1 kali saat
        // pengguna baru (Aplikasi pertama kali diinstal)
        preference = Preferences(this)
        preference.setValues("onboarding", "1")
        if(preference.getValues("status").equals("1")) {
            finishAffinity()

            var home = Intent(this@SignInActivity, HomeScreenActivity::class.java)
            startActivity(home)
        }

        btn_masuk.setOnClickListener {
            iUsername = et_username.text.toString()
            iPassword = et_password.text.toString()

            if (iUsername.equals("")) {
                et_username.error = "Silahkan tulis usename Anda"
                et_username.requestFocus()
            } else if (iPassword.equals("")) {
                et_password.error = "Silahkan tulis password Anda"
                et_password.requestFocus()
            } else {
                pushLogin(iUsername,iPassword)
            }
        }

        btn_register.setOnClickListener{
            var formDaftar = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(formDaftar)
        }
    }

    //fungsi pushlogin untuk mengirimkan data user dan validasi data ke DB.
    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(this@SignInActivity, "User tidak ditemukan !", Toast.LENGTH_LONG).show()
                } else {
                    //Jika data di DB dan di Inputan Teks Password benar, maka User dapat login.
                    if (user.password.equals(iPassword)) {
                        //Preferensi disini digunakan untuk menyimpan data dari DB kedalam aplikasi.
                        preference.setValues("nama", user.nama.toString())
                        preference.setValues("username", user.username.toString())
                        preference.setValues("url", user.url.toString())
                        preference.setValues("email", user.email.toString())
                        preference.setValues("saldo", user.saldo.toString())
                        preference.setValues("status", "1")

                        var intent = Intent(this@SignInActivity, HomeScreenActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignInActivity, "Password Anda salah !", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message, Toast.LENGTH_LONG).show()
            }
        })

    }
}