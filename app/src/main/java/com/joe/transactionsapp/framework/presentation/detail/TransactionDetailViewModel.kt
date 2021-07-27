package com.joe.transactionsapp.framework.presentation.detail

import androidx.lifecycle.SavedStateHandle
import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.business.domain.state.DataState
import com.joe.transactionsapp.business.domain.state.StateEvent
import com.joe.transactionsapp.business.interactors.TransactionInteractors
import com.joe.transactionsapp.framework.presentation.base.BaseViewModel
import com.joe.transactionsapp.framework.presentation.detail.state.TransactionStateEvent.*
import com.joe.transactionsapp.framework.presentation.detail.state.TransactionViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val SAFE_ARG_TRANSACTION_ID_SAVED_STATE_KEY = "transactionId"

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class TransactionDetailViewModel @Inject internal constructor(
    private val savedStateHandle: SavedStateHandle,
    private val transactionInteractors: TransactionInteractors
) : BaseViewModel<TransactionViewState>() {

    init {
        searchTransactionByIdInCache()
    }

    private fun searchTransactionByIdInCache() {
        val transactionId: String? =
            savedStateHandle.get<String>(SAFE_ARG_TRANSACTION_ID_SAVED_STATE_KEY)
        transactionId?.let {
            setStateEvent(SearchTransactionByIdEvent(it))
        }
    }

    override fun handleNewData(data: TransactionViewState) {
        data.let { viewState ->
            viewState.transaction?.let {
                setTransaction(it)
            }
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {

        val job: Flow<DataState<TransactionViewState>?> = when (stateEvent) {

            is SearchTransactionByIdEvent -> {
                transactionInteractors.searchTransactionById(
                    stateEvent.transactionId,
                    stateEvent
                )
            }

            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent, job)
    }

    override fun initNewViewState(): TransactionViewState {
        return TransactionViewState()
    }

    private fun setTransaction(transaction: TransactionModel) {
        val update = getCurrentViewStateOrNew()
        update.transaction = transaction
        setViewState(update)
    }
}