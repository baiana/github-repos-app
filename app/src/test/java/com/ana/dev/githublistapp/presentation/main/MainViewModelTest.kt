package com.ana.dev.githublistapp.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ana.dev.githublistapp.R
import com.ana.dev.githublistapp.data.model.MainViewState
import com.ana.dev.githublistapp.data.model.Project
import com.ana.dev.githublistapp.data.repository.ProjectsRepository
import com.ana.dev.githublistapp.data.response.OwnerResponse
import com.ana.dev.githublistapp.data.response.ProjectResult
import com.ana.dev.githublistapp.data.response.SearchResult
import io.mockk.*
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.koin.core.get
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import retrofit2.Response
import retrofit2.http.Body

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
            viewModel { MainViewModel() }
        })
    }

    private lateinit var responseSearch: Response<SearchResult>
    private lateinit var viewModel: MainViewModel
    private lateinit var stateObserver: Observer<MainViewState>

    private lateinit var responseList: Response<List<ProjectResult>>

    @Before
    fun init() {
        Dispatchers.setMain(TestCoroutineDispatcher())

        stateObserver = spyk(Observer { })
        viewModel = get<MainViewModel>().also {
            it.fragmentProjectsStateLiveData.observeForever(stateObserver)
        }

        responseSearch = mockk()
        responseList = mockk(relaxed = true)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
        stopKoin()
    }

    @Test
    fun `getProjectList() should return list of projects if call is successful`() {
        //Given
        val slots = mutableListOf<MainViewState>()
        val mockList = listOf(
            spyk(
                ProjectResult(
                    "23", "And", OwnerResponse(
                        "", "Ansa",
                        "", "234@"
                    ), "", ""
                )
            )
        )
        every { responseList.isSuccessful } returns true
        every { responseList.body() } returns mockList

        //When
        viewModel.getProjectsList()

        //Then
        //Verify that the last two states correspond to loading and returning the list
        verify(exactly = 3) { stateObserver.onChanged(capture(slots)) }
        val (_, load, list) = slots
        assertTrue(load.isLoading)
        assertNotNull(list.projectList)
    }
    @Test
    fun `getProjectList() should return error id corresponding to string if call failed`() {
        //Given
        val slots = mutableListOf<MainViewState>()
        val errorCode = 401 //Forbiden

        every { responseList.isSuccessful } returns false
        every { responseList.code() } returns errorCode

        //When
        viewModel.getProjectsList()

        //Then
        //Verify that the last two states correspond to loading and returning the list
        verify(atLeast = 2) { stateObserver.onChanged(capture(slots)) }
        val error = slots.last()

        assert(error.errorId == R.string.error_forbidden_401)
    }

}