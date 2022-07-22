package com.emedinaa.kotlinmvvm.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emedinaa.kotlinmvvm.R
import com.emedinaa.kotlinmvvm.di.Injection
import com.emedinaa.kotlinmvvm.extensions.createFactory
import com.emedinaa.kotlinmvvm.model.Museum
import com.emedinaa.kotlinmvvm.viewmodel.MuseumUIState
import com.emedinaa.kotlinmvvm.viewmodel.MuseumViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author Eduardo Medina
 */
private const val TAG = "CONSOLE"

class MuseumActivity : AppCompatActivity() {

    private val viewModel by viewModels<MuseumViewModel> {
        MuseumViewModel(Injection.providerRepository()).createFactory()
    }
    private lateinit var adapter: MuseumAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutError: View
    private lateinit var textViewError: TextView
    private lateinit var layoutEmpty: View
    private lateinit var progressBar: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museum)

        setupUi()
        observerUiState()
    }

    private fun setupUi() {
        recyclerView = findViewById(R.id.recyclerView)
        layoutError = findViewById(R.id.layoutError)
        layoutEmpty = findViewById(R.id.layoutEmpty)
        progressBar = findViewById(R.id.progressBar)
        textViewError = findViewById(R.id.textViewError)

        adapter = MuseumAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun observerUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is MuseumUIState.Success -> {
                            layoutError.visibility = View.GONE
                            layoutEmpty.visibility = View.GONE
                            adapter.update(uiState.museums)
                        }
                        is MuseumUIState.ViewLoading -> {
                            val visibility = if (uiState.visible) View.VISIBLE else View.GONE
                            progressBar.visibility = visibility
                        }
                        is MuseumUIState.Empty -> {
                            layoutEmpty.visibility = View.VISIBLE
                            layoutError.visibility = View.GONE
                        }
                        is MuseumUIState.Error -> {
                            layoutError.visibility = View.VISIBLE
                            layoutEmpty.visibility = View.GONE
                            textViewError.text = "Error ${uiState.exception.toString()}"
                        }
                    }
                }
            }
        }
    }


    //If you require updated data, you can call the method "loadMuseum" here
    override fun onResume() {
        super.onResume()
        viewModel.loadMuseums()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injection.destroy()
    }

}
