package com.joe.transactionsapp.business.data.network.abstraction

import com.joe.transactionsapp.business.domain.model.TransactionModel


interface ITransactionApiDataSource {

    suspend fun getAllTransactionsFromNet(): List<TransactionModel>
}