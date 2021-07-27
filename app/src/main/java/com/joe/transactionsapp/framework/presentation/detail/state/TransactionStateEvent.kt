package com.joe.transactionsapp.framework.presentation.detail.state

import com.joe.transactionsapp.business.domain.state.StateEvent

sealed class TransactionStateEvent : StateEvent {

    class SearchTransactionByIdEvent(
        val transactionId: String
    ) : TransactionStateEvent() {

        override fun errorInfo(): String {
            return "Error search transaction by $transactionId"
        }

        override fun eventName(): String {
            return "SearchTransactionByIdEvent"
        }

        override fun shouldDisplayProgressBar() = true
    }
}