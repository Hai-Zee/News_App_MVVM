package com.example.newsappzeesh.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

class MyReceiver : BroadcastReceiver() {

    var isConnected : Boolean? = null
    lateinit var connectivityInterface : ConnectivityInterface

    override fun onReceive(context: Context, intent: Intent) {

        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.

        connectivityInterface = context as ConnectivityInterface

        if (intent.extras == null)
            return

        val connectivityManager : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.state == NetworkInfo.State.CONNECTED){
            isConnected = networkInfo.type == ConnectivityManager.TYPE_MOBILE || networkInfo.type == ConnectivityManager.TYPE_WIFI
        }
        else
            isConnected = false

        connectivityInterface.connectivity(isConnected!!)
    }

    interface ConnectivityInterface{
        fun connectivity(isConnected : Boolean)
    }
}