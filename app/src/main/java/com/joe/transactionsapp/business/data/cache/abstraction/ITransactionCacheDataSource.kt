package com.joe.transactionsapp.business.data.cache.abstraction

import com.joe.transactionsapp.business.domain.model.TransactionModel


interface ITransactionCacheDataSource {

    suspend fun insertAllTransactions(transactionList:List<TransactionModel>)

    suspend fun getAllTransactions(): List<TransactionModel>

    suspend fun searchTransactionById(id:String):TransactionModel
}