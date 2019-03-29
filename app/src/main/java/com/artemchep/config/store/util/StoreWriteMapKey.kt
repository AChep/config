package com.artemchep.config.store.util

import com.artemchep.config.store.StoreWrite

/**
 * @author Artem Chepurnoy
 */
class StoreWriteMapKey<K>(
    private val storeWrite: StoreWrite<K>,
    /**
     * Map function of the
     * keys.
     */
    private val map: (K) -> K
) : StoreWrite<K> {
    override fun putBoolean(key: K, value: Boolean) = storeWrite.putBoolean(map(key), value)

    override fun putString(key: K, value: String) = storeWrite.putString(map(key), value)

    override fun putLong(key: K, value: Long) = storeWrite.putLong(map(key), value)

    override fun putInt(key: K, value: Int) = storeWrite.putInt(map(key), value)
}