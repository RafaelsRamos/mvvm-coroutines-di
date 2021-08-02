package com.example.mvvmcoroutines.utils

import java.net.InetAddress

fun hasInternetConnection(): Boolean {
    return try {
        val ipAddr: InetAddress = InetAddress.getByName("google.com")
        //You can replace it with your name
        !ipAddr.equals("")
    } catch (e: Exception) {
        false
    }
}