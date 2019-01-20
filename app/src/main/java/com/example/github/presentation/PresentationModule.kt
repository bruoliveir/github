package com.example.github.presentation

import com.example.github.presentation.features.repository.list.RepositoryListActivity
import com.example.github.presentation.features.repository.list.RepositoryListModule
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class])
abstract class PresentationModule {
    @ContributesAndroidInjector(modules = [RepositoryListModule::class])
    abstract fun repositoryListActivity(): RepositoryListActivity
}
