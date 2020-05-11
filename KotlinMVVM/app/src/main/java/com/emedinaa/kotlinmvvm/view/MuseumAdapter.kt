package com.emedinaa.kotlinmvvm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emedinaa.kotlinmvvm.R
import com.emedinaa.kotlinmvvm.model.Museum
import kotlinx.android.synthetic.main.row_museum.view.*

class MuseumAdapter(private var museums:List<Museum>):RecyclerView.Adapter<MuseumAdapter.MViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_museum, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        //render
        vh.bind(museums[position])
    }

    override fun getItemCount(): Int {
        return museums.size
    }

    fun update(data:List<Museum>){
        museums= data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val textViewName:TextView = view.textViewName
        private val imageView:ImageView = view.imageView
        fun bind(museum:Museum){
            textViewName.text = museum.name
            Glide.with(imageView.context).load(museum.photo).into(imageView)
        }
    }
}