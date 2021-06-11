package com.jamalulikhsan.movieticket.checkout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.jamalulikhsan.movieticket.R
import com.jamalulikhsan.movieticket.checkout.model.Checkout
import com.jamalulikhsan.movieticket.model.Film
import kotlinx.android.synthetic.main.activity_pilihbangku.*

class PilihBangkuActivity : AppCompatActivity() {

    var statusA1:Boolean = false
    var statusA2:Boolean = false
    var total:Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilihbangku)
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()

        val data = intent.getParcelableExtra<Film>("data")
        tv_title_seat.text = data.judul

        seat_A1.setOnClickListener{
            if(statusA1) {
                seat_A1.setImageResource(R.drawable.ic_empty)
                statusA1 = false
                total -= 1
                pesanTiket(total)

                // delete data
                dataList.remove(Checkout("A4", "70000"))

            } else {
                seat_A1.setImageResource(R.drawable.ic_selected)
                statusA1 = true
                total += 1
                pesanTiket(total)

                val data = Checkout("A1", "55000")
                dataList.add(data)
            }
        }

        seat_A2.setOnClickListener{
            if(statusA2) {
                seat_A2.setImageResource(R.drawable.ic_empty)
                statusA2 = false
                total -= 1
                pesanTiket(total)

                // delete data
                dataList.remove(Checkout("A4", "70000"))

            } else {
                seat_A2.setImageResource(R.drawable.ic_selected)
                statusA2 = true
                total += 1
                pesanTiket(total)

                val data = Checkout("A2", "55000")
                dataList.add(data)
            }
        }

        btn_pesantiket.setOnClickListener {
            var intent = Intent(this@PilihBangkuActivity, CheckoutActivity::class.java).putExtra("data", dataList)
            startActivity(intent)
        }

    }

    private fun pesanTiket(total: Int) {
        if(total == 0){
            btn_pesantiket.setText("Pesan Tiket")
            btn_pesantiket.visibility = View.INVISIBLE
        } else {
            btn_pesantiket.setText("Pesan Tiket ("+total+")")
            btn_pesantiket.visibility = View.VISIBLE
        }

    }
}
