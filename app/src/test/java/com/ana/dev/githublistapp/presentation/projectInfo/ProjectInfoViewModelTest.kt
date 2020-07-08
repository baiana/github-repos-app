package com.ana.dev.githublistapp.presentation.projectInfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.ana.dev.githublistapp.data.model.Project
import com.ana.dev.githublistapp.data.model.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import junit.framework.Assert.*
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

@ExperimentalCoroutinesApi
class ProjectInfoViewModelTest() {
    @get: Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProjectInfoViewModel
    private val validProjectMock by lazy {
        Project(
            id = "id",
            name = "mockname",
            description = "desc",
            url = "url",
            user = User("username", "fakeurl")
        )
    }

    @Test
    fun `displayProjectInfo() should change value on projectInfoLiveData, if called with valid parameter`() {
        //Given
        viewModel = spyk(ProjectInfoViewModel())
//        When
//        function is called with non-null parameter
        viewModel.displayProjectInfo(validProjectMock)

//        Then
        assertEquals(viewModel.projectInfoLiveData.value?.projectInfo, validProjectMock)
    }

    @Test
    fun `displayProjectInfo() should return a error on projectInfoLiveData, if called with a null parameter`() {
        //Given
        viewModel = spyk(ProjectInfoViewModel())
//        When
//        function is called with null parameter
        viewModel.displayProjectInfo(null)

//        Then
        assertNotNull(viewModel.projectInfoLiveData.value?.error)
        assertNull(viewModel.projectInfoLiveData.value?.projectInfo)
    }

    @Test
    fun `getUsername() should return username if there is a valid value on projectInfoLiveData`() {
        // Given
        viewModel = spyk(ProjectInfoViewModel())

        every { viewModel.projectInfoLiveData.value } returns ProjectInfoViewState(validProjectMock)

        // When
        // username is requested
        val name = viewModel.getUsername()

        //Then
        assertTrue(name == validProjectMock.user.name)
    }

    @Test
    fun `getUsername() should return blank if there is no value on projectInfoLiveData`() {
        // Given
        viewModel = spyk(ProjectInfoViewModel())

        every { viewModel.projectInfoLiveData.value } returns null

        // When
        // username is requested
        val name = viewModel.getUsername()

        //Then
        assert(name.isBlank())
    }


}