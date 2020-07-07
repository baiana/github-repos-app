package com.ana.dev.githublistapp.utilities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.ana.dev.githublistapp.databinding.CustomErrorDialogLayoutBinding

class CustomErrorDialog(
    context: Context, val errorMessage: String
) : AlertDialog(context) {

    private val binding: CustomErrorDialogLayoutBinding by lazy(LazyThreadSafetyMode.NONE) {
        CustomErrorDialogLayoutBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.errorTXT.text = errorMessage
    }


    fun displayDialog() {
        binding.tryAgainBTN.gone()
        this.show()
    }

    fun displayDialogWithTryAgain(tryAgainFun: () -> Unit) {
        binding.tryAgainBTN.setOnClickListener {
            dismiss()
            tryAgainFun()
        }
        this.show()
    }
}