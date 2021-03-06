package com.ana.dev.githublistapp.utilities

import android.content.Context
import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.ana.dev.githublistapp.R
import com.ana.dev.githublistapp.data.model.Project
import com.ana.dev.githublistapp.data.model.User
import com.ana.dev.githublistapp.data.response.ProjectResult
import com.squareup.picasso.Picasso


fun ImageView.loadWithPicasso(url: String) {
    Picasso.get().load(url).placeholder(R.drawable.ic_user_place_holder).into(this)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Context.displayErrorWithFunction(
    message: String,
    tryAgainFun: () -> Unit,
    dismissOnclick: Boolean = false
) {
    CustomErrorDialog(
        this,
        errorMessage = message,
        dismissOnclick = dismissOnclick
    ).displayDialogWithTryAgain(tryAgainFun)
}

fun List<ProjectResult>.convertToProjectArray() =
    ArrayList(this.map {
        Project(
            it.id,
            it.name,
            User(it.user.username, it.user.pictureLink),
            it.description ?: "",
            it.url
        )
    })


fun Context.displayError(message: String) {
    CustomErrorDialog(this, errorMessage = message).displayDialog()
}

fun getErrorMessageByCode(code: Int) =
    when (code) {
        //Client side error
        401 -> R.string.error_forbidden_401
        404 -> R.string.error_not_found_404
        449 -> R.string.error_try_again_449
        in 400..499 -> R.string.generic_client_error
        //server side error
        in 500..599 -> {
            R.string.error_server_500
        }
        else -> R.string.generic_client_error
    }


fun Context.isConnected(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetwork != null
}

fun ImageView.stopLoading() {
    if (this.drawable is AnimatedVectorDrawable && this.context != null) {
        val loading = this.drawable as AnimatedVectorDrawable
        loading.stop()
        this.gone()
    } else {
        Log.e("Error/animation", "Imageview não é animação")
    }
}

fun ImageView.playLoading() {
    if (this.drawable is AnimatedVectorDrawable && this.context != null) {
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

