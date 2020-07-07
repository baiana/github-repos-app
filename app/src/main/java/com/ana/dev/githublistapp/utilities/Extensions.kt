package com.ana.dev.githublistapp.utilities

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.media.Image
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.ana.dev.githublistapp.R
import com.squareup.picasso.Picasso
import java.lang.Exception
import kotlin.math.log

fun ImageView.loadWithPicasso(url: String) {
    Picasso.get().load(url).placeholder(R.drawable.ic_user_place_holder).into(this)
}

fun ImageView.playLoading() {
    if (this.drawable is AnimatedVectorDrawable) {
        val loading = this.drawable as AnimatedVectorDrawable
        loading.apply {
            registerAnimationCallback(object : Animatable2.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    loading.start()
                }
            })
            start()
        }
        this.visible()
    } else {
        Log.e("Error/animation", "Imageview não é animação")
    }

}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun ImageView.stopLoading() {
    if (this.drawable is AnimatedVectorDrawable) {
        try {
            val loading = this.drawable as AnimatedVectorDrawable
            loading.stop()
            this.gone()
        } catch (e: Exception) {
            Log.e("Exception", e.message ?: "Exception ao parar o loading")
        }
    } else {
        Log.e("Error/animation", "Imageview não é animação")
    }
}