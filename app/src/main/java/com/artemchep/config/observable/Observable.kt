package com.artemchep.config.observable

/**
 * @author Artem Chepurnoy
 */
interface Observable<Observer> {

    fun observe(observer: Observer): ObservableRegistration

    fun removeObserver(observer: Observer)

}
