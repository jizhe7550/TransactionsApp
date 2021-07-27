package com.joe.transactionsapp.framework.datasource.cache.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.business.domain.util.DateUtil
import com.joe.transactionsapp.business.domain.util.EntityMapper
import com.joe.transactionsapp.framework.datasource.cache.entity.TransactionCacheEntity
import javax.inject.Inject

/**
 * Maps TransactionModel to TransactionCacheEntity or TransactionCacheEntity to TransactionModel.
 */
@RequiresApi(Build.VERSION_CODES.O)
class CacheMapper
@Inject
constructor(
    private val dateUtil: DateUtil
) : EntityMapper<TransactionCacheEntity, TransactionModel> {

    fun entityListToTransactionModelList(entities: List<TransactionCacheEntity>): List<TransactionModel> {
        val list: ArrayList<TransactionModel> = ArrayList()
        for (entity in entities) {
            list.add(mapFromEntity(entity))
        }
        return list
    }

    fun transactionModelListToEntityList(TransactionModels: List<TransactionModel>): List<TransactionCacheEntity> {
        val entities: ArrayList<TransactionCacheEntity> = ArrayList()
        for (TransactionModel in TransactionModels) {
            entities.add(mapToEntity(TransactionModel))
        }
        return entities
    }

    override fun mapFromEntity(entity: TransactionCacheEntity): TransactionModel {
        return TransactionModel(
            id = entity.id,
            credit = entity.credit,
            debit = entity.debit,
            summary = entity.summary,
            timestamp = entity.timestamp,
            transactionDate = dateUtil.convertTimestampToStringDate(entity.timestamp)
        )
    }

    override fun mapToEntity(domainModel: TransactionModel): TransactionCacheEntity {
        return TransactionCacheEntity(
            id = domainModel.id,
            credit = domainModel.credit,
            debit = domainModel.debit,
            summary = domainModel.summary,
            timestamp = domainModel.timestamp,
        )
    }
}







