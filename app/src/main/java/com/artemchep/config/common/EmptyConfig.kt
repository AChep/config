package com.artemchep.config.common

import com.artemchep.config.Config
import com.artemchep.config.editor.common.EmptyEditor

/**
 * @author Artem Chepurnoy
 */
open class EmptyConfig<T> : Config<T>(
    reduceKeys = { a, _ ->
        // We won't put anything to the config,
        // so it doesn't matter.
        a
    }
) {

    private val emptyEditor = EmptyEditor<T>()

    fun edit(block: () -> Unit) = edit(emptyEditor, block)

}
