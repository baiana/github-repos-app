package com.ana.dev.githublistapp.utilities

import android.content.Context
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
//    val avd = AnimatedVectorDrawableCompat.create(this.context,
//        R.drawable.loading_octocat)
//    avd?.registerAnimationCallback(object :Animatable2Compat.AnimationCallback() {
//        override fun onAnimationEnd(drawable: Drawable?) {
//            avd.start()
//        }
//    })
//    this.apply {
//        setImageDrawable(avd)
//    }
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

fun Context.displayErrorWithFunction(message: String, tryAgainFun: () -> Unit) {
    CustomErrorDialog(this, errorMessage = message).displayDialogWithTryAgain(tryAgainFun)
}

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


fun ImageView.stopLoading() {
//    if (this.drawable is AnimatedVectorDrawable) {
//        try {
//            val loading = this.drawable as AnimatedVectorDrawable
//            loading.stop()
//            this.gone()
//        } catch (e: Exception) {
//            Log.e("Exception", e.message ?: "Exception ao parar o loading")
//        }
//    } else {
//        Log.e("Error/animation", "Imageview não é animação")
//    }
}