package com.artemchep.config

import com.artemchep.config.common.KEY_INT_PROP
import com.artemchep.config.common.KEY_STRING_PROP
import com.artemchep.config.common.TestMapConfig
import com.artemchep.config.extensions.asStore
import com.artemchep.config.store.common.MapStoreReadWrite
import org.junit.Assert
import org.junit.Test

/**
 * @author Artem Chepurnoy
 */
class ConfigCopyTest {

    @Test
    fun testCopyTo() {
        val dst = mutableMapOf<String, Any>()
        val src = mapOf(
            KEY_INT_PROP to 1,
            KEY_STRING_PROP to "1"
        )

        val store = MapStoreReadWrite(dst)
        val config = TestMapConfig()
        config.init(src.asStore())
        config.copyTo(store)

        Assert.assertTrue(src == dst)
    }

}
