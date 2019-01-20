package com.example.github.data.utils

import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import com.example.github.data.extensions.isConnected
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

class CacheInterceptor(context: Context) : Interceptor {
    private val connectivityManager = context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = with(chain) { proceed(request()) }

        val cacheControl = with(CacheControl.Builder()) {
            if (connectivityManager.isConnected()) {
                maxAge(5, TimeUnit.MINUTES)
            } else {
                onlyIfCached()
                maxStale(30, TimeUnit.DAYS)
            }
            build()
        }

        return response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}
