package com.artemchep.config.extensions

import com.artemchep.config.Config

inline fun <T, C : Config<*, T>> C.editWithConfig(helper: T? = null, crossinline block: C.() -> Unit) {
    edit(helper) {
        block.invoke(this@editWithConfig)
    }
}
