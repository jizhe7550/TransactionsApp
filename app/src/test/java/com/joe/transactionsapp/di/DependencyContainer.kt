package com.joe.transactionsapp.di

import com.joe.transactionsapp.business.data.TransactionDataFactory
import com.joe.transactionsapp.business.data.cache.FakeTransactionCacheDataSourceImpl
import com.joe.transactionsapp.business.data.cache.abstraction.ITransactionCacheDataSource
import com.joe.transactionsapp.business.data.network.FakeTransactionApiDataSourceImpl
import com.joe.transactionsapp.business.data.network.abstraction.ITransactionApiDataSource
import com.joe.transactionsapp.business.domain.util.DateUtil
import com.joe.transactionsapp.framework.datasource.cache.mapper.CacheMapper
import com.joe.transactionsapp.framework.datasource.network.mapper.ApiMapper
import com.joe.transactionsapp.util.isUnitTest
import java.time.format.DateTimeFormatter
import java.util.*

class DependencyContainer {

    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm", Locale.getDefault())
    val dateUtil = DateUtil(dateFormat)
    val apiMapper = ApiMapper(dateUtil)
    val cacheMapper = CacheMapper(dateUtil)
    lateinit var transactionApiDataSource: ITransactionApiDataSource
    lateinit var transactionCacheDataSource: ITransactionCacheDataSource
    lateinit var transactionDataFactory: TransactionDataFactory

    init {
        isUnitTest = true // for Logger.kt
    }

    fun build() {
        this.javaClass.classLoader?.let { classLoader ->
            transactionDataFactory = TransactionDataFactory(classLoader,dateUtil)
        }
        transactionApiDataSource = FakeTransactionApiDataSourceImpl(
            transactionsData = transactionDataFactory.produceHashMapOfApiTransactions(
                transactionDataFactory.produceListOfApiTransactions()
            ),
            mapper = apiMapper
        )
        transactionCacheDataSource = FakeTransactionCacheDataSourceImpl(
            transactionsData = transactionDataFactory.produceHashMapOfCacheTransactions(
                transactionDataFactory.produceEmptyListOfCacheTransactions()
            ),
            mapper = cacheMapper
        )
    }

}

















