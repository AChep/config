package com.artemchep.cfg.ui.activities.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artemchep.cfg.IPresenter
import com.artemchep.cfg.IView
import com.artemchep.cfg.IViewApi

/**
 * @author Artem Chepurnoy
 */
abstract class ActivityBase<VAPI : IViewApi, V : IView<VAPI, P>, P : IPresenter<VAPI>> : AppCompatActivity() {

    abstract val view: V

    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = lastCustomNonConfigurationInstance as P? ?: createPresenter()

        // Connect view and presenter with
        // each other
        view.presenter = presenter
        presenter.view = view as VAPI
    }

    abstract fun createPresenter(): P

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun onStop() {
        presenter.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.view = null
        super.onDestroy()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any = presenter

}