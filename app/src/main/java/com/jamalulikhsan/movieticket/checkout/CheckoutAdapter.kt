package com.jamalulikhsan.movieticket.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jamalulikhsan.movieticket.R
import com.jamalulikhsan.movieticket.checkout.model.Checkout
import java.text.NumberFormat
import java.util.*

class CheckoutAdapter(private var data: List<Checkout>, private val listener: (Checkout) -> Unit) : RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_checkout, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CheckoutAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val tvPosisi = view.findViewById<TextView>(R.id.tv_name_position)
        private val tvHarga = view.findViewById<TextView>(R.id.tv_harga)

        fun bindItem(data: Checkout, listener: (Checkout) -> Unit, context: Context) {

            val localID = Locale("id", "ID")
            val formatRP = NumberFormat.getCurrencyInstance(localID)

            //tvPosisi.setText(data.kursi)
            tvHarga.setText(formatRP.format(data.harga!!.toDouble()))

            if(data.kursi!!.startsWith("Total")) {
                tvPosisi.setText(data.kursi)
                tvPosisi.setCompoundDrawables(null, null, null, null)
            } else {
                tvPosisi.setText("Seat No. "+data.kursi)
            }

            itemView.setOnClickListener{
                listener(data)
            }

        }

    }
}
