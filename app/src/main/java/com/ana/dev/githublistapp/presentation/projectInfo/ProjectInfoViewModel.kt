package com.ana.dev.githublistapp.presentation.projectInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ana.dev.githublistapp.R
import com.ana.dev.githublistapp.data.model.Project

class ProjectInfoViewModel() : ViewModel() {

    private val _viewState = MutableLiveData<ProjectInfoViewState>()
    val projectInfoLiveData: LiveData<ProjectInfoViewState> get() = _viewState

    fun displayProjectInfo(projectInfo: Project?) {
        _viewState.postValue(projectInfo?.let {
            setProjectInfo(projectInfo)
        } ?: run {
            setErrorId(R.string.app_name)
        })
    }

    fun getUsername(): String {
        return projectInfoLiveData.value?.projectInfo?.user?.name ?: ""
    }

}