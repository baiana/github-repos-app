package com.ana.dev.githublistapp.presentation.projectInfo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ana.dev.githublistapp.R
import com.ana.dev.githublistapp.utilities.loadWithPicasso
import com.ana.dev.githublistapp.databinding.ActivityProjectInfoBinding
import com.ana.dev.githublistapp.utilities.CustomErrorDialog
import com.ana.dev.githublistapp.utilities.displayError
import com.ana.dev.githublistapp.utilities.displayErrorWithFunction
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

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

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
        binding.closeBTN.setOnClickListener {
            onBackPressed()
        }
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