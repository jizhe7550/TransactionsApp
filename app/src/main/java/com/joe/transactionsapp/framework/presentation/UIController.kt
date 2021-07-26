package com.joe.transactionsapp.framework.presentation

import com.joe.transactionsapp.business.domain.state.DialogInputCaptureCallback
import com.joe.transactionsapp.business.domain.state.Response
import com.joe.transactionsapp.business.domain.state.StateMessageCallback


interface UIController {

    fun displayProgressBar(isDisplayed: Boolean)

    fun hideSoftKeyboard()

    fun displayInputCaptureDialog(title: String, callback: DialogInputCaptureCallback)

    fun onResponseReceived(
        response: Response,
        stateMessageCallback: StateMessageCallback
    )

}


















