package com.joe.transactionsapp.framework.presentation.list.state

import com.joe.transactionsapp.business.domain.state.StateEvent

sealed class TransactionListSateEvent: StateEvent {

    object GetAllTransactionsInCacheEvent: TransactionListSateEvent() {

        override fun errorInfo(): String {
            return "Error get all transactions in cache."
        }

        override fun eventName(): String {
            return "GetAllTransactionsInCacheEvent"
        }

        override fun shouldDisplayProgressBar() = true
    }

    object GetAllTransactionsFromNetEvent: TransactionListSateEvent() {

        override fun errorInfo(): String {
            return "Error get all transactions from net."
        }

        override fun eventName(): String {
            return "GetAllTransactionsFromNetEvent"
        }

        override fun shouldDisplayProgressBar() = true
    }
}