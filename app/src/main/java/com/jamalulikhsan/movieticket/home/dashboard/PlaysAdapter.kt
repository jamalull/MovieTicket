package com.jamalulikhsan.movieticket.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jamalulikhsan.movieticket.R
import com.jamalulikhsan.movieticket.model.Plays

class PlaysAdapter(private var data: List<Plays>, private val listener: (Plays) -> Unit) : RecyclerView.Adapter<PlaysAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_play, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val tvName = view.findViewById<TextView>(R.id.tv_name)

        private val tvImage = view.findViewById<ImageView>(R.id.iv_poster_plays)

        fun bindItem(data:Plays, listener: (Plays) -> Unit, context: Context) {
            tvName.setText(data.nama)

            Glide.with(context)
                .load(data.url)
                .apply(RequestOptions.circleCropTransform())
                .into(tvImage)

            itemView.setOnClickListener{
                listener(data)
            }

        }

    }
}
