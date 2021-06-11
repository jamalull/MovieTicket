package com.jamalulikhsan.movieticket.home.dashboard

import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import com.jamalulikhsan.movieticket.DetailActivity
import com.jamalulikhsan.movieticket.R
import com.jamalulikhsan.movieticket.model.Film
import com.jamalulikhsan.movieticket.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabaseReference: DatabaseReference

    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //preferences = Preferences(activity!!.applicationContext)
        preferences = Preferences(requireActivity().applicationContext)
        mDatabaseReference = FirebaseDatabase.getInstance("https://movie-ticket-628c9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Film")

        tv_name.setText(preferences.getValues("nama"))
        if(!preferences.getValues("saldo").equals("")) {
            currency(preferences.getValues("saldo")!!.toDouble(), tv_saldo)
        }

        Glide.with(this)
            .load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_pic_profile)
        rv_nowplaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_coomingsoon.layoutManager = LinearLayoutManager(context)

        getData()
    }

    private fun getData() {
        mDatabaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear()
                for(getdataSnapshot in dataSnapshot.children) {
                    val film = getdataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                rv_nowplaying.adapter = NowPlayingAdapter(dataList) {
                    val intent = Intent(context, DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }

                rv_coomingsoon.adapter = CoomingSoonAdapter(dataList) {
                    val intent = Intent(context, DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun currency(nominal: Double, tvSaldo: TextView) {
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        tvSaldo.setText(format.format(nominal))
    }
}