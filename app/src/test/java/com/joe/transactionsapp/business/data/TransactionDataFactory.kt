package com.joe.transactionsapp.business.data

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.joe.transactionsapp.business.domain.util.DateUtil
import com.joe.transactionsapp.framework.datasource.cache.entity.TransactionCacheEntity
import com.joe.transactionsapp.framework.datasource.network.entity.TransactionApiEntity

class TransactionDataFactory(
    private val testClassLoader: ClassLoader,
    private val dateUtil: DateUtil
) {

    fun produceListOfApiTransactions(): List<TransactionApiEntity> {
        val jsonStr = getTransactionsFromFile("transaction_list.json")
        val array = JsonParser().parse(jsonStr).asJsonArray
        val transactionList: MutableList<TransactionApiEntity> = ArrayList()
        for (json in array) {
            val entity: TransactionApiEntity =
                Gson().fromJson(json, TransactionApiEntity::class.java)
            transactionList.add(entity)
        }
        return transactionList
    }

    fun produceHashMapOfApiTransactions(transactionApiEntityList: List<TransactionApiEntity>): HashMap<String, TransactionApiEntity> {
        val map = HashMap<String, TransactionApiEntity>()
        for (transaction in transactionApiEntityList) {
            map[transaction.id] = transaction
        }
        return map
    }

    fun produceEmptyListOfApiTransactions(): List<TransactionApiEntity> {
        return ArrayList()
    }

    /**
     * mock data from api
     */
    fun getTransactionsFromFile(fileName: String): String {
        return testClassLoader.getResource(fileName).readText()
    }

    /**
     * mock database to get all transaction
     */
    fun produceListOfCacheTransactions(): List<TransactionCacheEntity> {
        val jsonStr = getTransactionsFromFile("transaction_list.json")
        val array = JsonParser().parse(jsonStr).asJsonArray
        val transactionList: MutableList<TransactionCacheEntity> = ArrayList()
        for (json in array) {
            val entity: TransactionApiEntity =
                Gson().fromJson(json, TransactionApiEntity::class.java)
           val cacheEntity = TransactionCacheEntity(
                id = entity.id,
                credit = entity.credit,
                debit = entity.debit,
                summary = entity.summary,
                timestamp = dateUtil.convertOffsetDateTimeStringToTimestamp(entity.transactionDate),
            )
            transactionList.add(cacheEntity)
        }
        return transactionList
    }

    fun produceHashMapOfCacheTransactions(transactionCacheEntityList: List<TransactionCacheEntity>): HashMap<String, TransactionCacheEntity> {
        val map = HashMap<String, TransactionCacheEntity>()
        for (transaction in transactionCacheEntityList) {
            map[transaction.id] = transaction
        }
        return map
    }

    fun produceEmptyListOfCacheTransactions(): List<TransactionCacheEntity> {
        return ArrayList()
    }
}


















