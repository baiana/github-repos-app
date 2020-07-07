package com.ana.dev.githublistapp.utilities

import android.widget.ImageView
import com.ana.dev.githublistapp.R
import com.squareup.picasso.Picasso

fun ImageView.loadWithPicasso(url: String) {
    Picasso.get().load(url).
//        error()
    placeholder(R.drawable.ic_user_place_holder).into(this)
}