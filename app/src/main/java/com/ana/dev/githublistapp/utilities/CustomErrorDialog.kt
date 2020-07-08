package com.ana.dev.githublistapp.utilities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.ana.dev.githublistapp.databinding.CustomErrorDialogLayoutBinding

class CustomErrorDialog(
    context: Context, val errorMessage: String, private val dismissOnclick: Boolean = false
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
        this.setCanceledOnTouchOutside(dismissOnclick)
    }

    fun displayDialog() {
        binding.tryAgainBTN.gone()
        this.show()
    }

    fun displayDialogWithTryAgain(tryAgainFun: () -> Unit) {
        binding.tryAgainBTN.apply {
            setOnClickListener {
                dismiss()
                tryAgainFun()
            }
            visible()

        }
        this.show()
    }
}