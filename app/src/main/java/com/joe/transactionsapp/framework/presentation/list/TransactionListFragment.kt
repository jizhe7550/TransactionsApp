package com.joe.transactionsapp.framework.presentation.list

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.joe.transactionsapp.R
import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.business.domain.state.StateMessageCallback
import com.joe.transactionsapp.databinding.FragmentTransactionListBinding
import com.joe.transactionsapp.framework.presentation.util.gone
import com.joe.transactionsapp.framework.presentation.util.visible
import com.joe.transactionsapp.framework.presentation.adapter.TransactionListAdapter
import com.joe.transactionsapp.framework.presentation.common.TopSpacingItemDecoration
import com.joe.transactionsapp.framework.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionListFragment :
    BaseFragment<TransactionListViewModel, FragmentTransactionListBinding>(R.layout.fragment_transaction_list),
    TransactionListAdapter.Interaction {

    override val viewModel: TransactionListViewModel by viewModels()
    private var transactionListAdapter: TransactionListAdapter? = null

    override fun setupChannel() {
        viewModel.setupChannel()
    }

    override fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            viewState?.apply {
                transactionList?.let { transactionList->
                    if (transactionList.isEmpty()){
                        binding.rvTransitionList.gone()
                        binding.tvNoList.visible()
                    }else{
                        transactionListAdapter?.submitList(transactionList)
                        transactionListAdapter?.notifyDataSetChanged()
                        binding.rvTransitionList.visible()
                        binding.tvNoList.gone()
                    }
                }
            }
        })
        viewModel.shouldDisplayProgressBar.observe(viewLifecycleOwner, {
            uiController.displayProgressBar(it)
        })

        viewModel.stateMessage.observe(viewLifecycleOwner, { stateMessage ->
            stateMessage?.let {
                uiController.onResponseReceived(
                    response = stateMessage.response,
                    stateMessageCallback = object : StateMessageCallback {
                        override fun removeMessageFromStack() {
                            viewModel.clearStateMessage()
                        }
                    }
                )
            }
        })
    }

    override fun initBinding(view: View): FragmentTransactionListBinding =
        FragmentTransactionListBinding.bind(view)

    override fun init() {
        setupUI()
        viewModel.retrieveTransactionListFromNet()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupUI() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvTransitionList.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingDecorator = TopSpacingItemDecoration(20)
            addItemDecoration(topSpacingDecorator)

            transactionListAdapter = TransactionListAdapter(
                this@TransactionListFragment,
            )
            adapter = transactionListAdapter
        }
    }

    private fun navigateToDetailFragment() {
//        val transactionId = item.id
//        val direction = TransactionListFragmentDirections.actionTransactionListFragmentToTransactionDetailGraph(
//            transactionId
//        )
//        findNavController().navigate(direction)
    }

    override fun onItemSelected(position: Int, item: TransactionModel) {


   }

    override fun onDestroyView() {
        super.onDestroyView()
        transactionListAdapter = null
    }
}