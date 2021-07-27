package com.joe.transactionsapp.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.joe.transactionsapp.business.data.cache.abstraction.ITransactionCacheDataSource
import com.joe.transactionsapp.business.data.cache.implementation.TransactionCacheDataSourceImpl
import com.joe.transactionsapp.framework.datasource.cache.mapper.CacheMapper
import com.joe.transactionsapp.framework.datasource.cache.abstraction.ITransactionDaoService
import com.joe.transactionsapp.framework.datasource.cache.database.TransactionDao
import com.joe.transactionsapp.framework.datasource.cache.implementation.TransactionDaoServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideTransactionCacheDataSource(
        transactionDaoService: ITransactionDaoService,
    ): ITransactionCacheDataSource {
        return TransactionCacheDataSourceImpl(transactionDaoService)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideTransactionDaoService(
        transactionDao: TransactionDao,
        cacheMapper: CacheMapper
    ): ITransactionDaoService {
        return TransactionDaoServiceImpl(transactionDao,cacheMapper)
    }

}