package com.ana.dev.githublistapp.di

import com.ana.dev.githublistapp.data.repository.ProjectsRepository
import com.ana.dev.githublistapp.data.repository.ProjectsRepositoryImp
import com.ana.dev.githublistapp.presentation.main.MainViewModel
import com.ana.dev.githublistapp.presentation.projectInfo.ProjectInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel() }
    single<ProjectsRepository> { ProjectsRepositoryImp() }
}
val displayInfoModule = module {
    viewModel { ProjectInfoViewModel() }
}