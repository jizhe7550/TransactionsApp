package com.joe.transactionsapp.business.data.cache

import com.joe.transactionsapp.business.data.cache.abstraction.ITransactionCacheDataSource
import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.framework.datasource.cache.entity.TransactionCacheEntity
import com.joe.transactionsapp.framework.datasource.cache.mapper.CacheMapper

class FakeTransactionCacheDataSourceImpl
constructor(
    private val transactionsData: HashMap<String, TransactionCacheEntity>,
    private val mapper: CacheMapper
) : ITransactionCacheDataSource {

    override suspend fun insertAllTransactions(transactionList: List<TransactionModel>): LongArray {
        val results = LongArray(transactionList.size)
        for ((index, transaction) in transactionList.withIndex()) {
            results[index] = 1
            val transactionCacheEntity = mapper.mapToEntity(transaction)
            transactionsData[transaction.id] = transactionCacheEntity
        }
        return results
    }

    override suspend fun getAllTransactions(): List<TransactionModel> {
        val entityList = ArrayList(transactionsData.values) as List<TransactionCacheEntity>
        return mapper.entityListToTransactionModelList(entityList)

    }

    override suspend fun searchTransactionById(id: String): TransactionModel? {
        val entity = transactionsData[id]!!
        return mapper.mapFromEntity(entity)
    }
}