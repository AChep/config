package com.artemchep.config.common

/**
 * @author Artem Chepurnoy
 */
class TestMapConfig(map: MutableMap<String, Any> = HashMap()) : MapConfig<String>(map) {

    var intParameter by configDelegate(KEY_INT_PROP, VALUE_INT_PROP)

    var stringParameter by configDelegate(KEY_STRING_PROP, VALUE_STRING_PROP)

}
