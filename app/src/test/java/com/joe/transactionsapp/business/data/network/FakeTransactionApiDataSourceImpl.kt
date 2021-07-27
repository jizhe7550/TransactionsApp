package com.joe.transactionsapp.business.data.network

import com.joe.transactionsapp.business.data.network.abstraction.ITransactionApiDataSource
import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.framework.datasource.network.entity.TransactionApiEntity
import com.joe.transactionsapp.framework.datasource.network.mapper.ApiMapper

class FakeTransactionApiDataSourceImpl
constructor(
    private val transactionsData: HashMap<String, TransactionApiEntity>,
    private val mapper:ApiMapper
) : ITransactionApiDataSource {

    override suspend fun getAllTransactionsFromNet(): List<TransactionModel> {
        val transactionApiEntityList = ArrayList(transactionsData.values) as List<TransactionApiEntity>
        return mapper.entityListToTransactionModelList(transactionApiEntityList)

    }
}