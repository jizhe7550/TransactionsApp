package com.joe.transactionsapp.framework.datasource.network.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.business.domain.util.DateUtil
import com.joe.transactionsapp.business.domain.util.EntityMapper
import com.joe.transactionsapp.framework.datasource.network.entity.TransactionApiEntity
import javax.inject.Inject
/**
 * Maps TransactionModel to TransactionEntity or TransactionEntity to TransactionModel.
 */
@RequiresApi(Build.VERSION_CODES.O)
class ApiMapper
@Inject
constructor(
    private val dateUtil: DateUtil
) : EntityMapper<TransactionApiEntity, TransactionModel> {

    fun entityListToTransactionModelList(entities: List<TransactionApiEntity>): List<TransactionModel> {
        val list: ArrayList<TransactionModel> = ArrayList()
        for (entity in entities) {
            list.add(mapFromEntity(entity))
        }
        return list
    }

    fun transactionModelListToEntityList(TransactionModels: List<TransactionModel>): List<TransactionApiEntity> {
        val entities: ArrayList<TransactionApiEntity> = ArrayList()
        for (TransactionModel in TransactionModels) {
            entities.add(mapToEntity(TransactionModel))
        }
        return entities
    }

    override fun mapFromEntity(entity: TransactionApiEntity): TransactionModel {
        return TransactionModel(
            id = entity.id,
            summary = entity.summary,
            debit = entity.debit,
            credit = entity.credit,
            transactionDate = dateUtil.convertOffsetDateTimeStringToStringDate(entity.transactionDate),
            timestamp = dateUtil.convertOffsetDateTimeStringToTimestamp(entity.transactionDate)
        )
    }

    override fun mapToEntity(domainModel: TransactionModel): TransactionApiEntity {
        TODO("Not yet implemented")
    }
}
