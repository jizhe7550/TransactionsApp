package com.joe.transactionsapp.di

import com.joe.transactionsapp.BuildConfig
import com.joe.transactionsapp.business.data.network.abstraction.ITransactionApiDataSource
import com.joe.transactionsapp.business.data.network.implementation.TransactionApiDataSourceImpl
import com.joe.transactionsapp.framework.datasource.network.abstraction.ITransactionApiService
import com.joe.transactionsapp.framework.datasource.network.implementation.TransactionApiServiceImpl
import com.joe.transactionsapp.framework.datasource.network.mapper.ApiMapper
import com.joe.transactionsapp.framework.datasource.network.retrofit.ApiRetrofit
import com.joe.transactionsapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = when (BuildConfig.DEBUG) {
                        true -> HttpLoggingInterceptor.Level.BODY
                        false -> HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyRetrofit(retrofit: Retrofit.Builder): ApiRetrofit {
        return retrofit
            .build()
            .create(ApiRetrofit::class.java)
    }

    @Singleton
    @Provides
    fun provideTransactionApiDataSource(
        transactionApiService: ITransactionApiService,
    ): ITransactionApiDataSource {
        return TransactionApiDataSourceImpl(transactionApiService)
    }

    @Singleton
    @Provides
    fun provideTransactionApiService(
        apiRetrofit: ApiRetrofit,
        apiMapper: ApiMapper
    ): ITransactionApiService {
        return TransactionApiServiceImpl(apiRetrofit,apiMapper)
    }
}