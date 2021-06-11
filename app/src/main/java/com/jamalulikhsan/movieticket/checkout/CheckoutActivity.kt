package com.jamalulikhsan.movieticket.checkout

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jamalulikhsan.movieticket.R
import com.jamalulikhsan.movieticket.checkout.model.Checkout
import com.jamalulikhsan.movieticket.home.HomeScreenActivity
import com.jamalulikhsan.movieticket.utils.Preferences
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.activity_pilihbangku.*


class CheckoutActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private var total:Int = 0
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()

        preferences = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>

        for(a in dataList.indices) {
            total += dataList[a].harga!!.toInt()
        }
        dataList.add(Checkout("Total harus dibayar ", total.toString()))

        rv_seat_position.layoutManager = LinearLayoutManager(this)
        rv_seat_position.adapter = CheckoutAdapter(dataList) {

        }

        btn_bayar_tiket.setOnClickListener {
            var intent = Intent(this@CheckoutActivity, CheckoutSuccessActivity::class.java)
            startActivity(intent)
        }

        btn_batalkan.setOnClickListener {
            var intent = Intent(this@CheckoutActivity, HomeScreenActivity::class.java)
            startActivity(intent)
        }
    }

}
