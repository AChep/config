package com.artemchep.config.observable

/**
 * @author Artem Chepurnoy
 */
interface ObservableRegistration {

    /**
     * `true` if the observer is added to an observable,
     * `false` otherwise.
     */
    fun isRegistered(): Boolean

    /**
     * Removes the observer, does nothing if
     * called second times.
     */
    fun unregister()

}
