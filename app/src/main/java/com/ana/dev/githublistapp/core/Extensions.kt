package com.ana.dev.githublistapp.core

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadWithPicasso(url: String) {
    Picasso.get().
    load(url).
//        error()
//        placeholder(0).
    into(this)
}