package com.artemchep.config

import com.artemchep.config.common.KEY_INT_PROP
import com.artemchep.config.common.KEY_STRING_PROP
import com.artemchep.config.common.TestEmptyConfig
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.mock
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * @author Artem Chepurnoy
 */
class ConfigObserverTest {

    private lateinit var config: TestEmptyConfig

    @Before
    fun initConfig() {
        config = TestEmptyConfig()
    }

    @Test(expected = Exception::class)
    fun testAddDuplicateObserver() {
        val observer = mock<Config.OnConfigChangedListener<String>>()
        config.apply {
            observe(observer)
            observe(observer) // should crash
        }
    }

    @Test
    fun testAddObserverFromEvent() {
        val observer = object : Config.OnConfigChangedListener<String> {

            override fun onConfigChanged(keys: Set<String>) {
                config.removeObserver(this) // stop listening to this

                // Add new observer and produce
                // a new change.
                val observerNested = mock<Config.OnConfigChangedListener<String>>()
                config.observe(observerNested) // should be fine
                config.edit {
                    config.intParameter = 2
                } // should emit the change to an observer

                verify(observerNested, times(1)).onConfigChanged(any())
            }

        }

        config.apply {
            observe(observer)
            observe(mock())
            edit {
                intParameter = 1
            } // should emit the change to an observer
        }
    }

    @Test
    fun testRemoveObserver() {
        val observer = object : Config.OnConfigChangedListener<String> {

            override fun onConfigChanged(keys: Set<String>) {
                Assert.fail()
            }

        }

        config.apply {
            observe(observer)
            removeObserver(observer)

            edit {
                intParameter = 1
            } // should not emit the change to an observer
        }
    }

    @Test
    fun testRemoveDuplicateObserver() {
        val observer = mock<Config.OnConfigChangedListener<String>>()
        config.apply {
            observe(observer)
            removeObserver(observer)
            removeObserver(observer) // should be fine
        }
    }

    @Test
    fun testOnePropertyObservable() {
        val expected = setOf(KEY_INT_PROP)
        val observer = object : Config.OnConfigChangedListener<String> {

            override fun onConfigChanged(keys: Set<String>) {
                Assert.assertEquals(keys, expected)
            }

        }

        config.apply {
            observe(observer)
            edit {
                intParameter = 1
            }
        }
    }

    @Test
    fun testManyPropertyObservable() {
        val expected = setOf(KEY_INT_PROP, KEY_STRING_PROP)
        val observer = object : Config.OnConfigChangedListener<String> {

            override fun onConfigChanged(keys: Set<String>) {
                Assert.assertEquals(keys, expected)
            }

        }

        config.apply {
            observe(observer)
            edit {
                intParameter = 1
                stringParameter = "1"
            }
        }
    }

}