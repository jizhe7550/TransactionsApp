package com.joe.transactionsapp.framework.datasource.network.retrofit

import com.joe.transactionsapp.framework.datasource.network.entity.TransactionApiEntity
import retrofit2.http.GET

interface ApiRetrofit {

    @GET("api/v1/transactions")
    suspend fun getTransactionsFromNet(): List<TransactionApiEntity>
}