package com.ana.dev.githublistapp.utilities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.ana.dev.githublistapp.R

class CustomErrorDialog(
    context: Context,
    errorCode: Int? = null, errorId: Int? = null, errorMessage: String? = null
) : AlertDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_payment_success)

    }
}