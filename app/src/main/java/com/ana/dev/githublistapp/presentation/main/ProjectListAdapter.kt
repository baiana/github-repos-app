package com.ana.dev.githublistapp.presentation.main

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ana.dev.githublistapp.core.loadWithPicasso
import com.ana.dev.githublistapp.data.model.Project
import com.ana.dev.githublistapp.databinding.ProjectListItemBinding
import com.squareup.picasso.Picasso

class ProjectListAdapter(private var projects: ArrayList<Project>, val resources: Resources?) :
    RecyclerView.Adapter<ProjectListAdapter.ProjectHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        val binding =
            ProjectListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProjectHolder(binding)
    }

    override fun getItemCount() = projects.size

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val repoItem = projects[position]
        holder.bind(repoItem)
    }

    class ProjectHolder(private val binding: ProjectListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Project) {
            with(binding) {
                projectTXT.text = repo.name
                userTXT.text = repo.user.name
                avatarIMG.loadWithPicasso(repo.url)
            }
        }
    }

    fun swap(projectsList: ArrayList<Project>) {
        this.projects = projectsList
        notifyDataSetChanged()
    }



}
