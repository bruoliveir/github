package com.example.github.presentation.model

import android.view.View
import com.example.github.R
import com.example.github.presentation.adapter.ViewHolder
import com.example.github.presentation.adapter.ViewModel

const val REPOSITORY_LIST_EMPTY_STATE_VIEW_MODEL = R.layout.view_repository_list_empty_state

object RepositoryListEmptyStateViewModel : ViewModel

class RepositoryListEmptyStateViewHolder(itemView: View) : ViewHolder<RepositoryListEmptyStateViewModel>(itemView) {
    override fun bind(viewModel: RepositoryListEmptyStateViewModel) {}
    override fun recycle() {}
}