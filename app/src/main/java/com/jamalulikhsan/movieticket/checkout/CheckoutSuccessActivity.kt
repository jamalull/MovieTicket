package com.jamalulikhsan.movieticket.checkout

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.jamalulikhsan.movieticket.R
import com.jamalulikhsan.movieticket.home.HomeScreenActivity
import com.jamalulikhsan.movieticket.onboarding.OnBoardingOneActivity
import kotlinx.android.synthetic.main.activity_checkout_succesfull.*

class CheckoutSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_succesfull)
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()

        btn_home.setOnClickListener {
            finishAffinity()

            var intent = Intent(this@CheckoutSuccessActivity, HomeScreenActivity::class.java)
            startActivity(intent)
        }

    }
}
