package com.ana.dev.githublistapp.presentation.projectInfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ana.dev.githublistapp.R
import com.ana.dev.githublistapp.utilities.loadWithPicasso
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
        viewModel.projectInfoLiveData.observe(this, Observer {
            val repo = it.projectInfo
            with(binding) {
                repo?.apply {
                    descriptionTXT.text = description
                    userTXT.text = user.name
                    projectTXT.text = name
                    avatarIMG.loadWithPicasso(user.pictureUrl)
                    if (url.isNotBlank()) {
                        openProjectButtonCLickListener(url)
                    }
                }
            }
        })
        viewModel.displayProjectInfo(intent.getParcelableExtra(PROJECT))
        binding.closeBTN.setOnClickListener { onBackPressed() }
    }

    private fun openExternalLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun openProjectButtonCLickListener(url: String) {
        binding.openProjectBTN.setOnClickListener {
            openExternalLink(url)
        }
        binding.userInfoLL.setOnClickListener {
            openUserProfile()
        }
    }

    private fun openUserProfile() {
        val url = resources.getString(R.string.github_url_x, viewModel.getUsername())
        openExternalLink(url)
    }

    companion object {
        const val PROJECT = "projectdata"
    }
}