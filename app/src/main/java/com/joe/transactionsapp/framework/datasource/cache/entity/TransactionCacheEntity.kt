package com.joe.transactionsapp.framework.datasource.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionCacheEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val credit: Double,
    val debit: Double,
    val summary: String,
    val timestamp:Long,
)
