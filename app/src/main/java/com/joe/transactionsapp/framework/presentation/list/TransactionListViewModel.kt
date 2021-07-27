package com.joe.transactionsapp.framework.presentation.list

import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.business.domain.state.DataState
import com.joe.transactionsapp.business.domain.state.StateEvent
import com.joe.transactionsapp.business.interactors.TransactionInteractors
import com.joe.transactionsapp.framework.presentation.base.BaseViewModel
import com.joe.transactionsapp.framework.presentation.list.state.TransactionListSateEvent.*
import com.joe.transactionsapp.framework.presentation.list.state.TransactionListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class TransactionListViewModel @Inject internal constructor(
    private val transactionInteractors: TransactionInteractors
) : BaseViewModel<TransactionListViewState>() {

    override fun handleNewData(data: TransactionListViewState) {
        data.let { viewState ->
            viewState.transactionList?.let { transactionList->
                setTransactionListData(transactionList)
            }
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {

        val job: Flow<DataState<TransactionListViewState>?> = when (stateEvent) {
            is GetAllTransactionsFromNetEvent -> {
                transactionInteractors.getAllTransactionsFromNet(stateEvent)
            }
            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent, job)
    }

    override fun initNewViewState(): TransactionListViewState {
        return TransactionListViewState()
    }

    fun retrieveTransactionListFromNet() {
        setStateEvent(GetAllTransactionsFromNetEvent)
    }

    private fun setTransactionListData(transactionList: ArrayList<TransactionModel>){
        val update = getCurrentViewStateOrNew()
        update.transactionList = transactionList
        setViewState(update)
    }

}