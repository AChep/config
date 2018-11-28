package com.artemchep.config

import com.artemchep.config.common.KEY_INT_PROP
import com.artemchep.config.common.KEY_STRING_PROP
import com.artemchep.config.common.TestMapConfig
import com.artemchep.config.extensions.asStore
import com.artemchep.config.store.common.MapStoreReadWrite
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * @author Artem Chepurnoy
 */
class ConfigInitTest {

    private lateinit var config: TestMapConfig

    @Before
    fun initConfig() {
        config = TestMapConfig()
    }

    @Test
    fun testInit() {
        val src = mapOf(
            KEY_INT_PROP to 1,
            KEY_STRING_PROP to "1"
        )

        config.init(src.asStore())

        Assert.assertEquals(src[KEY_INT_PROP], config.intParameter)
        Assert.assertEquals(src[KEY_STRING_PROP], config.stringParameter)
    }

    @Test
    fun testInitOverwrite() {
        val srcA = mapOf(
            KEY_INT_PROP to 1,
            KEY_STRING_PROP to "1"
        )
        val srcB = mapOf(
            KEY_INT_PROP to 2,
            KEY_STRING_PROP to "2"
        )

        config.init(srcA.asStore())
        config.init(srcB.asStore())

        Assert.assertEquals(srcB[KEY_INT_PROP], config.intParameter)
        Assert.assertEquals(srcB[KEY_STRING_PROP], config.stringParameter)
    }

    @Test
    fun testInitPartially() {
        val src = mapOf(
            KEY_INT_PROP to 1
        )

        val stringPrev = config.stringParameter
        config.init(src.asStore())

        Assert.assertEquals(src[KEY_INT_PROP], config.intParameter)
        Assert.assertEquals(stringPrev, config.stringParameter)
    }

}
