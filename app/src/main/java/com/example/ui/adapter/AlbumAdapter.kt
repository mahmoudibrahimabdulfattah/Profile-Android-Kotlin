package com.example.ui.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.Albums
import com.example.ui.R


class AlbumAdapter(var albumsList: MutableList<Albums>, val itemClick: OnItemClick) :
    RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {
    private var albumId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.album_items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = albumsList[position].title.toString()

        holder.textView.setOnClickListener {
            albumId = albumsList[position].id
            itemClick.onItemClicked(albumsList[position], it.id, position, albumsList[position].title.toString(), albumId!!)
        }
    }

    override fun getItemCount(): Int {
        return albumsList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.tvTitle)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(list: MutableList<Albums>) {
        albumsList.clear()
        albumsList.addAll(list)
        notifyDataSetChanged()
    }

    //interface item listener
    interface OnItemClick {
        fun onItemClicked(album: Albums, viewID: Int, position: Int, title: String, albumId: Int)
    }
}