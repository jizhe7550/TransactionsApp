package com.joe.transactionsapp.framework.datasource.network.entity

data class TransactionEntity(
    val credit: Int,
    val debit: Double,
    val id: String,
    val summary: String,
    val transactionDate: String
)