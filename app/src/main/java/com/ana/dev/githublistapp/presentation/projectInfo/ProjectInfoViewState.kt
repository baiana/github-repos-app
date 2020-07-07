package com.ana.dev.githublistapp.presentation.projectInfo

import androidx.lifecycle.ViewModel
import com.ana.dev.githublistapp.data.model.Project
import java.lang.Error

data class ProjectInfoViewState internal constructor(val projectInfo: Project? = null, val error: Int? = null)

fun ProjectInfoViewModel.setProjectInfo(projectInfo: Project) = ProjectInfoViewState(projectInfo = projectInfo)
fun ProjectInfoViewModel.setErrorId(error: Int) = ProjectInfoViewState(error = error)