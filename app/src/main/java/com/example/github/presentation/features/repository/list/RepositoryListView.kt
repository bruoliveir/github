package com.example.github.presentation.features.repository.list

import com.example.github.presentation.model.RepositoryListItemViewModel

interface RepositoryListView {
    fun showDialog(messageResId: Int, onDismiss: () -> Unit = {})
    fun addItems(items: List<RepositoryListItemViewModel>, clear: Boolean)
    fun startLoading()
    fun stopLoading()
    fun startRefreshing()
    fun stopRefreshing()
    fun showEmptyState()
    fun hideEmptyState()
}
