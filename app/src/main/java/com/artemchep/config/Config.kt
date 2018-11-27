package com.artemchep.config

import com.artemchep.config.editor.Editor
import com.artemchep.config.observable.Observable
import com.artemchep.config.observable.ObservableRegistration
import com.artemchep.config.store.StoreRead
import com.artemchep.config.store.StoreWrite
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author Artem Chepurnoy
 */
abstract class Config<K, T> :
    Observable<Config.OnConfigChangedListener<K>> {

    private val properties: MutableList<ConfigDelegate<*>> = ArrayList()

    /**
     * `true` if the config is being edited,
     * `false` otherwise.
     */
    protected var isEditing = false
        private set

    protected var editor: Editor<K>? = null
        private set

    protected var helper: T? = null
        private set

    /** Set of current changes in the config */
    private val changes: MutableSet<K> = HashSet()

    /** List of current listeners of the config */
    private val listeners: MutableList<OnConfigChangedListener<K>> = ArrayList()

    fun init(storeRead: StoreRead<K>) {
        // Init each of the properties
        properties.forEach { property ->
            property.init(storeRead)
        }
    }

    /**
     * Writes all of the [properties] to given [store][StoreWrite].
     * @see init
     */
    infix fun writeTo(storeWrite: StoreWrite<K>) {
        // Write each of the properties
        properties.forEach { it.putToStore(storeWrite, it.key) }
    }

    fun edit(helper: T? = null, block: () -> Unit) {
        synchronized(this) {
            val keys: Set<K>
            try {
                this.isEditing = true
                this.helper = helper
                // Create editor and do the
                // changes.
                createEditor().let {
                    this.editor = it
                    block.invoke()
                    it.apply()
                }
            } finally {
                this.isEditing = false
                this.helper = null
                this.editor = null

                // Copy the changes map
                keys = HashSet(changes)
                changes.clear()
            }

            // Notify every listener about
            // those changes.
            if (keys.isNotEmpty()) synchronized(listeners) {
                listeners.forEach {
                    it.onConfigChanged(keys)
                }
            }
        }
    }

    protected abstract fun createEditor(): Editor<K>

    override fun observe(observer: OnConfigChangedListener<K>): Registration<K> {
        synchronized(listeners) {
            listeners += observer
            return Registration(this, observer)
        }
    }

    override fun removeObserver(observer: OnConfigChangedListener<K>) {
        synchronized(listeners) {
            listeners -= observer
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> configDelegate(key: K, defaultValue: T) = when (defaultValue) {
        is Int -> ConfigIntDelegate(key, defaultValue)
        is Long -> ConfigLongDelegate(key, defaultValue)
        is Boolean -> ConfigBooleanDelegate(key, defaultValue)
        is String -> ConfigStringDelegate(key, defaultValue)
        else -> throw IllegalArgumentException()
    }.also { properties += it } as ConfigDelegate<T>

    /**
     * @author Artem Chepurnoy
     */
    abstract inner class ConfigDelegate<T : Any>(
        internal var key: K,
        @Volatile
        private var cur: T
    ) : ReadWriteProperty<Any?, T> {

        /**
         * Sets the value from a store, if exists, otherwise
         * keeps current value.
         */
        internal fun init(storeRead: StoreRead<K>) {
            cur = getFromStore(storeRead, key, cur)
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            if (isEditing) {
                if (value == cur) {
                    return // no need to change
                }

                cur = value
                changes.add(key)

                // Editor is guaranteed to be not null because
                // we are editing right now.
                putToStore(editor!!, key, cur)
            } else edit {
                // I hope people will still use the edit block for
                // more than one change.
                setValue(thisRef, property, value)
            }
        }

        override fun getValue(thisRef: Any?, property: KProperty<*>): T = cur

        internal abstract fun putToStore(storeWrite: StoreWrite<K>, key: K, value: T = cur)

        internal abstract fun getFromStore(storeRead: StoreRead<K>, key: K, value: T = cur): T

    }

    /**
     * @author Artem Chepurnoy
     */
    inner class ConfigIntDelegate internal constructor(key: K, cur: Int) :
        ConfigDelegate<Int>(key, cur) {

        override fun putToStore(storeWrite: StoreWrite<K>, key: K, value: Int) {
            storeWrite.putInt(key, value)
        }

        override fun getFromStore(storeRead: StoreRead<K>, key: K, value: Int): Int {
            return storeRead.getInt(key, value)
        }

    }

    /**
     * @author Artem Chepurnoy
     */
    inner class ConfigLongDelegate internal constructor(key: K, cur: Long) :
        ConfigDelegate<Long>(key, cur) {

        override fun putToStore(storeWrite: StoreWrite<K>, key: K, value: Long) {
            storeWrite.putLong(key, value)
        }

        override fun getFromStore(storeRead: StoreRead<K>, key: K, value: Long): Long {
            return storeRead.getLong(key, value)
        }

    }

    /**
     * @author Artem Chepurnoy
     */
    inner class ConfigBooleanDelegate internal constructor(key: K, cur: Boolean) :
        ConfigDelegate<Boolean>(key, cur) {

        override fun putToStore(storeWrite: StoreWrite<K>, key: K, value: Boolean) {
            storeWrite.putBoolean(key, value)
        }

        override fun getFromStore(storeRead: StoreRead<K>, key: K, value: Boolean): Boolean {
            return storeRead.getBoolean(key, value)
        }

    }

    /**
     * @author Artem Chepurnoy
     */
    inner class ConfigStringDelegate internal constructor(key: K, cur: String) :
        ConfigDelegate<String>(key, cur) {

        override fun putToStore(storeWrite: StoreWrite<K>, key: K, value: String) {
            storeWrite.putString(key, value)
        }

        override fun getFromStore(storeRead: StoreRead<K>, key: K, value: String): String {
            return storeRead.getString(key, value)
        }

    }

    /**
     * @author Artem Chepurnoy
     */
    interface OnConfigChangedListener<K> {
        fun onConfigChanged(keys: Set<K>)
    }

    /**
     * @author Artem Chepurnoy
     */
    class Registration<K>(
        private val config: Config<K, *>,
        private val listener: OnConfigChangedListener<K>
    ): ObservableRegistration {

        private var isRegistered = true

        override fun isRegistered(): Boolean = isRegistered

        override fun unregister() {
            if (isRegistered) {
                isRegistered = false
                config.removeObserver(listener)
            }
        }

    }

}