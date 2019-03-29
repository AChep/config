package com.artemchep.config

import com.artemchep.config.util.reduce
import org.junit.Assert
import org.junit.Test

/**
 * @author Artem Chepurnoy
 */
class ConfigPropertiesTest {

    @Test
    fun testAddProperties() {
        val key = "key"
        val value = "value"
        val config = object : Config<String>(
            reduceKeys = ::reduce
        ) {

            @Suppress("unused")
            val property by configDelegate(key, value)

            fun getProps() = properties

        }

        val property = config.getProps().first()
        Assert.assertEquals(key, property.key)
    }

}
