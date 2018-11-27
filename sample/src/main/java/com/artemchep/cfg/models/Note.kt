package com.artemchep.cfg.models

import com.artemchep.config.Config
import com.artemchep.config.store.StoreRead
import com.artemchep.config.store.StoreWrite

/**
 * @author Artem Chepurnoy
 */
data class Note(
    var text: String = "",
    var timestamp: Long = 0L
) : Config.Record<String> {

    companion object {
        fun formatTextKey(key: String) = "$key::text"
        fun formatTimestampKey(key: String) = "$key::timestamp"
    }

    override fun putToStore(storeWrite: StoreWrite<String>, key: String) {
        storeWrite.apply {
            putString(formatTextKey(key), text)
            putLong(formatTimestampKey(key), timestamp)
        }
    }

    override fun getFromStore(storeRead: StoreRead<String>, key: String) {
        storeRead.apply {
            text = getString(formatTextKey(key), text)
            timestamp = getLong(formatTimestampKey(key), timestamp)
        }
    }

}