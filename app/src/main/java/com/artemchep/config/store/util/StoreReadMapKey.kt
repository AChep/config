package com.artemchep.config.store.util

import com.artemchep.config.store.StoreRead

/**
 * @author Artem Chepurnoy
 */
class StoreReadMapKey<K>(
    private val storeRead: StoreRead<K>,
    /**
     * Map function of the
     * keys.
     */
    private val map: (K) -> K
) : StoreRead<K> {
    override fun getBoolean(key: K, defaultValue: Boolean) =
        storeRead.getBoolean(map(key), defaultValue)

    override fun getString(key: K, defaultValue: String) =
        storeRead.getString(map(key), defaultValue)

    override fun getLong(key: K, defaultValue: Long) =
        storeRead.getLong(map(key), defaultValue)

    override fun getInt(key: K, defaultValue: Int) =
        storeRead.getInt(map(key), defaultValue)
}