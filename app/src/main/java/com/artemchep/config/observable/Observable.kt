package com.artemchep.config.observable

/**
 * @author Artem Chepurnoy
 */
interface Observable<Observer> {

    /**
     * Adds the given observer to the observers list. This means that the given observer will
     * receive all events and will never be automatically removed. You should manually call
     * [removeObserver] to stop observing this Observable.
     */
    fun observe(observer: Observer): ObservableRegistration

    /**
     * Removes the given observer from the observers list.
     */
    fun removeObserver(observer: Observer)

}
