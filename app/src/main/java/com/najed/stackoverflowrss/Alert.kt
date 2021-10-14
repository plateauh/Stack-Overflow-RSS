package com.najed.stackoverflowrss

import android.app.AlertDialog
import android.content.Context

class Alert (private val context: Context, private val title: String, private val details: String) {
    init {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage("$details")
        dialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.cancel()
        }
        val alert = dialogBuilder.create()
        alert.setTitle(title)
        alert.show()
    }
}