package com.joe.transactionsapp.util

import android.util.Log
import com.joe.transactionsapp.util.Constants.DEBUG

fun cLog(message: String?) {
    message?.let {
        if(!DEBUG){
            Log.d("MyApp", message)
        }
    }
}

fun String.cLogD() {
    Log.d("MyApp", this)
}
