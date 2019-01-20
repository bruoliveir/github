package com.example.github.presentation.features.repository.list

import com.example.github.R
import com.example.github.data.api.GitHubApi
import com.example.github.data.extensions.onDefaultSchedulers
import com.example.github.presentation.model.RepositoryListItemViewModel
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryListPresenter @Inject constructor(
    private val view: RepositoryListView,
    private val gitHubApi: GitHubApi
) {
    private val compositeDisposable = CompositeDisposable()
    private var page = 1
    private var isLoading = false
    private var isComplete = false

    fun onCreate() {
        subscribe()
    }

    fun onRefresh() {
        isComplete = false
        page = 1
        compositeDisposable.clear()
        subscribe()
    }

    fun onScrolledBeyondVisibleThreshold() {
        if (!isLoading && !isComplete) subscribe()
    }

    private fun subscribe() {
        compositeDisposable.add(gitHubApi.getSearchRepositories(page = page)
            .onDefaultSchedulers()
            .doOnSubscribe {
                isLoading = true
                view.startLoading()
            }.doOnNext {
                view.stopLoading()
                isLoading = false
            }.subscribe(
                { response ->
                    val list = response.items.map { RepositoryListItemViewModel.from(it) }

                    if (page == 1 && list.isEmpty()) {
                        view.showEmptyState()
                    } else {
                        view.hideEmptyState()
                        val clearList = page == 1
                        view.addItems(list, clearList)
                        page++
                    }
                },
                {
                    it.printStackTrace()
                    if (it is HttpException && it.code() == 422) {
                        isComplete = true
                        view.stopLoading()
                        isLoading = false
                    } else {
                        view.showDialog(R.string.repository_list_fetch_error) {
                            view.stopLoading()
                            isLoading = false
                        }
                    }
                }
            ))
    }

    fun onDestroy() {
        compositeDisposable.dispose()
    }
}
