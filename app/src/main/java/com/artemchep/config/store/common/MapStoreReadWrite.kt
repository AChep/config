package com.artemchep.config.store.common

import com.artemchep.config.store.StoreReadWrite

/**
 * @author Artem Chepurnoy
 */
open class MapStoreReadWrite<K>(private val map: MutableMap<K, Any>) : MapStoreRead<K>(map), StoreReadWrite<K> {

    override fun putBoolean(key: K, value: Boolean) {
        map[key] = value
    }

    override fun putString(key: K, value: String) {
        map[key] = value
    }

    override fun putLong(key: K, value: Long) {
        map[key] = value
    }

    override fun putInt(key: K, value: Int) {
        map[key] = value
    }

}