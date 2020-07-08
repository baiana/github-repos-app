package com.ana.dev.githublistapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Project(
    val id: String,
    val name: String,
    val user: User,
    val description: String,
    val url: String
) : Parcelable
