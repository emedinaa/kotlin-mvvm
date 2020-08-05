package com.emedinaa.kotlinmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.emedinaa.kotlinmvvm.R
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.model.MuseumRepository
import com.emedinaa.kotlinmvvm.viewmodel.MuseumViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_museum.*
import kotlinx.android.synthetic.main.layout_error.*

/**
 * @author : Eduardo Medina
 */
@AndroidEntryPoint
class MuseumActivity : AppCompatActivity() {

    private val museumViewModel: MuseumViewModel by viewModels()
    private lateinit var adapter: MuseumAdapter

    companion object {
        const val TAG= "CONSOLE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museum)

        setupViewModel()
        setupUI()
    }

    //ui
    private fun setupUI(){
        adapter= MuseumAdapter(emptyList())
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter= adapter
    }

    //viewmodel
    private fun setupViewModel(){
        museumViewModel.museums.observe(this,renderMuseums)

        museumViewModel.isViewLoading.observe(this,isViewLoadingObserver)
        museumViewModel.onMessageError.observe(this,onMessageErrorObserver)
        museumViewModel.isEmptyList.observe(this,emptyListObserver)
    }

    //observers
    private val renderMuseums= Observer<List<Museum>> {
        Log.v(TAG, "data updated $it")
        layoutError.visibility=View.GONE
        layoutEmpty.visibility=View.GONE
        adapter.update(it)
    }

    private val isViewLoadingObserver= Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility=if(it)View.VISIBLE else View.GONE
        progressBar.visibility= visibility
    }

    private val onMessageErrorObserver= Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        layoutError.visibility=View.VISIBLE
        layoutEmpty.visibility=View.GONE
        textViewError.text= "Error $it"
    }

    private val emptyListObserver= Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        layoutEmpty.visibility=View.VISIBLE
        layoutError.visibility=View.GONE
    }

     //If you require updated data, you can call the method "loadMuseum" here
     override fun onResume() {
        super.onResume()
         museumViewModel.loadMuseums()
     }
}
