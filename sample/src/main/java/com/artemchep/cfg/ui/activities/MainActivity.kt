package com.artemchep.cfg.ui.activities

import android.os.Bundle
import com.artemchep.cfg.R
import com.artemchep.cfg.contracts.IMainPresenter
import com.artemchep.cfg.contracts.IMainView
import com.artemchep.cfg.contracts.IMainViewApi
import com.artemchep.cfg.presenters.MainPresenter
import com.artemchep.cfg.ui.activities.base.ActivityBase
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.min
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @author Artem Chepurnoy
 */
class MainActivity : ActivityBase<IMainViewApi, IMainView, IMainPresenter>(), IMainView {

    override val view: IMainView = this

    private val noteDelegate = object : ReadOnlyProperty<Any, String> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String = noteField.text.toString()
    }

    override fun createPresenter(): IMainPresenter = MainPresenter(applicationContext, noteDelegate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarView)

        roLayout.setOnClickListener { roSwitch.toggle() }
        roSwitch.setOnCheckedChangeListener { _, isChecked -> presenter.setReadOnly(isChecked) }
    }

    override fun setReadOnly(isReadOnly: Boolean) {
        roSwitch.isChecked = isReadOnly
        noteField.isEnabled = !isReadOnly
    }

    override fun setNote(note: String) {
        noteField.apply {
            setText(note)
        }
    }

}
