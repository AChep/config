package com.artemchep.config.extensions

import com.artemchep.config.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * Creates a channel of changes from the config.
 */
fun <K> CoroutineScope.observe(
    config: Config<K>,
    /**
     * Factory to build the output
     * channel.
     */
    channelFactory: () -> Channel<K> = { Channel(Channel.RENDEZVOUS) }
): Channel<K> {
    val channel = channelFactory()

    launch(Dispatchers.Main) {
        suspendCancellableCoroutine<Unit> {
            val observer = object : Config.OnConfigChangedListener<K> {
                override fun onConfigChanged(keys: Set<K>) {
                    launch {
                        // Send changed keys to a
                        // channel.
                        keys.forEach { key ->
                            channel.send(key)
                        }
                    }
                }
            }

            config.observe(observer)

            it.invokeOnCancellation {
                config.removeObserver(observer)
            }
        }
    }

    return channel
}