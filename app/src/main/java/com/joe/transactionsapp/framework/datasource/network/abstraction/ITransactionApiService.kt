package com.joe.transactionsapp.framework.datasource.network.abstraction

import com.joe.transactionsapp.business.domain.model.TransactionModel


interface ITransactionApiService {

    suspend fun getAllTransactionsFromNet(): List<TransactionModel>
}