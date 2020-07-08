package com.ana.dev.githublistapp.data.model

import com.ana.dev.githublistapp.presentation.projectInfo.ProjectInfoViewModel

data class ProjectInfoViewState internal constructor(
    val projectInfo: Project? = null,
    val error: Int? = null
)

fun ProjectInfoViewModel.setProjectInfo(projectInfo: Project) =
    ProjectInfoViewState(projectInfo = projectInfo)

fun ProjectInfoViewModel.setErrorId(error: Int) =
    ProjectInfoViewState(error = error)