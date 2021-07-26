package com.joe.transactionsapp.business.interactors

import com.joe.transactionsapp.business.data.cache.CacheResponseHandler
import com.joe.transactionsapp.business.data.network.ApiResponseHandler
import com.joe.transactionsapp.business.data.network.abstraction.ITransactionApiDataSource
import com.joe.transactionsapp.business.data.util.safeApiCall
import com.joe.transactionsapp.business.data.util.safeCacheCall
import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.business.domain.state.*
import com.joe.transactionsapp.framework.presentation.list.state.TransactionListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class TransactionInteractors(
    private val transactionApiDataSource: ITransactionApiDataSource,
) {

    fun getAllTransactionsFromNet(
        stateEvent: StateEvent
    ): Flow<DataState<TransactionListViewState>?> = flow {

        val apiResult = safeApiCall(Dispatchers.IO) {
            transactionApiDataSource.getAllTransactionsFromNet()
        }

        val response =
            object : ApiResponseHandler<TransactionListViewState, List<TransactionModel>>(
                response = apiResult,
                stateEvent = stateEvent
            ) {
                override suspend fun handleSuccess(resultObj: List<TransactionModel>): DataState<TransactionListViewState>? {
                    var message: String? =
                        SEARCH_ALL_TRANSACTIONS_SUCCESS
                    var uiComponentType: UIComponentType? = UIComponentType.None
                    if (resultObj.isEmpty()) {
                        message =
                            SEARCH_TRANSACTIONS_NO_MATCHING_RESULTS
                        uiComponentType = UIComponentType.Toast
                    }
                    return DataState.data(
                        response = Response(
                            message = message,
                            uiComponentType = uiComponentType as UIComponentType,
                            messageType = MessageType.Success
                        ),
                        data = TransactionListViewState(
                            transactionList = ArrayList(resultObj)
                        ),
                        stateEvent = stateEvent
                    )
                }
            }.getResult()

        emit(response)
    }

    companion object {
        const val INSERT_TRANSACTION_SUCCESS = "Successfully inserted new transaction."
        const val INSERT_TRANSACTION_FAILED = "Failed to insert new transaction."
        const val SEARCH_ALL_TRANSACTIONS_SUCCESS = "Successfully search all transactions."
        const val SEARCH_TRANSACTIONS_NO_MATCHING_RESULTS =
            "There are no transactions that match that query."
        const val GET_RATE_FROM_NET_SUCCESS = "Successfully get rate from net."
        const val PLEASE_INPUT_ALL_THE_FILL = "Please input all the values"
        const val SEARCH_TRANSACTION_SUCCESS = "Successfully search transactions."
        const val SEARCH_TRANSACTION_FAILED = "Failed to search this transaction."
    }
}