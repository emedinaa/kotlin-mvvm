package com.emedinaa.kotlinmvvm.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.emedinaa.kotlinmvvm.R
import com.emedinaa.kotlinmvvm.model.Museum

/**
 * @author Eduardo Medina
 */
class MuseumDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_museum_detail, container, false)
    }

    private fun populate(museum: Museum, view: View) {
        view.findViewById<TextView>(R.id.textView).text = museum.name
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        Glide.with(imageView.context).load(museum.photo).into(imageView)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Museum>("MUSEUM")?.let {
            populate(it, view)
        }
    }
}