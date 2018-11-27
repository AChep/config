package com.artemchep.config.editor.common

import com.artemchep.config.editor.Editor
import com.artemchep.config.store.common.MapStoreReadWrite

/**
 * Editor that stores configuration values in
 * a map.
 *
 * @author Artem Chepurnoy
 */
class MapEditor<K>(map: MutableMap<K, Any>) : MapStoreReadWrite<K>(map), Editor<K> {

    /*
     * Changes are written to the map immediately,
     * no need to apply them.
     */
    override fun apply() {
    }

}