package com.jamalulikhsan.movieticket.home

import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jamalulikhsan.movieticket.R
import com.jamalulikhsan.movieticket.home.dashboard.DashboardFragment
import kotlinx.android.synthetic.main.activity_homescreen.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar!!.hide()

        val fragmentHome = DashboardFragment()
        val fragmentTicket = TicketFragment()
        val fragmentSetting = SettingsFragment()

        setFragment(fragmentHome)

        iv_menu1.setOnClickListener{
            setFragment(fragmentHome)

            changeIcon(iv_menu1, R.drawable.ic_home_active)
            changeIcon(iv_menu1, R.drawable.ic_ticket1)
            changeIcon(iv_menu1, R.drawable.ic_profile1)
        }
        iv_menu2.setOnClickListener{
            setFragment(fragmentTicket)

            changeIcon(iv_menu1, R.drawable.ic_home1)
            changeIcon(iv_menu1, R.drawable.ic_tiket_active)
            changeIcon(iv_menu1, R.drawable.ic_profile1)
        }
        iv_menu3.setOnClickListener{
            setFragment(fragmentSetting)

            changeIcon(iv_menu1, R.drawable.ic_home1)
            changeIcon(iv_menu1, R.drawable.ic_ticket1)
            changeIcon(iv_menu1, R.drawable.ic_profile_active)
        }
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }
}
