package com.example.recyclerview

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.databinding.CardLayoutBinding


class CustomAdapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

    val titles = arrayOf("Perro1", "Perro 2", "Perro3", "Perro 4","Perro5", "Perro 6",)
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var image: ImageView
        var title: TextView
        init{
            image = itemView.findViewById(R.id.image)
            title = itemView.findViewById(R.id.title)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text =  titles[position]


    }

    override fun getItemCount(): Int {
        return titles.size
    }

}