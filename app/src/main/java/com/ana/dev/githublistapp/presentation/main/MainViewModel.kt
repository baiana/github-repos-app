package com.ana.dev.githublistapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ana.dev.githublistapp.data.model.*
import com.ana.dev.githublistapp.data.repository.ProjectsRepository
import com.ana.dev.githublistapp.data.response.ProjectResult
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel : ViewModel(), KoinComponent {

    private val repository: ProjectsRepository by inject()
    private val _viewStateLiveData = MutableLiveData<MainViewState>()
    val stateLiveData: LiveData<MainViewState> get() = _viewStateLiveData

    init {
        _viewStateLiveData.value = MainViewState()
    }

    fun getProjectsList() {
        _viewStateLiveData.postValue(startLoading())
        viewModelScope.launch {
            val result = repository.getProjectsList()
            with(result) {
                if (this.isSuccessful) {
                    body()?.takeIf { it.isNotEmpty() }?.let {
                        val convertedList = convertBodyToProjectList(it)
                        changeViewState(displayProjectList(convertedList))
                    }
                } else {
//                    todo tratamento de erro
                }
            }
        }
    }

    private fun convertBodyToProjectList(list: List<ProjectResult>) =
        ArrayList(list.map {
            Project(
                it.id,
                it.name,
                User(it.user.username, it.user.pictureLink),
                it.description ?: "",
                it.url
            )
        })

    private fun changeViewState(newState: MainViewState) {
        _viewStateLiveData.postValue(newState)
    }

}