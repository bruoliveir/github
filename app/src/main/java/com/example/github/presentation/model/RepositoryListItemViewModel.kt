package com.example.github.presentation.model

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.github.R
import com.example.github.data.model.Repository
import com.example.github.presentation.adapter.ViewHolder
import com.example.github.presentation.adapter.ViewModel
import kotlinx.android.synthetic.main.view_repository_list_item.view.*

const val REPOSITORY_LIST_ITEM_VIEW_MODEL = R.layout.view_repository_list_item

class RepositoryListItemViewModel(
    val name: String = "",
    val stars: Int = 0,
    val forks: Int = 0,
    val authorAvatar: String? = null,
    val authorName: String = ""
) : ViewModel {
    companion object {
        fun from(repository: Repository) = with(repository) {
            RepositoryListItemViewModel(
                name,
                stargazers_count,
                forks_count,
                owner.avatar_url,
                owner.login
            )
        }
    }
}

class RepositoryListItemViewHolder(itemView: View) : ViewHolder<RepositoryListItemViewModel>(itemView) {
    override fun bind(viewModel: RepositoryListItemViewModel) {
        itemView.apply {
            tvName.text = viewModel.name
            tvStarCount.text = context.getString(R.string.repository_list_item_stars, viewModel.stars)
            tvForkCount.text = context.getString(R.string.repository_list_item_forks, viewModel.forks)
            tvAuthorName.text = context.getString(R.string.repository_list_item_by_author, viewModel.authorName)

            Glide.with(this)
                .load(viewModel.authorAvatar)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivAuthorAvatar)
        }
    }

    override fun recycle() {
        itemView.apply {
            Glide.with(this).clear(ivAuthorAvatar)
        }
    }
}