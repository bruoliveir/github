package com.example.github.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class ViewHolder<in T : ViewModel>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(viewModel: T)
    abstract fun recycle()
}