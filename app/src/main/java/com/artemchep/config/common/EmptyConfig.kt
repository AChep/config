package com.artemchep.config.common

import com.artemchep.config.Config
import com.artemchep.config.editor.common.EmptyEditor

/**
 * @author Artem Chepurnoy
 */
open class EmptyConfig<T> : Config<T>() {

    private val emptyEditor = EmptyEditor<T>()

    fun edit(block: () -> Unit) = edit(emptyEditor, block)

}
