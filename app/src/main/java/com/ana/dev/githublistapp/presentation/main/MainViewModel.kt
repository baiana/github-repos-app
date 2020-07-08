package com.ana.dev.githublistapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ana.dev.githublistapp.data.model.*
import com.ana.dev.githublistapp.data.repository.ProjectsRepository
import com.ana.dev.githublistapp.data.response.ProjectResult
import com.ana.dev.githublistapp.utilities.convertToProjectArray
import com.ana.dev.githublistapp.utilities.getErrorMessageByCode
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel : ViewModel(), KoinComponent {

    private val repository: ProjectsRepository by inject()

    private val _fragmentProjectsStateLiveData = MutableLiveData<MainViewState>()
    val fragmentProjectsStateLiveData: LiveData<MainViewState> get() = _fragmentProjectsStateLiveData

    init {
        _fragmentProjectsStateLiveData.value = MainViewState()
    }

    fun getProjectsList() {
        changeViewStateValue(startLoading())
        viewModelScope.launch {
            val result = repository.getProjectsList()
            with(result) {
                if (this.isSuccessful) {
                    body()?.takeIf { it.isNotEmpty() }?.let {
                        val convertedList = it.convertToProjectArray()
                        changeViewStateValue(displayProjectList(convertedList))
                    }
                } else {
                    handleError(this.code())
                }
            }
        }
    }

    private fun handleError(errorCode: Int) {
        val errorId = getErrorMessageByCode(errorCode)
        changeViewStateValue(displayError(errorId))
    }

    private fun searchWithAPI(query: String) {
        val currentList = fragmentProjectsStateLiveData.value?.projectList ?: ArrayList()
        changeViewStateValue(startLoading(currentList))
        viewModelScope.launch {
            with(repository.searchProjectByName(query)) {
                if (isSuccessful) {
                    body()?.result?.let {
                        changeViewStateValue(
                            displaySearchResult(
                                it.convertToProjectArray(), currentList
                            )
                        )
                    } ?: run {
                        changeViewStateValue(
                            displaySearchResult(
                                ArrayList(),
                                currentList
                            )
                        )
                    }
                } else {
                    handleError(this.code())
                }
            }
        }
    }

    fun searchProjectsByName(query: String, submitted: Boolean = false) {
        if (query.isNotBlank() && (submitted || query.length > 3)) {
            searchWithAPI(query)
        } else {
            resetSearch()
        }
    }

    fun resetSearch() {
        val regularList = fragmentProjectsStateLiveData.value?.projectList ?: ArrayList()
        if (regularList.size == 0) {
            getProjectsList()
        } else {
            changeViewStateValue(displayProjectList(regularList))
        }
    }

    fun displayProjectInfo(project: Project) {
        changeViewStateValue(displaySelectedInfo(project))
    }

    private fun changeViewStateValue(newValue: MainViewState) {
        _fragmentProjectsStateLiveData.postValue(newValue)
    }

    fun clearSelected() {
        changeViewStateValue(
            displayProjectList(
                fragmentProjectsStateLiveData.value?.projectList ?: ArrayList()
            )
        )
    }


}