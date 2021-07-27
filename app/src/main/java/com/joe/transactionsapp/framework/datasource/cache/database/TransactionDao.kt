package com.joe.transactionsapp.framework.datasource.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.joe.transactionsapp.framework.datasource.cache.entity.TransactionCacheEntity

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTransactions(transactions: List<TransactionCacheEntity>):LongArray

    @Query("SELECT * FROM transactions ORDER BY timestamp")
    fun getAllTransactions(): List<TransactionCacheEntity>

    @Query("SELECT * FROM transactions WHERE id = :id")
    fun searchTransactionById(id: String): TransactionCacheEntity

}