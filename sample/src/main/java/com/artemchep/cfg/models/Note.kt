package com.artemchep.cfg.models

import com.artemchep.config.Config
import com.artemchep.config.store.StoreRead
import com.artemchep.config.store.StoreWrite

/**
 * @author Artem Chepurnoy
 */
data class Note(
    val text: String = "",
    val timestamp: Long = 0L
) : Config.Record<String> {

    companion object {
        private const val KEY_TEXT = "text"
        private const val KEY_TIMESTAMP = "timestamp"
    }

    override fun putToStore(storeWrite: StoreWrite<String>) {
        storeWrite.apply {
            putString(KEY_TEXT, text)
            putLong(KEY_TIMESTAMP, timestamp)
        }
    }

    override fun getFromStore(storeRead: StoreRead<String>): Config.Record<String> {
        return Note(
            text = storeRead.getString(KEY_TEXT, text),
            timestamp = storeRead.getLong(KEY_TIMESTAMP, timestamp)
        )
    }

}