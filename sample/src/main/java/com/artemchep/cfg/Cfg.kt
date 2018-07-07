package com.artemchep.cfg

import com.artemchep.config.common.SharedPrefConfig

/**
 * @author Artem Chepurnoy
 */
object Cfg : SharedPrefConfig("cfg") {

    const val KEY_READ_ONLY = "ro"
    const val KEY_NOTE = "note"

    /**
     * A switch indication if the note field should or should not
     * be read only.
     */
    var isReadOnly by configDelegate(KEY_READ_ONLY, false)

    /**
     * Saved note;
     */
    // Please don't use the Config as an actual note database,
    // it is not!
    var note by configDelegate(KEY_NOTE, "")

}