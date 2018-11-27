package com.artemchep.cfg.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artemchep.cfg.Cfg
import com.artemchep.cfg.R
import com.artemchep.cfg.models.Note
import com.artemchep.config.Config
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Artem Chepurnoy
 */
class MainActivity : AppCompatActivity(), Config.OnConfigChangedListener<String> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarView)

        roLayout.setOnClickListener { roSwitch.toggle() }
        roSwitch.setOnCheckedChangeListener { _, isChecked ->
            noteField.isEnabled = isChecked.not()
        }
    }

    override fun onStart() {
        super.onStart()
        Cfg.observe(this)
        updateNote()
        updateReadOnly()
    }

    override fun onPause() {
        super.onPause()
        // Save the current state on pause, cause this
        // is the only place we can trust.
        Cfg.edit(this) {
            Cfg.isReadOnly = roSwitch.isChecked
            Cfg.note = Note(
                text = noteField.text.toString(),
                timestamp = System.currentTimeMillis()
            )
        }
    }

    override fun onStop() {
        Cfg.removeObserver(this)
        super.onStop()
    }

    override fun onConfigChanged(keys: Set<String>) {
        if (Cfg.KEY_NOTE in keys) {
            updateNote()
        }

        if (Cfg.KEY_READ_ONLY in keys) {
            updateReadOnly()
        }
    }

    private fun updateNote() {
        noteField.setText(Cfg.note.text)
    }

    private fun updateReadOnly() {
        roSwitch.isChecked = Cfg.isReadOnly
    }

}
