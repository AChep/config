package com.artemchep.cfg.ui.dialogs

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.artemchep.cfg.BuildConfig
import com.artemchep.cfg.R

fun createDependenciesDialog(context: Context): AlertDialog {
    val deps = BuildConfig.DEPENDENCIES
    return AlertDialog.Builder(context)
        .setTitle(R.string.deps)
        .setPositiveButton(android.R.string.ok) { dialog, _ -> }
        .setItems(deps) { dialog, item -> }
        .create()
}
