package com.geektech.tasks.ext

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    toast.show()
}