package com.ana.dev.githublistapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ana.dev.githublistapp.data.repository.ProjectsRepository
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel : ViewModel(), KoinComponent {

    private val repository: ProjectsRepository by inject()


    fun getList() {
        viewModelScope.launch {
            val x =repository.getProjectsList()
            with(x){
                val esdfsv = this.isSuccessful
            }
        }
    }
}