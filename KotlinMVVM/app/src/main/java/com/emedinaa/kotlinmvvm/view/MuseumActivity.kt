package com.emedinaa.kotlinmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.emedinaa.kotlinmvvm.R
import com.emedinaa.kotlinmvvm.di.Injection
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.viewmodel.MuseumViewModel
import kotlinx.android.synthetic.main.activity_museum.*
import kotlinx.android.synthetic.main.layout_error.*

class MuseumActivity : AppCompatActivity() {

    private lateinit var viewModel: MuseumViewModel
    private lateinit var adapter: MuseumAdapter

    companion object {
        const val TAG= "CONSOLE"
    }

    /**
     //Consider this, if you need to call the service once when activity was created.
        Log.v(TAG,"savedInstanceState $savedInstanceState")
        if(savedInstanceState==null){
            viewModel.loadMuseums()
        }
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museum)

        setupViewModel()
        setupUI()
    }

    //ui
    private fun setupUI(){
        adapter= MuseumAdapter(viewModel.museums.value?: emptyList())
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter= adapter
    }

    //viewmodel
    /**
        //Consider this if ViewModel class don't require parameters.
        viewModel = ViewModelProviders.of(this).get(MuseumViewModel::class.java)

        //if you require any parameters to  the ViewModel consider use a ViewModel Factory
        //viewModel = ViewModelProviders.of(this,ViewModelFactory(Injection.providerRepository())).get(MuseumViewModel::class.java)
        viewModel = ViewModelProvider(this,Injection.provideViewModelFactory()).get(MuseumViewModel::class.java)

        //Anonymous observer implementation
        viewModel.museums.observe(this,Observer<List<Museum>> {
            Log.v("CONSOLE", "data updated $it")
            adapter.update(it)
        })
     */
    private fun setupViewModel(){
        viewModel = ViewModelProvider(this,Injection.provideViewModelFactory()).get(MuseumViewModel::class.java)
        //viewModel = ViewModelProvider(this,ViewModelFactory(Injection.providerRepository())).get(MuseumViewModel::class.java)
        //viewModel = ViewModelProviders.of(this,ViewModelFactory(Injection.providerRepository())).get(MuseumViewModel::class.java)
        viewModel.museums.observe(this,renderMuseums)

        viewModel.isViewLoading.observe(this,isViewLoadingObserver)
        viewModel.onMessageError.observe(this,onMessageErrorObserver)
        viewModel.isEmptyList.observe(this,emptyListObserver)
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
        viewModel.loadMuseums()
     }

}
