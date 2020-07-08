package com.ana.dev.githublistapp.presentation.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ana.dev.githublistapp.R
import com.ana.dev.githublistapp.data.model.MainViewState
import com.ana.dev.githublistapp.data.model.Project
import com.ana.dev.githublistapp.data.model.User
import com.ana.dev.githublistapp.data.repository.ProjectsRepository
import com.ana.dev.githublistapp.data.response.OwnerResponse
import com.ana.dev.githublistapp.data.response.ProjectResult
import com.ana.dev.githublistapp.data.response.SearchResult
import io.mockk.*
import junit.framework.Assert.*
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
            viewModel { spyk(MainViewModel()) }
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

        responseSearch = mockk(relaxed = true)
        responseList = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
        stopKoin()
    }

    //getProjectList() tests
    @Test
    fun `getProjectList() should return list of projects if call is successful`() {
        //Given
        val slots = mutableListOf<MainViewState>()
        every { responseList.isSuccessful } returns true
        every { responseList.body() } returns getMockProjectResultList()

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
        verify(exactly = 3) { stateObserver.onChanged(capture(slots)) }
        val error = slots.last()

        assert(error.errorId == R.string.error_forbidden_401)
    }

    //searchProjectsByName() tests
    @Test
    fun `searchProjectsByName() should return search result as list, if called with valid query`() {
        val slots = mutableListOf<MainViewState>()

        every { responseSearch.isSuccessful } returns true
        every { responseSearch.body() } returns SearchResult(4, getMockProjectResultList())

        viewModel.searchProjectsByName("project")

        verify(exactly = 3) { stateObserver.onChanged(capture(slots)) }
        val (_, _, result) = slots

        assert(result.searchData?.isNotEmpty() == true)
    }

    @Test
    fun `searchProjectsByName() should return current project list, if called with blank query`() {
        val slots = mutableListOf<MainViewState>()
        every { viewModel.fragmentProjectsStateLiveData.value?.projectList } returns getMockProjectArray()

        viewModel.searchProjectsByName("")

        verify(exactly = 2) { stateObserver.onChanged(capture(slots)) }
        val (_, result) = slots

        assert(result.projectList?.isNotEmpty() == true)
        assertNull(result.searchData)
    }

    @Test
    fun `searchProjectsByName() should return error when api call failed`() {
        val slots = mutableListOf<MainViewState>()
        val errorCode = 404 //Not Found
        every { responseSearch.isSuccessful } returns false
        every { responseSearch.code() } returns errorCode

        viewModel.searchProjectsByName("failed")

        verify(exactly = 3) { stateObserver.onChanged(capture(slots)) }
        val (_, _, data) = slots
        assertEquals(data.errorId, R.string.error_not_found_404)
    }

    //resetSearch() tests
    @Test
    fun `resetSearch() should display current project list when regular list is not empty`() {
        val slots = mutableListOf<MainViewState>()
        every { viewModel.fragmentProjectsStateLiveData.value?.isLoading } returns true
        every { viewModel.fragmentProjectsStateLiveData.value?.projectList } returns arrayListOf(
            Project("id", "name", User("username", "dwwwdw"), "dwdd", "wdfwsdf")
        )

        viewModel.resetSearch()

        verify(exactly = 2) { stateObserver.onChanged(capture(slots)) }

        val (_, final) = slots
        assertFalse(final.projectList.isNullOrEmpty())
    }

    @Test
    fun `resetSearch() should display api project list when regular list is empty`() {
        val slots = mutableListOf<MainViewState>()
        every { responseList.isSuccessful } returns true
        every { responseList.body() } returns getMockProjectResultList()
        every { viewModel.fragmentProjectsStateLiveData.value?.projectList } returns null

        viewModel.resetSearch()

        verify(exactly = 3) { stateObserver.onChanged(capture(slots)) }
        val (_, _, final) = slots
        assert(final.projectList?.isNotEmpty() == true)
    }

    //clearSelected() tests
    @Test
    fun `clearSelected() should change viewState value to displayProjectList`() {
        val slots = mutableListOf<MainViewState>()
        every { viewModel.fragmentProjectsStateLiveData.value?.selected } returns mockk()
        every { viewModel.fragmentProjectsStateLiveData.value?.projectList } returns getMockProjectArray()

        viewModel.clearSelected()

        verify(exactly = 2) { stateObserver.onChanged(capture(slots)) }
        val (_, cleared) = slots
        assertNull(cleared.selected)
        assert(cleared.projectList?.isNotEmpty() == true)
    }

//    displayProjectInfo tests

    @Test
    fun `displayProjectInfo() should display info passed as paramether to viewState as selected `() {
        val slots = mutableListOf<MainViewState>()

        viewModel.displayProjectInfo(mockk())

        verify(exactly = 2) {
            stateObserver.onChanged(capture(slots))
        }
        val (_, displayInfo) = slots
        assertNotNull(displayInfo.selected)
    }

    private fun getMockProjectArray() = arrayListOf(
        Project("id", "name", User("username", "dwwwdw"), "dwdd", "wdfwsdf")
    )

    private fun getMockProjectResultList() = listOf(
        spyk(
            ProjectResult(
                "23", "And", OwnerResponse(
                    "", "Ansa",
                    "", "234@"
                ), "", ""
            )
        )
    )

}