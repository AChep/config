package com.artemchep.config.extensions

import com.artemchep.config.Config
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow

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

/**
 * Creates a flow of the single property out of the
 * channel.
 */
fun <K, V : Any> Config<out K>.asFlowOfProperty(delegate: Config<out K>.ConfigDelegate<out V>) =
    flow {
        // Immediately emit an initial value
        emit(delegate.value)

        // Observe the changes in a config
        coroutineScope {
            observe(this@asFlowOfProperty)
                .consumeAsFlow()
                .filter { it == delegate.key }
                .collect {
                    emit(delegate.value)
                }
        }
    }
