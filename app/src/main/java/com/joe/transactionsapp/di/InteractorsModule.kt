package com.joe.transactionsapp.di

import com.joe.transactionsapp.business.data.network.abstraction.ITransactionApiDataSource
import com.joe.transactionsapp.business.interactors.TransactionInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorsModule {

    @Singleton
    @Provides
    fun provideTransactionInteractors(
        transactionApiDataSource: ITransactionApiDataSource,
    ): TransactionInteractors {
        return TransactionInteractors(
            transactionApiDataSource
        )
    }
}