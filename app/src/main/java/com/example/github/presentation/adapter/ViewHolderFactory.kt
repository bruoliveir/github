package com.example.github.presentation.adapter

import android.view.View

interface ViewHolderFactory {
    fun getType(viewModel: ViewModel): Int
    fun getHolder(viewType: Int, view: View): ViewHolder<*>
    fun onScrolledBeyondVisibleThreshold()
}