package com.artemchep.config.common

import com.artemchep.config.Config
import com.artemchep.config.editor.common.MapEditor

/**
 * @author Artem Chepurnoy
 */
open class MapConfig<T>(
    @Suppress("CanBeParameter")
    val map: MutableMap<T, Any> = HashMap(),
    reduceKeys: (T, T) -> T
) : Config<T>(
    reduceKeys = reduceKeys
) {

    private val mapEditor = MapEditor(map)

    fun edit(block: () -> Unit) = edit(mapEditor, block)

}
