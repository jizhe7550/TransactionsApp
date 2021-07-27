package com.joe.transactionsapp.business.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionModel(
    val credit: Double,
    val debit: Double,
    val id: String,
    val summary: String,
    val timestamp:Long,
    val transactionDate: String,
):Parcelable{

}
