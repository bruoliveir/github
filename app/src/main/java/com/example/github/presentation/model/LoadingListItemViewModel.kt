package com.example.github.presentation.model

import android.view.View
import com.example.github.R
import com.example.github.presentation.adapter.ViewHolder
import com.example.github.presentation.adapter.ViewModel

const val LOADING_LIST_ITEM_VIEW_MODEL = R.layout.view_loading_list_item

object LoadingListItemViewModel : ViewModel

class LoadingListItemViewHolder(itemView: View) : ViewHolder<LoadingListItemViewModel>(itemView) {
    override fun bind(viewModel: LoadingListItemViewModel) {}
    override fun recycle() {}
}