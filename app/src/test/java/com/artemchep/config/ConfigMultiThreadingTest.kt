package com.artemchep.config

import com.artemchep.config.common.TestMapConfig
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import org.junit.Test

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

        val n = 1000

        runBlocking {
            // Clone a data and iterate
            // through it.
            (0..n).forEach { index ->
                launch(context) {
                    config.edit {
                        // Just to increase the chances
                        // of concurrency issues.
                        Thread.sleep(10L)

                        config.intParameter = index
                    }
                }
            }
        }

        verify(observer, times(n)).onConfigChanged(any())
    }

}
