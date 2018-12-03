package com.artemchep.config

import com.artemchep.config.common.TestMapConfig
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito

/**
 * @author Artem Chepurnoy
 */
class ConfigMultiThreadingTest {

    @UseExperimental(ObsoleteCoroutinesApi::class)
    @Test
    fun testConcurrency() {
        val config = TestMapConfig()
        val context = newFixedThreadPoolContext(15, "ConfigMultiThreadingTestPool")

        val observer = mock<Config.OnConfigChangedListener<String>>()
        config.observe(observer)

        val n = 10000

        runBlocking {
            // Clone a data and iterate
            // through it.
            (0..n).forEach { index ->
                launch(context) {
                    config.edit {
                        config.intParameter = index
                    }
                }
            }
        }

        verify(observer, times(n)).onConfigChanged(any())
    }

}
