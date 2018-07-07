package com.artemchep.cfg.presenters

import android.content.Context
import com.artemchep.config.Config
import com.artemchep.config.extensions.editWithConfig
import com.artemchep.cfg.Cfg
import com.artemchep.cfg.contracts.IMainPresenter
import com.artemchep.cfg.contracts.IMainViewApi
import kotlin.properties.ReadOnlyProperty

/**
 * @author Artem Chepurnoy
 */
class MainPresenter(
        private val context: Context,
        noteDelegate: ReadOnlyProperty<Any, String>
) : IMainPresenter, Config.OnConfigChangedListener<String> {

    private val note by noteDelegate

    override var view: IMainViewApi? = null

    override fun onResume() {
        super.onResume()
        Cfg.addListener(this)
        updateReadOnly()
        updateNote()
    }

    override fun onPause() {
        Cfg.removeListener(this)
        Cfg.editWithConfig(context) {
            this.note = this@MainPresenter.note
        }
        super.onPause()
    }

    override fun onConfigChanged(keys: Set<String>) {
        keys.forEach { key ->
            when (key) {
                Cfg.KEY_READ_ONLY -> updateReadOnly()
                Cfg.KEY_NOTE -> updateNote()
            }
        }
    }

    private fun updateReadOnly() = view?.setReadOnly(Cfg.isReadOnly)

    private fun updateNote() = view?.setNote(Cfg.note)

    override fun setReadOnly(isReadOnly: Boolean) {
        // Update the config immediately; this will call the
        // `onConfigChanged` callback!
        Cfg.editWithConfig(context) {
            this.isReadOnly = isReadOnly
        }
    }

}
