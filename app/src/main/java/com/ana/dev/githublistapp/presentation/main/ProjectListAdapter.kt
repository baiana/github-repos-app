package com.ana.dev.githublistapp.presentation.main

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ana.dev.githublistapp.R
import com.ana.dev.githublistapp.utilities.loadWithPicasso
import com.ana.dev.githublistapp.data.model.Project
import com.ana.dev.githublistapp.databinding.ProjectListItemBinding

class ProjectListAdapter(private var projects: ArrayList<Project>, val resources: Resources) :
    RecyclerView.Adapter<ProjectListAdapter.ProjectHolder>() {

    var detailsClickListener: OnProjectClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        val binding =
            ProjectListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectHolder(binding, resources)
    }

    override fun getItemCount() = projects.size

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val repoItem = projects[position]
        holder.bind(repoItem, detailsClickListener)
    }

    class ProjectHolder(private val binding: ProjectListItemBinding, val resources: Resources) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(repo: Project, detailsClickListener: OnProjectClickListener?) {
            with(binding) {
                projectTXT.text =
                    resources.getString(R.string.project_name_template, repo.user.name, repo.name)
                userTXT.text = repo.description
                avatarIMG.loadWithPicasso(repo.user.pictureUrl)
                holderCL.setOnClickListener {
                    detailsClickListener?.onClick(repo)
                }
            }
        }
    }

    fun swap(projectsList: ArrayList<Project>) {
        this.projects = projectsList
        notifyDataSetChanged()
    }

    interface OnProjectClickListener {
        fun onClick(project: Project)
    }


}
