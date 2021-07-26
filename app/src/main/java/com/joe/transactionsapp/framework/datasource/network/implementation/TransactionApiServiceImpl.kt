package com.joe.transactionsapp.framework.datasource.network.implementation

import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.framework.datasource.network.abstraction.ITransactionApiService
import com.joe.transactionsapp.framework.datasource.network.mapper.ApiMapper
import com.joe.transactionsapp.framework.datasource.network.retrofit.ApiRetrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionApiServiceImpl
@Inject
constructor(
    private val apiRetrofit: ApiRetrofit,
    private val mapper: ApiMapper,
) : ITransactionApiService {

    override suspend fun getAllTransactionsFromNet(): List<TransactionModel> {
        return mapper.entityListToTransactionModelList(apiRetrofit.getTransactionsFromNet())
    }
}