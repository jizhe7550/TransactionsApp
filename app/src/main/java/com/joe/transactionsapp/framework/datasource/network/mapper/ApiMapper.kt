package com.joe.transactionsapp.framework.datasource.network.mapper

import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.business.domain.util.EntityMapper
import com.joe.transactionsapp.framework.datasource.network.entity.TransactionEntity
import javax.inject.Inject

class ApiMapper
@Inject
constructor() : EntityMapper<TransactionEntity, TransactionModel> {

    fun entityListToTransactionModelList(entities: List<TransactionEntity>): List<TransactionModel> {
        val list: ArrayList<TransactionModel> = ArrayList()
        for (entity in entities) {
            list.add(mapFromEntity(entity))
        }
        return list
    }

    fun transactionModelListToEntityList(TransactionModels: List<TransactionModel>): List<TransactionEntity> {
        val entities: ArrayList<TransactionEntity> = ArrayList()
        for (TransactionModel in TransactionModels) {
            entities.add(mapToEntity(TransactionModel))
        }
        return entities
    }

    override fun mapFromEntity(entity: TransactionEntity): TransactionModel {
        return TransactionModel(
            id = entity.id,
            summary = entity.summary,
            debit = entity.debit,
            credit = entity.credit,
            transactionDate = entity.transactionDate
        )
    }

    override fun mapToEntity(domainModel: TransactionModel): TransactionEntity {
        return TransactionEntity(
            id = domainModel.id,
            summary = domainModel.summary,
            debit = domainModel.debit,
            credit = domainModel.credit,
            transactionDate = domainModel.transactionDate
        )
    }
}
