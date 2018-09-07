package com.anantadwi13.footballclub

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.jetbrains.anko.sdk25.coroutines.onClick

class RecyclerViewAdapter(private val context: Context, private val items: List<Club>, private val listener: (Club)-> Unit) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyclerview,parent,false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)
    }

    class ViewHolder(view:View): RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ImageView>(R.id.image)
        val nama = view.findViewById<TextView>(R.id.text)

        fun bindItem(item: Club, listener: (Club) -> Unit){
            nama.text = item.nama
            Glide.with(itemView.context).load(item.image).into(image)
            itemView.setOnClickListener{
                listener(item)
            }
        }
    }
}