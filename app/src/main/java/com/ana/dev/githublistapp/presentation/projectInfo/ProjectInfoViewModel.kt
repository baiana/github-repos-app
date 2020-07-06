package com.ana.dev.githublistapp.presentation.projectInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ana.dev.githublistapp.data.model.Project

class ProjectInfoViewModel() : ViewModel() {

    private val _projectInfo = MutableLiveData<Project>()
    val projectInfoLiveData: LiveData<Project> get() = _projectInfo

    fun displayProjectInfo(projectInfo: Project?) {
        projectInfo?.let {
            _projectInfo.postValue(projectInfo)
        } ?: run {
            //todo fechar activity com erro
        }
    }

}