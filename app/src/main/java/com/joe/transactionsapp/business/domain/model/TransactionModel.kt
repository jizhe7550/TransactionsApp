package com.joe.transactionsapp.business.domain.model

data class TransactionModel(
    val credit: Double,
    val debit: Double,
    val id: String,
    val summary: String,
    val timestamp:Long,
    val transactionDate: String,
)
