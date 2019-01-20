package com.example.github

import com.example.github.data.DataModule
import com.example.github.presentation.PresentationModule
import dagger.BindsInstance
import dagger.Component

import javax.inject.Singleton

@Component(modules = [PresentationModule::class, DataModule::class])
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}
