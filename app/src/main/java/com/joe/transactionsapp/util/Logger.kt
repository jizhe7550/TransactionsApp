package com.joe.transactionsapp.util

import android.util.Log
import com.joe.transactionsapp.util.Constants.DEBUG
import com.joe.transactionsapp.util.Constants.TAG

var isUnitTest = false

fun cLog(message: String?) {
    if (DEBUG && !isUnitTest) {
        message?.let {
            Log.d(TAG, "$message")
        }
    } else {
        println("$message")
    }

}

fun String.cLogD() {
    Log.d(TAG, this)
}
