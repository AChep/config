package com.artemchep.cfg.presenters

import android.content.Context
import com.artemchep.config.Config
import com.artemchep.cfg.Cfg
import com.artemchep.cfg.contracts.IMainPresenter
import com.artemchep.cfg.contracts.IMainViewApi
import com.artemchep.cfg.models.Note
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
        Cfg.edit(context) {
            Cfg.note = Note().apply {
                timestamp = System.currentTimeMillis()
                text = note
            }
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

    private fun updateNote() = view?.setNote(Cfg.note.text)

    override fun setReadOnly(isReadOnly: Boolean) {
        // Update the config immediately; this will call the
        // `onConfigChanged` callback!
        Cfg.edit(context) {
            Cfg.isReadOnly = isReadOnly
        }
    }

}
