package com.artemchep.cfg

/**
 * @author Artem Chepurnoy
 */
interface IView<V : IViewApi, P : IPresenter<V>> : IViewApi {

    var presenter: P

}

/**
 * @author Artem Chepurnoy
 */
interface IViewApi {
}

/**
 * @author Artem Chepurnoy
 */
interface IPresenter<A : IViewApi> {

    var view: A?

    /**
     * Called every time the view starts, the view is guaranteed to be not null starting at this
     * method, until [onStop] is called.
     */
    fun onStart() {
    }

    fun onResume() {
    }

    fun onPause() {
    }

    /**
     * Called every time the view stops.
     * After this method, the view may be null.
     */
    fun onStop() {
    }

}
