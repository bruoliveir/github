package com.example.github.data.api

import com.example.github.data.model.Repository
import com.example.github.data.model.Response
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/repositories")
    fun getSearchRepositories(
        @Query("q") q: String = "language:kotlin",
        @Query("sort") sort: String = "stars",
        @Query("page") page: Int = 1
    ): Observable<Response<Repository>>
}