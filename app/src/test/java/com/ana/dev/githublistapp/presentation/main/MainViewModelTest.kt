package com.ana.dev.githublistapp.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ana.dev.githublistapp.data.model.MainViewState
import com.ana.dev.githublistapp.data.model.Project
import com.ana.dev.githublistapp.data.repository.ProjectsRepository
import com.ana.dev.githublistapp.data.response.ProjectBodyResponse
import com.ana.dev.githublistapp.data.response.ProjectResult
import com.ana.dev.githublistapp.data.response.SearchResult
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import retrofit2.Response

@ExperimentalCoroutinesApi
class MainViewModelTest : KoinTest {
    @get: Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(module(override = true) {
            single<ProjectsRepository> {
                mockk(relaxed = true) {
                    coEvery { getProjectsList() } returns responseList
                    coEvery { searchProjectByName(any()) } returns responseSearch
                }
            }
        })
    }

    private lateinit var responseSearch: Response<SearchResult>
    private lateinit var viewModel: MainViewModel
    private lateinit var viewState: MainViewState
    private lateinit var responseList: Response<List<ProjectResult>>

    @Before
    fun init() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        responseSearch = mockk()
        responseList = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
        stopKoin()
    }

    @Test
    fun `clearSelected() should clear selected data and keep projectList on viewState`() {
        viewModel = spyk(MainViewModel())
        val mockList = mockk<ArrayList<Project>>()
        every { viewModel }

    }

}