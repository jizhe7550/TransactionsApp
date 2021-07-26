package com.joe.transactionsapp.business.data.network.implementation

import com.joe.transactionsapp.business.data.network.abstraction.ITransactionApiDataSource
import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.framework.datasource.network.abstraction.ITransactionApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionApiDataSourceImpl
@Inject
constructor(
    private val transactionApiService: ITransactionApiService
): ITransactionApiDataSource {
    override suspend fun getAllTransactionsFromNet(): List<TransactionModel> {
       return transactionApiService.getAllTransactionsFromNet()
    }


}