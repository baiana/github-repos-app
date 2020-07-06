package com.ana.dev.githublistapp.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val name: String,
    val pictureUrl: String
) : Parcelable
