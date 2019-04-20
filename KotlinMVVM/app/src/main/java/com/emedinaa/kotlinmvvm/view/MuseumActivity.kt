package com.emedinaa.kotlinmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.emedinaa.kotlinmvvm.R
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.viewmodel.MuseumViewModel
import kotlinx.android.synthetic.main.activity_museum.*
import kotlinx.android.synthetic.main.layout_error.*

class MuseumActivity : AppCompatActivity() {

    private lateinit var viewModel: MuseumViewModel
    private lateinit var adapter: MuseumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museum)

        //viewmodel
        setUpViewModel()

        //ui
        adapter= MuseumAdapter(viewModel.museums.value?: emptyList())
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.adapter= adapter

        Log.v("CONSOLE","savedInstanceState $savedInstanceState")

        //Consider this, if you need to call the service once when activity was created.
        /*if(savedInstanceState==null){
            viewModel.loadMuseums()
        }*/
    }

    private fun setUpViewModel(){
        viewModel = ViewModelProviders.of(this).get(MuseumViewModel::class.java)
        /*viewModel.museums.observe(this,
            Observer<List<Museum>> {
                Log.v("CONSOLE", "data updated $it")
                adapter.update(it)
            })*/
        viewModel.museums.observe(this,renderMuseums)
        viewModel.isViewLoading.observe(this,isViewLoadingObserver)
        viewModel.onMessageError.observe(this,onMessageErrorObserver)
    }

    //observers
    private val renderMuseums= Observer<List<Museum>> {
        Log.v("CONSOLE", "data updated $it")
        layoutError.visibility=View.GONE
        adapter.update(it)
    }

    private val isViewLoadingObserver= Observer<Boolean> {
        Log.v("CONSOLE", "isViewLoading $it")
        val visibility=if(it)View.VISIBLE else View.GONE
        progressBar.visibility= visibility
    }

    private val onMessageErrorObserver= Observer<Any> {
        Log.v("CONSOLE", "onMessageError $it")
        layoutError.visibility=View.VISIBLE
        textViewError.text= "Error $it"
    }

    private val emptyListObserver= Observer<Boolean> {}

    /**
     * If you require updated data, you can call the method "loadMuseum" here
     */
    override fun onResume() {
        super.onResume()
        //viewModel.loadMuseums()
    }
}
