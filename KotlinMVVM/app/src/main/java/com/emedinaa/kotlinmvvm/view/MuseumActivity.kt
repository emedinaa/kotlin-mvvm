package com.emedinaa.kotlinmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.emedinaa.kotlinmvvm.databinding.ActivityMuseumBinding
import com.emedinaa.kotlinmvvm.di.Injection
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.viewmodel.MuseumViewModel
import com.emedinaa.kotlinmvvm.viewmodel.ViewModelFactory

class MuseumActivity : AppCompatActivity() {

    private lateinit var viewModel: MuseumViewModel
    private lateinit var adapter: MuseumAdapter
    private lateinit var binding:ActivityMuseumBinding

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
        binding = ActivityMuseumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupUI()
    }

    //ui
    private fun setupUI(){
        adapter= MuseumAdapter(viewModel.museums.value?: emptyList())
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
        binding.recyclerView.adapter= adapter
    }

    //viewmodel
    /**
        //Consider this if ViewModel class don't require parameters.
        viewModel = ViewModelProviders.of(this).get(MuseumViewModel::class.java)

        //if you require any parameters to  the ViewModel consider use a ViewModel Factory
        viewModel = ViewModelProviders.of(this,ViewModelFactory(Injection.providerRepository())).get(MuseumViewModel::class.java)

        //Anonymous observer implementation
        viewModel.museums.observe(this,Observer<List<Museum>> {
            Log.v("CONSOLE", "data updated $it")
            adapter.update(it)
        })
     */
    private fun setupViewModel(){
        viewModel = ViewModelProviders.of(this,ViewModelFactory(Injection.providerRepository())).get(MuseumViewModel::class.java)
        viewModel.museums.observe(this,renderMuseums)

        viewModel.isViewLoading.observe(this,isViewLoadingObserver)
        viewModel.onMessageError.observe(this,onMessageErrorObserver)
        viewModel.isEmptyList.observe(this,emptyListObserver)
    }

    //observers
    private val renderMuseums= Observer<List<Museum>> {
        Log.v(TAG, "data updated $it")
        binding.layoutError.root.visibility=View.GONE
        binding.layoutEmpty.root.visibility=View.GONE
        adapter.update(it)
    }

    private val isViewLoadingObserver= Observer<Boolean> {
        Log.v(TAG, "isViewLoading $it")
        val visibility=if(it)View.VISIBLE else View.GONE
        binding.progressBar.visibility= visibility
    }

    private val onMessageErrorObserver= Observer<Any> {
        Log.v(TAG, "onMessageError $it")
        binding.layoutError.root.visibility=View.VISIBLE
        binding.layoutEmpty.root.visibility=View.GONE
        binding.layoutError.textViewError.text= "Error $it"
    }

    private val emptyListObserver= Observer<Boolean> {
        Log.v(TAG, "emptyListObserver $it")
        binding.layoutEmpty.root.visibility=View.VISIBLE
        binding.layoutError.root.visibility=View.GONE
    }

     //If you require updated data, you can call the method "loadMuseum" here
     override fun onResume() {
        super.onResume()
        viewModel.loadMuseums()
     }

}
