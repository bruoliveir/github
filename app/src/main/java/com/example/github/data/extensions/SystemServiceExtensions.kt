package com.example.github.data.extensions

import android.net.ConnectivityManager

fun ConnectivityManager.isConnected() = activeNetworkInfo?.isConnected == true
