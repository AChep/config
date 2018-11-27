package com.artemchep.config.observable

/**
 * @author Artem Chepurnoy
 */
interface ObservableRegistration {

    fun isRegistered(): Boolean

    /**
     * Removes the observer, does nothing if
     * called second times.
     */
    fun unregister()

}
