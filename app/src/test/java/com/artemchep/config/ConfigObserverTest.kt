package com.artemchep.config

import com.artemchep.config.common.TestEmptyConfig
import com.artemchep.config.common.KEY_INT_PROP
import com.artemchep.config.common.KEY_STRING_PROP
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.whenever
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