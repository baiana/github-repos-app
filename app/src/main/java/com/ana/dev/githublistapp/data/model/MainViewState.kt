package com.ana.dev.githublistapp.data.model

import com.ana.dev.githublistapp.presentation.main.MainViewModel

data class MainViewState internal constructor(
    val isLoading: Boolean = false,
    val error: String = "",
    val searchData: ArrayList<Project>? = null,
    val projectList: ArrayList<Project>? = null
)

fun MainViewModel.displayProjectList(result: ArrayList<Project>) =
    MainViewState(isLoading = false, projectList = result)

fun MainViewModel.displaySearchResult(result: ArrayList<Project>, currentList: ArrayList<Project>) =
    MainViewState(isLoading = false, searchData = result, projectList = currentList)

fun MainViewModel.startLoading() = MainViewState(isLoading = true)

fun MainViewModel.clear() = MainViewState()
fun MainViewModel.displayError(error: String) = MainViewState(isLoading = false, error = error)