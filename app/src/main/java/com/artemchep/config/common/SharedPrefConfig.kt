package com.artemchep.config.common

import android.content.Context
import com.artemchep.config.Config
import com.artemchep.config.editor.common.SharedPrefEditor
import com.artemchep.config.extensions.asStore

/**
 * @author Artem Chepurnoy
 */
open class SharedPrefConfig(private val sharedPrefName: String) : Config<String>() {

    /**
     * Initializes the config with values loaded
     * from [shared preference file][sharedPrefName].
     */
    fun init(context: Context) {
        val store = context.getSharedPreferences(sharedPrefName, 0).asStore()
        init(store)
    }

    /**
     * Enters the "edit" mode, in which you can actually change
     * the values of a config.
     */
    fun edit(context: Context, block: () -> Unit) {
        val editor = SharedPrefEditor(context, sharedPrefName)
        edit(editor, block)
    }

}