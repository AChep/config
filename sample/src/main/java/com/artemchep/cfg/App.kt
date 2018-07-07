package com.artemchep.cfg

import android.app.Application

/**
 * @author Artem Chepurnoy
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Cfg.init(this)
    }

}
