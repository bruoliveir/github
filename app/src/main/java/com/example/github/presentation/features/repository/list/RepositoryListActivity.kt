package com.example.github.presentation.features.repository.list

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.github.R
import com.example.github.presentation.adapter.Adapter
import com.example.github.presentation.adapter.ViewHolder
import com.example.github.presentation.adapter.ViewHolderFactory
import com.example.github.presentation.adapter.ViewModel
import com.example.github.presentation.extensions.show
import com.example.github.presentation.model.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_repository_list.*
import javax.inject.Inject

class RepositoryListActivity : AppCompatActivity(), RepositoryListView {

    @Inject
    lateinit var presenter: RepositoryListPresenter

    private var adapter: Adapter? = null
    private var dialog: AlertDialog? = null

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_list)

        setupSwipeRefresh()
        setupList()

        presenter.onCreate()
    }

    private fun setupSwipeRefresh() {
        srlRefresh.isEnabled = false
        srlRefresh.setOnRefreshListener { presenter.onRefresh() }
    }

    private fun setupList() {
        adapter = Adapter(object : ViewHolderFactory {
            override fun getType(viewModel: ViewModel) = when (viewModel) {
                is RepositoryListItemViewModel -> REPOSITORY_LIST_ITEM_VIEW_MODEL
                is RepositoryListEmptyStateViewModel -> REPOSITORY_LIST_EMPTY_STATE_VIEW_MODEL
                is LoadingListItemViewModel -> LOADING_LIST_ITEM_VIEW_MODEL
                else -> throw IllegalArgumentException()
            }

            override fun getHolder(viewType: Int, view: View): ViewHolder<*> = when (viewType) {
                REPOSITORY_LIST_ITEM_VIEW_MODEL -> RepositoryListItemViewHolder(view)
                REPOSITORY_LIST_EMPTY_STATE_VIEW_MODEL -> RepositoryListEmptyStateViewHolder(view)
                LOADING_LIST_ITEM_VIEW_MODEL -> LoadingListItemViewHolder(view)
                else -> throw IllegalArgumentException()
            }

            override fun onScrolledBeyondVisibleThreshold() {
                presenter.onScrolledBeyondVisibleThreshold()
            }
        })

        rvList.apply {
            layoutManager = LinearLayoutManager(this@RepositoryListActivity)
            adapter = this@RepositoryListActivity.adapter
            setItemViewCacheSize(0)
            setHasFixedSize(true)
        }
    }

    override fun showDialog(messageResId: Int, onDismiss: () -> Unit) {
        if (dialog?.isShowing == true) dialog?.dismiss()
        dialog = AlertDialog.Builder(this).show(messageResId) { onDismiss() }
    }

    override fun addItems(items: List<RepositoryListItemViewModel>, clear: Boolean) {
        adapter?.apply { if (clear) replace(items) else add(items) }
    }

    override fun startLoading() {
        if (!srlRefresh.isRefreshing) {
            srlRefresh.isEnabled = false
            adapter?.isLoading = true
        }
    }

    override fun stopLoading() {
        srlRefresh.apply {
            isEnabled = true
            isRefreshing = false
        }
        adapter?.isLoading = false
    }

    override fun showEmptyState() {
        adapter?.replace(listOf(RepositoryListEmptyStateViewModel))
        rvList.isLayoutFrozen = true
    }

    override fun hideEmptyState() {
        adapter?.remove(RepositoryListEmptyStateViewModel)
        rvList.isLayoutFrozen = false
    }

    override fun startRefreshing() {
        srlRefresh.isRefreshing = true
    }

    override fun stopRefreshing() {
        srlRefresh.isRefreshing = false
    }
}
