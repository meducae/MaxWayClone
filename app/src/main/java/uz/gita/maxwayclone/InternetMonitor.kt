package uz.gita.maxwayclone

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class InternetMonitor(private val context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val listeners = mutableListOf<(Boolean) -> Unit>()
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    private var redirectHandled = false


    fun registerListener(listener: (Boolean) -> Unit) {
        listeners.add(listener)
        val connected = connectivityManager.activeNetwork?.let { network ->
            connectivityManager.getNetworkCapabilities(network)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } ?: false
        listener(connected)
    }

    fun startMonitoring() {
        if (networkCallback != null) return
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                listeners.forEach { it(true) }
                redirectHandled = false
            }
            override fun onLost(network: Network) {
                listeners.forEach { listener ->
                    if (!redirectHandled) {
                        listener(false)
                        redirectHandled = true
                    }
                }
            }
        }

        connectivityManager.registerNetworkCallback(request, networkCallback!!)
    }

    fun stopMonitoring() {
        networkCallback?.let {
            connectivityManager.unregisterNetworkCallback(it)
            networkCallback = null
        }
        listeners.clear()
        redirectHandled = false
    }
}
