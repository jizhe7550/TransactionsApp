package com.joe.transactionsapp.business.data.cache.implementation

import com.joe.transactionsapp.business.data.cache.abstraction.ITransactionCacheDataSource
import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.framework.datasource.cache.abstraction.ITransactionDaoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionCacheDataSourceImpl
@Inject
constructor(
    private val transactionDaoService: ITransactionDaoService
) : ITransactionCacheDataSource {
    override suspend fun insertAllTransactions(transactionList: List<TransactionModel>): LongArray {
        return transactionDaoService.insertAllTransactions(transactionList)
    }

    override suspend fun getAllTransactions(): List<TransactionModel> {
        return transactionDaoService.getAllTransactions()
    }

    override suspend fun searchTransactionById(id: String): TransactionModel? {
        return transactionDaoService.searchTransactionById(id)
    }


}