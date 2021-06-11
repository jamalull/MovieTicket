package com.jamalulikhsan.movieticket.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.jamalulikhsan.movieticket.R
import com.jamalulikhsan.movieticket.sign.signin.SignInActivity
import kotlinx.android.synthetic.main.activity_onboarding1.*
import kotlinx.android.synthetic.main.activity_onboarding1.btn_lanjut
import kotlinx.android.synthetic.main.activity_onboarding1.btn_lewati
import kotlinx.android.synthetic.main.activity_onboarding2.*

class OnBoardingTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding2)
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()

        btn_lanjut.setOnClickListener {
            startActivity(Intent(this@OnBoardingTwoActivity, OnBoardingThreeActivity::class.java))
            finish()
        }

        btn_lewati.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this@OnBoardingTwoActivity, SignInActivity::class.java))
        }
    }
}
