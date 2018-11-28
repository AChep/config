package com.artemchep.config

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
        val config = object : Config<String>() {

            @Suppress("unused")
            val property by configDelegate(key, value)

            fun getProps() = properties

        }

        val property = config.getProps().first()
        Assert.assertEquals(key, property.key)
    }

}
