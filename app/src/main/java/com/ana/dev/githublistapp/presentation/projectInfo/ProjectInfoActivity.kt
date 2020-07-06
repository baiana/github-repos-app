package com.ana.dev.githublistapp.presentation.projectInfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ana.dev.githublistapp.core.loadWithPicasso
import com.ana.dev.githublistapp.data.model.Project
import com.ana.dev.githublistapp.databinding.ActivityProjectInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProjectInfoActivity : AppCompatActivity() {
    private val viewModel: ProjectInfoViewModel by viewModel()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityProjectInfoBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModelSetup()
    }

    private fun viewModelSetup() {
//        viewModel
        viewModel.projectInfoLiveData.observe(this, Observer {
            with(binding) {
                descriptionTXT.text = it.description
                userTXT.text = it.user.name
                projectTXT.text = it.name
                avatarIMG.loadWithPicasso(it.user.pictureUrl)
                if (it.url.isNotBlank()) {
                    openProjectButtonCLickListener(it.url)
                }
            }

        })

        viewModel.displayProjectInfo(intent.getParcelableExtra(PROJECT))
    }

    private fun openRepositoryOnWeb(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun openProjectButtonCLickListener(url: String) {
        binding.openProjectBTN.setOnClickListener {
            viewModel.projectInfoLiveData.value?.url?.let { it -> openRepositoryOnWeb(it) }
        }

    }

    companion object {
        const val PROJECT = "projectdata"
    }
}