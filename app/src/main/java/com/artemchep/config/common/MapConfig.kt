package com.artemchep.config.common

import com.artemchep.config.Config
import com.artemchep.config.editor.common.EmptyEditor
import com.artemchep.config.editor.common.MapEditor

/**
 * @author Artem Chepurnoy
 */
open class MapConfig<T>(val map: MutableMap<T, Any> = HashMap()) : Config<T>() {

    private val mapEditor = MapEditor(map)

    fun edit(block: () -> Unit) = edit(mapEditor, block)

}
