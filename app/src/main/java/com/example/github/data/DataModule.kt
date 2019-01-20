package com.example.github.data

import com.example.github.App
import com.example.github.BuildConfig
import com.example.github.data.api.GitHubApi
import com.example.github.data.utils.CacheInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun gitHubApi(okHttpClient: OkHttpClient): GitHubApi = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL_GITHUB)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
        .create(GitHubApi::class.java)

    @Provides
    @Singleton
    fun okHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        cache: Cache,
        cacheInterceptor: CacheInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addNetworkInterceptor(cacheInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun httpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) BASIC else NONE
    }

    @Provides
    @Singleton
    fun cacheInterceptor(app: App) = CacheInterceptor(app.applicationContext)

    @Provides
    @Singleton
    fun cache(app: App) = Cache(app.applicationContext.cacheDir, 10 * 1024 * 1024)
}
