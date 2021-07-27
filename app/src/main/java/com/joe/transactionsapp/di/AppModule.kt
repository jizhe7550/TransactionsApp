package com.joe.transactionsapp.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.joe.transactionsapp.business.domain.util.DateUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @RequiresApi(Build.VERSION_CODES.O)
    @Singleton
    @Provides
    fun provideDateFormat(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm", Locale.getDefault())
    }

    @Singleton
    @Provides
    fun provideDateUtil(dateFormat: DateTimeFormatter): DateUtil {
        return DateUtil(
            dateFormat
        )
    }
}