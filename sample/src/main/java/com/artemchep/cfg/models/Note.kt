package com.artemchep.cfg.models

import com.artemchep.config.Config
import com.artemchep.config.store.StoreRead
import com.artemchep.config.store.StoreWrite

/**
 * @author Artem Chepurnoy
 */
class Note : Config.Record<String> {

    var text: String = ""

    var timestamp: Long = 0L

    override fun putToStore(storeWrite: StoreWrite<String>, key: String) {
        storeWrite.apply {
            putString(key, text)
            putLong(key, timestamp)
        }
    }

    override fun getFromStore(storeRead: StoreRead<String>, key: String) {
        storeRead.apply {
            text = getString(key, text)
            timestamp = getLong(key, timestamp)
        }
    }

}