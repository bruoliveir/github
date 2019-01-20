package com.example.github.presentation.extensions

import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import com.example.github.R

fun AlertDialog.Builder.show(@StringRes messageResId: Int, onDismiss: () -> Unit): AlertDialog =
    setMessage(context.getString(messageResId))
        .setPositiveButton(context.getString(R.string.dialog_ok)) { dialog, _ -> dialog.dismiss() }
        .setOnDismissListener { onDismiss() }
        .create()
        .apply { show() }