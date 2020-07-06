package com.ana.dev.githublistapp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.ana.dev.githublistapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ana.dev.githublistapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModelSetup()
        val navController = Navigation.findNavController(this, R.id.navHost)


    }

    private fun viewModelSetup() {
        viewModel.stateLiveData.observe(this, Observer {
            it?.let {
                when {
                    it.isLoading -> {
                        displayLoading()
                    }
                    it.error.isNotBlank() -> {
                        displayError(it.error)
                    }
                    //   it.projectList?.isNotEmpty() == true -> {
                    //      recyclerSetup(it.projectList)
                    //}
//                    todo configurações do search e do recycler

                }
            }
        })

    }

    private fun displayLoading() {
//        todo implementar
    }

    private fun displayError(error: String) {
//        todo implementar
    }


}