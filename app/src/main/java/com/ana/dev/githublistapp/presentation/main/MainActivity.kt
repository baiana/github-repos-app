package com.ana.dev.githublistapp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.ana.dev.githublistapp.R
import com.ana.dev.githublistapp.data.model.Project
import com.ana.dev.githublistapp.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by inject()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModelSetup()

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
                    it.projectList?.isNotEmpty() == true -> {
                        recyclerSetup(it.projectList)
                    }
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

    private fun recyclerSetup(projects:ArrayList<Project>){
        //todo implementar
    }
}