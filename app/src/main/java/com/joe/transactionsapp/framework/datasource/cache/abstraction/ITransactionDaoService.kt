package com.joe.transactionsapp.framework.datasource.cache.abstraction

import com.joe.transactionsapp.business.domain.model.TransactionModel


interface ITransactionDaoService {

    suspend fun insertAllTransactions(transactionList:List<TransactionModel>): LongArray

    suspend fun getAllTransactions(): List<TransactionModel>

    suspend fun searchTransactionById(id: String): TransactionModel
}