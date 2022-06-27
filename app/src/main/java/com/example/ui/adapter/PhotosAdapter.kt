package com.example.ui.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.data.model.Albums
import com.example.data.model.Photos
import com.example.ui.R
import okhttp3.HttpUrl

class PhotosAdapter(var originList: MutableList<Photos>,var photosList: MutableList<Photos>, val context: Context) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>(), Filterable {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = photosList[position].title.toString()
        //Glide.with(context).load(photosList[position].url).centerCrop().placeholder(R.drawable.ic_error).into(holder.imageView)
        holder.imageView.load(HttpUrl.get(photosList[position].url)) {
            size(150, 150)
        }
    }

    override fun getItemCount(): Int {
        return photosList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val title: TextView = itemView.findViewById(R.id.tv_imgTitle)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(photos: MutableList<Photos>) {
        originList.clear()
        photosList.clear()
        originList.addAll(photos)
        photosList.addAll(photos)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charString: String = constraint.toString().lowercase()
                photosList = if (charString.isEmpty()) {
                    originList
                } else {
                    val newList = ArrayList<Photos>()
                    for (row in originList) {
                        if (row.title!!.lowercase()
                                .contains(constraint.toString(),false)
                            || row.url!!.lowercase()
                                .contains(constraint.toString(),false)){
                            newList.add(row)
                        }
                    }
                    newList
                }

                val filterResults = FilterResults()
                filterResults.values = photosList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                photosList = results.values as MutableList<Photos>
                notifyDataSetChanged()
            }

        }
    }
}