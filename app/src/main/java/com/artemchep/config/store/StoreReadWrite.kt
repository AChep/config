package com.artemchep.config.store

/**
 * @author Artem Chepurnoy
 * @see StoreRead
 * @see StoreWrite
 */
interface StoreReadWrite<K> : StoreRead<K>, StoreWrite<K>
