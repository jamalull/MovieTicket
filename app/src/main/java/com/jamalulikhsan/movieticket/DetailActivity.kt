package com.jamalulikhsan.movieticket

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.jamalulikhsan.movieticket.checkout.PilihBangkuActivity
import com.jamalulikhsan.movieticket.home.dashboard.PlaysAdapter
import com.jamalulikhsan.movieticket.model.Film
import com.jamalulikhsan.movieticket.model.Plays
import kotlinx.android.synthetic.main.activity_detail_movie.*
import java.util.ArrayList

class DetailActivity : AppCompatActivity() {

    private lateinit var mDatabase :DatabaseReference
    private var dataList = ArrayList<Plays>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()

        val data = intent.getParcelableExtra<Film>("data")

        mDatabase = FirebaseDatabase.getInstance("https://movie-ticket-628c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Film")
            .child(data?.judul.toString())
            .child("play")

        tv_title_detail_movie.text = data?.judul
        tv_genre_detail_movie.text = data?.genre
        tv_desc_detail_movie.text = data?.desc
        tv_rate_detail_movie.text = data?.rating

        Glide.with(this)
            .load(data?.poster)
            .into(iv_poster_detail_movie)

        rv_directors.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getData()

        btn_ok_detail_movie.setOnClickListener {
            val intent = Intent(this@DetailActivity, PilihBangkuActivity::class.java).putExtra("data", data)
            startActivity(intent)
        }
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@DetailActivity, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()

                for(getdataSnapshot in dataSnapshot.children) {
                    val film = getdataSnapshot.getValue(Plays::class.java)
                    dataList.add(film!!)
                }

                rv_directors.adapter = PlaysAdapter(dataList) {

                }
            }

        })
    }
}
