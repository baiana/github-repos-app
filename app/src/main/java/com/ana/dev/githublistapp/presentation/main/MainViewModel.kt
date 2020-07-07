package com.ana.dev.githublistapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ana.dev.githublistapp.data.model.*
import com.ana.dev.githublistapp.data.repository.ProjectsRepository
import com.ana.dev.githublistapp.data.response.ProjectResult
import com.ana.dev.githublistapp.utilities.getErrorMessageByCode
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainViewModel : ViewModel(), KoinComponent {

    private val repository: ProjectsRepository by inject()
    private val _viewStateLiveData = MutableLiveData<MainViewState>()
    val stateLiveData: LiveData<MainViewState> get() = _viewStateLiveData

    private val _fragmentProjectsStateLiveData = MutableLiveData<MainViewState>()
    val fragmentProjectsStateLiveData: LiveData<MainViewState> get() = _fragmentProjectsStateLiveData

    init {
        _viewStateLiveData.value = MainViewState()
        _fragmentProjectsStateLiveData.value = MainViewState()
    }

    fun getProjectsList() {
        _viewStateLiveData.postValue(startLoading())
        viewModelScope.launch {
            val result = repository.getProjectsList()
            with(result) {
                if (this.isSuccessful) {
                    body()?.takeIf { it.isNotEmpty() }?.let {
                        val convertedList = convertBodyToProjectList(it)
                        _fragmentProjectsStateLiveData.postValue(displayProjectList(convertedList))
                    }
                } else {
                    handleError(this.code())
                }
            }
        }
    }

    private fun handleError(errorCode: Int) {
        val errorId = getErrorMessageByCode(errorCode)
        _fragmentProjectsStateLiveData.postValue(displayError(errorId))
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


    private fun searchWithAPI(query: String) {
        val currentList = fragmentProjectsStateLiveData.value?.projectList ?: ArrayList()
        _fragmentProjectsStateLiveData.postValue(startLoading(currentList))
        viewModelScope.launch {
            with(repository.searchProjectByName(query)) {
                if (isSuccessful) {
                    body()?.result?.let {
                        _fragmentProjectsStateLiveData.postValue(
                            displaySearchResult(
                                convertBodyToProjectList(it), currentList
                            )
                        )
                    } ?: run {
                        //todo handle error lista nula

                    }
                } else {
                    handleError(this.code())
                }
            }
        }

    }

    fun searchProjectsByName(query: String, submitted: Boolean = false) {
        if (query.isNotBlank() && (submitted || query.length > 4)) {
            searchWithAPI(query)
        } else {
            resetSearch()
        }

    }

    private fun localSearch(query: String) {
        val currentList = fragmentProjectsStateLiveData.value?.projectList ?: ArrayList()
        val result =
            fragmentProjectsStateLiveData.value?.projectList?.filter { it.name.startsWith(query) } as ArrayList
        _fragmentProjectsStateLiveData.postValue(displaySearchResult(result, currentList))
    }

    fun resetSearch() {
        val regularList = fragmentProjectsStateLiveData.value?.projectList ?: ArrayList()
        _fragmentProjectsStateLiveData.postValue(displayProjectList(regularList))
    }

    fun displayProjectInfo(project: Project) {
        _fragmentProjectsStateLiveData.postValue(displaySelectedInfo(project))
    }

    fun clearSelected() {
        _fragmentProjectsStateLiveData.postValue(clear())

    }


}