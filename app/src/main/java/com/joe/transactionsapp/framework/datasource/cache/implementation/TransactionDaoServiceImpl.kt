package com.joe.transactionsapp.framework.datasource.cache.implementation

import android.os.Build
import androidx.annotation.RequiresApi
import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.framework.datasource.cache.abstraction.ITransactionDaoService
import com.joe.transactionsapp.framework.datasource.cache.database.TransactionDao
import com.joe.transactionsapp.framework.datasource.cache.mapper.CacheMapper
import javax.inject.Inject
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.O)
@Singleton
class TransactionDaoServiceImpl
@Inject
constructor(
    private val transactionDao: TransactionDao,
    private val cacheMapper: CacheMapper,
) : ITransactionDaoService {

    override suspend fun insertAllTransactions(transactionList: List<TransactionModel>): LongArray {
        return transactionDao.insertAllTransactions(cacheMapper.transactionModelListToEntityList(transactionList))
    }

    override suspend fun getAllTransactions(): List<TransactionModel> {
        return cacheMapper.entityListToTransactionModelList(transactionDao.getAllTransactions())
    }

    override suspend fun searchTransactionById(id: String): TransactionModel {
        return cacheMapper.mapFromEntity(transactionDao.searchTransactionById(id))
    }


}