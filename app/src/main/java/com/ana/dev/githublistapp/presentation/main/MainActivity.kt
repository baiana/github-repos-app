package com.ana.dev.githublistapp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ana.dev.githublistapp.R
import com.ana.dev.githublistapp.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by inject()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.getList()
    }
}