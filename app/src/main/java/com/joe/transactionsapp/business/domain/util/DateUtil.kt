package com.joe.transactionsapp.business.domain.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Singleton

@Singleton
class DateUtil
constructor(
    private val dateFormat: DateTimeFormatter
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertOffsetDateTimeStringToStringDate(timeStr: String): String {
        return OffsetDateTime.parse(timeStr).format(dateFormat)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertOffsetDateTimeStringToTimestamp(datetimeStr: String): Long {
        return OffsetDateTime.parse(datetimeStr).toInstant().toEpochMilli()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTimestampToStringDate(timestamp: Long): String {
        return dateFormat.format(
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault()
            )
        );
    }

    // Date format: "2021-02-02T08:11:16+13:00"
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertStringDateToTimestamp(datetimeStr: String): Long {
        val parse = LocalDateTime.parse(datetimeStr, dateFormat)
        return LocalDateTime.from(parse).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}