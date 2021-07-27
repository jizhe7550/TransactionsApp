package com.joe.transactionsapp.framework.datasource.network.entity

data class TransactionApiEntity(
    val credit: Double,
    val debit: Double,
    val id: String,
    val summary: String,
    val transactionDate: String
)