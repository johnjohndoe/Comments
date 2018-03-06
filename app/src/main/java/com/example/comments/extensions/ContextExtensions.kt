package com.example.comments.extensions

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import android.view.LayoutInflater


fun Context.getConnectivityManager() =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.getLayoutInflater() =
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

fun Context.getPreferences(): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(this)

fun Context.isConnectionAvailable(): Boolean {
    val networkInfo = getConnectivityManager().activeNetworkInfo
    return networkInfo != null && networkInfo.isConnectedOrConnecting
}
