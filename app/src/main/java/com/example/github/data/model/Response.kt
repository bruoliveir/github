package com.example.github.data.model

data class Response<T>(
    val incomplete_results: Boolean,
    val items: List<T>,
    val total_count: Int
)