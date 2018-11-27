package com.artemchep.config.extensions

import com.artemchep.config.store.StoreReadWrite
import com.artemchep.config.store.StoreRead
import com.artemchep.config.store.common.MapStoreReadWrite
import com.artemchep.config.store.common.MapStoreRead

fun <K> Map<K, *>.asStore(): StoreRead<K> = MapStoreRead(this)

fun <K> MutableMap<K, Any>.asStore(): StoreReadWrite<K> = MapStoreReadWrite(this)
