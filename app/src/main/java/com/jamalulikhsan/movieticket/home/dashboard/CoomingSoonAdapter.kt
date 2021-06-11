package com.jamalulikhsan.movieticket.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jamalulikhsan.movieticket.R
import com.jamalulikhsan.movieticket.model.Film

class CoomingSoonAdapter(private var data: List<Film>, private val listener: (Film) -> Unit) : RecyclerView.Adapter<CoomingSoonAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_coomingsoon, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val tvTitle = view.findViewById<TextView>(R.id.tv_title)
        private val tvGenre = view.findViewById<TextView>(R.id.tv_genre)
        private val tvRate = view.findViewById<TextView>(R.id.tv_rate)

        private val tvImage = view.findViewById<ImageView>(R.id.iv_poster_film)

        fun bindItem(data:Film, listener: (Film) -> Unit, context: Context) {
            tvTitle.setText(data.judul)
            tvGenre.setText(data.genre)
            tvRate.setText(data.rating)

            Glide.with(context)
                .load(data.poster)
                .into(tvImage)

            itemView.setOnClickListener{
                listener(data)
            }

        }

    }
}
