package com.ana.dev.githublistapp.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.ana.dev.githublistapp.R
import com.ana.dev.githublistapp.data.model.Project
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.ana.dev.githublistapp.databinding.ActivityMainBinding
import com.ana.dev.githublistapp.presentation.projectInfo.ProjectInfoActivity

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }


    fun displayItemInfo(project: Project?) {
        project?.let {
            val intent = Intent(this, ProjectInfoActivity::class.java)
            intent.putExtra(ProjectInfoActivity.PROJECT, project)
            startActivity(intent)
        }

    }


}