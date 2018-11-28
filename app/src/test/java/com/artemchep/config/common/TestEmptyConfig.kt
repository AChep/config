package com.artemchep.config.common

/**
 * @author Artem Chepurnoy
 */
class TestEmptyConfig : EmptyConfig<String>() {

    var intParameter by configDelegate(KEY_INT_PROP, VALUE_INT_PROP)

    var stringParameter by configDelegate(KEY_STRING_PROP, VALUE_STRING_PROP)

}
