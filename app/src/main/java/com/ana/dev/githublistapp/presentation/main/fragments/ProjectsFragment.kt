package com.ana.dev.githublistapp.presentation.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ana.dev.githublistapp.data.model.Project
import com.ana.dev.githublistapp.databinding.ActivityMainBinding
import com.ana.dev.githublistapp.databinding.FragmentProjectsBinding
import com.ana.dev.githublistapp.presentation.main.MainActivity
import com.ana.dev.githublistapp.presentation.main.MainViewModel
import com.ana.dev.githublistapp.presentation.main.ProjectListAdapter
import com.ana.dev.githublistapp.utilities.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProjectsFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentProjectsBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchConfig()
    }

    private fun setupViewModel() {
        viewModel.fragmentProjectsStateLiveData.observe(this, Observer {
            when {
                it.isLoading -> {
                    displayLoading()
                }
                it.errorId != 0 -> {
                    displayError(it.errorId)
                }

                it.searchData != null -> {
                    handleSearchResult(it.searchData)
                }
                it.projectList?.isNotEmpty() == true -> {
                    recyclerSetup(it.projectList)
                }
                it.selected != null -> {
                    (activity as MainActivity).displayItemInfo(it.selected)
                    viewModel.clearSelected()
                }

            }

        })
        viewModel.getProjectsList()
    }

    private fun handleSearchResult(searchData: ArrayList<Project>) {
        if (searchData.isNotEmpty()) {
            recyclerSetup(searchData)
        } else {
            displayEmptySearchScreen()
        }
    }

    private fun displayEmptySearchScreen() {
        binding.emptyListGP.visible()
        binding.projectsRV.gone()
        binding.loadingPB.gone()
    }


    private fun displayError(error: Int) {
        hideLoading()
        activity?.displayError(resources.getString(error))
    }

    private fun displayLoading() {
        binding.loadingPB.visible()
    }

    private fun hideLoading() {
        binding.loadingPB.gone()
    }

    private fun displayProjectInfo(project: Project) {
        viewModel.displayProjectInfo(project)
    }

    private fun recyclerSetup(projects: ArrayList<Project>) {
        if (binding.projectsRV.adapter == null) {
            with(binding.projectsRV) {
                this.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                this.adapter = ProjectListAdapter(projects, resources).apply {
                    detailsClickListener = object : ProjectListAdapter.OnProjectClickListener {
                        override fun onClick(project: Project) {
                            displayProjectInfo(project)
                        }
                    }
                }
            }
        } else {
            (binding.projectsRV.adapter as ProjectListAdapter).swap(projects)
            binding.projectsRV.smoothScrollToPosition(0)
        }
        displayRecycler()
    }

    private fun displayRecycler() {
        hideLoading()
        binding.emptyListGP.gone()
        binding.projectsRV.visible()
    }

    private fun searchConfig() {
        binding.projectSV.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        viewModel.searchProjectsByName(it, submitted = true)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val search = newText ?: ""
                    viewModel.searchProjectsByName(search)

                    return true
                }
            })

            setOnCloseListener {
                resetSearchView()
                true
            }
        }
    }

    private fun resetSearchView() {
        binding.projectSV.setQuery(null, false)
        binding.projectSV.clearFocus()
        viewModel.resetSearch()
    }
}

