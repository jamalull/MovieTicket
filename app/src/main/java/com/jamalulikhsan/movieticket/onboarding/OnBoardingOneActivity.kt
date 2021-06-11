package com.jamalulikhsan.movieticket.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.jamalulikhsan.movieticket.R
import com.jamalulikhsan.movieticket.sign.signin.SignInActivity
import com.jamalulikhsan.movieticket.utils.Preferences
import kotlinx.android.synthetic.main.activity_onboarding1.*

class OnBoardingOneActivity : AppCompatActivity() {

    lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding1)
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()

        //Inisialisasi Objek kelas Preferences
        preference = Preferences(this)
        if(preference.getValues("onboarding").equals("1")) {
            finishAffinity()

            var intent = Intent(this@OnBoardingOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }

        btn_lanjut.setOnClickListener {
            startActivity(Intent(this@OnBoardingOneActivity, OnBoardingTwoActivity::class.java))
            finish()
        }

        btn_lewati.setOnClickListener {
            preference.setValues("onboarding", "1")
            finishAffinity()

            startActivity(Intent(this@OnBoardingOneActivity, SignInActivity::class.java))
        }
    }
}
