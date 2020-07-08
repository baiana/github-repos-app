package com.ana.dev.githublistapp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ana.dev.githublistapp.R
import com.ana.dev.githublistapp.presentation.main.MainActivity
import com.ana.dev.githublistapp.utilities.displayError
import com.ana.dev.githublistapp.utilities.displayErrorWithFunction
import com.ana.dev.githublistapp.utilities.isConnected

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        validateAndProceed()
    }

    private fun validateAndProceed() {
        if (this.isConnected()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            displayErrorWithFunction(
                message = getString(R.string.error_no_internet),
                dismissOnclick = true, tryAgainFun = { validateAndProceed() }
            )
        }
    }
}