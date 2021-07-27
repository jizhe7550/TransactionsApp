package com.joe.transactionsapp.framework.datasource.network.implementation

import android.os.Build
import androidx.annotation.RequiresApi
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

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAllTransactionsFromNet(): List<TransactionModel> {
        return mapper.entityListToTransactionModelList(apiRetrofit.getTransactionsFromNet())
    }
}