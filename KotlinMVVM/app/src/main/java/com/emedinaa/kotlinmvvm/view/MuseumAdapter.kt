package com.emedinaa.kotlinmvvm.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.emedinaa.kotlinmvvm.databinding.RowMuseumBinding
import com.emedinaa.kotlinmvvm.model.Museum

class MuseumAdapter(private var museums:List<Museum>):RecyclerView.Adapter<MuseumAdapter.MViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val itemBinding = RowMuseumBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MViewHolder(itemBinding)
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

    class MViewHolder(itemBinding: RowMuseumBinding) : RecyclerView.ViewHolder(itemBinding.root){
        private val textViewName:TextView = itemBinding.textViewName
        private val imageView:ImageView = itemBinding.imageView

        fun bind(museum:Museum){
            textViewName.text = museum.name
            Glide.with(imageView.context).load(museum.photo).into(imageView)
        }
    }
}