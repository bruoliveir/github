package com.example.github.presentation.features.repository.list

import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryListModule {
    @Binds
    abstract fun view(repositoryListActivity: RepositoryListActivity): RepositoryListView
}