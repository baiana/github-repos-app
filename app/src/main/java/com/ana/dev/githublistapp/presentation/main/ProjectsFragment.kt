package com.ana.dev.githublistapp.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ana.dev.githublistapp.R
import com.ana.dev.githublistapp.data.model.Project
import com.ana.dev.githublistapp.databinding.FragmentProjectsBinding
import org.koin.android.ext.android.inject
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

    private fun setupViewModel() {
        viewModel.stateLiveData.observe(this, Observer {
            when {
                it.projectList?.isNotEmpty() == true -> {
                    recyclerSetup(it.projectList)
                }
            }

        })
        viewModel.getProjectsList()
    }

    private fun recyclerSetup(projects: ArrayList<Project>) {
        if (binding.projectsRV.adapter == null) {
            with(binding.projectsRV) {
                this.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                this.adapter = ProjectListAdapter(projects, resources)
            }

        } else {

        }
        //todo implementar
    }
}