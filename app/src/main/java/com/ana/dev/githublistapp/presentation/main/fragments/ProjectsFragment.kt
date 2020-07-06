package com.ana.dev.githublistapp.presentation.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ana.dev.githublistapp.data.model.Project
import com.ana.dev.githublistapp.databinding.FragmentProjectsBinding
import com.ana.dev.githublistapp.presentation.main.MainViewModel
import com.ana.dev.githublistapp.presentation.main.ProjectListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProjectsFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: FragmentProjectsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProjectsBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search()
    }

    private fun setupViewModel() {
        viewModel.fragmentProjectsStateLiveData.observe(this, Observer {
            when {
                it.searchData != null -> {
                    handleSearchResult(it.searchData)
                }
                it.projectList?.isNotEmpty() == true -> {
                    recyclerSetup(it.projectList)
                }
                it.isLoading -> {
                    displayLoading()
                }
                it.error.isNotBlank() -> {
                    displayError(it.error)
                }
            }

        })
        viewModel.getProjectsList()
    }

    private fun handleSearchResult(searchData: ArrayList<Project>) {
        recyclerSetup(searchData)

    }

    private fun displayError(error: String) {
        binding.loading.hide()
        TODO("Not yet implemented")
    }

    private fun displayLoading() {
        binding.loading.show()

    }


    private fun recyclerSetup(projects: ArrayList<Project>) {
        binding.loading.hide()
        if (binding.projectsRV.adapter == null) {
            with(binding.projectsRV) {
                this.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                this.adapter =
                    ProjectListAdapter(
                        projects,
                        resources
                    )
            }

        } else {
            val x = 0
        }
        //todo implementar swap
    }

    private fun search() {
        binding.projectSV.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        viewModel.searchProjectsByName(it)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.takeIf { it.length > 4 }?.let {
                        viewModel.searchProjectsByName(it)
                    }
                    return true
                }
            })

            setOnCloseListener {
                viewModel.resetSearch()
                true
            }
        }
    }
}