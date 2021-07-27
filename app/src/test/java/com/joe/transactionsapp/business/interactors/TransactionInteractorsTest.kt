package com.joe.transactionsapp.business.interactors

import com.joe.transactionsapp.business.data.cache.abstraction.ITransactionCacheDataSource
import com.joe.transactionsapp.business.data.network.abstraction.ITransactionApiDataSource
import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.business.domain.state.DataState
import com.joe.transactionsapp.business.interactors.TransactionInteractors.Companion.GET_ALL_TRANSACTIONS_SUCCESS
import com.joe.transactionsapp.business.interactors.TransactionInteractors.Companion.SEARCH_TRANSACTION_SUCCESS
import com.joe.transactionsapp.di.DependencyContainer
import com.joe.transactionsapp.framework.presentation.detail.state.TransactionStateEvent.SearchTransactionByIdEvent
import com.joe.transactionsapp.framework.presentation.detail.state.TransactionViewState
import com.joe.transactionsapp.framework.presentation.list.state.TransactionListSateEvent.GetAllTransactionsFromNetEvent
import com.joe.transactionsapp.framework.presentation.list.state.TransactionListViewState
import org.junit.Assert.assertEquals
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertIterableEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@InternalCoroutinesApi
class TransactionInteractorsTest {

    private var dependencyContainer: DependencyContainer = DependencyContainer()
    private var transactionInteractors: TransactionInteractors? = null
    private lateinit var transactionCacheDataSource: ITransactionCacheDataSource
    private lateinit var transactionApiDataSource: ITransactionApiDataSource

    @AfterEach
    fun after() {
        transactionInteractors = null
    }


    @BeforeAll
    fun setUp() {
        dependencyContainer.build()
        transactionCacheDataSource = dependencyContainer.transactionCacheDataSource
        transactionApiDataSource = dependencyContainer.transactionApiDataSource
        transactionInteractors = TransactionInteractors(
            transactionApiDataSource = transactionApiDataSource,
            transactionCacheDataSource = transactionCacheDataSource,
        )
    }

    @Test
    fun `get transactions success from network and save to database`() = runBlocking {

        // Mock api source list and interactors return list
        val sourceList = transactionApiDataSource.getAllTransactionsFromNet()
        val fetchList = ArrayList<TransactionModel>()

        // call getAllTransactionsFromNet
        transactionInteractors?.getAllTransactionsFromNet(
            stateEvent = GetAllTransactionsFromNetEvent
        )?.collect(object : FlowCollector<DataState<TransactionListViewState>?> {
            override suspend fun emit(value: DataState<TransactionListViewState>?) {
                // check api success
                assertEquals(
                    value?.stateMessage?.response?.message,
                    GET_ALL_TRANSACTIONS_SUCCESS
                )
                // save success data
                fetchList.addAll(ArrayList(value?.data?.transactionList))
            }
        })

        // check lists are same
        assertIterableEquals(fetchList, sourceList)

        // check database
        var databaseList = transactionCacheDataSource.getAllTransactions()
        assertIterableEquals(fetchList, databaseList)
    }

    @Test
    fun `search transaction by id from database success`() = runBlocking {
        // check database data
        val databaseList = transactionCacheDataSource.getAllTransactions()
        // already inserted from fun `get transactions success from network and save to database`()
        assertEquals(19, databaseList.size)

        // get index 5 transactionModel(transactionCacheEntity) in database
        val transactionModel5 = databaseList[5]
        val id = transactionModel5.id


        transactionInteractors?.searchTransactionById(
            id,
            SearchTransactionByIdEvent(id)
        )?.collect(object : FlowCollector<DataState<TransactionViewState>?> {
            override suspend fun emit(value: DataState<TransactionViewState>?) {
                // check query success
                assertEquals(
                    value?.stateMessage?.response?.message,
                    SEARCH_TRANSACTION_SUCCESS
                )


                val transactionModel = value?.data?.transaction!!

                // check searchTransactionById as expected.
                assertEquals(transactionModel5,transactionModel)
                assertEquals(transactionModel5.id,transactionModel.id)
            }
        })
    }

    // TODO
    @Test
    fun `get transactions failed from network and save to database`(){

    }
    
    // TODO
    @Test
    fun `search transaction by id from database failed`(){

    }
}