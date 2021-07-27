package com.joe.transactionsapp.framework.presentation.detail

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.joe.transactionsapp.R
import com.joe.transactionsapp.business.domain.state.StateMessageCallback
import com.joe.transactionsapp.databinding.FragmentTransactionDetailBinding
import com.joe.transactionsapp.framework.presentation.base.BaseFragment
import com.joe.transactionsapp.framework.presentation.detail.state.TransactionStateEvent.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TransactionDetailFragment :
    BaseFragment<TransactionDetailViewModel, FragmentTransactionDetailBinding>(R.layout.fragment_transaction_detail) {

    override val viewModel: TransactionDetailViewModel by viewModels()

    override fun setupChannel() {
    }

    override fun subscribeObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { viewState ->
            viewState?.apply {
                transaction?.let { transactionModel ->
                    binding.tvSummary.text = transactionModel.summary
                    binding.tvCredit.text = transactionModel.credit.toString()
                    binding.tvDebit.text = transactionModel.debit.toString()
                    binding.tvDate.text = transactionModel.transactionDate
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

    override fun initBinding(view: View): FragmentTransactionDetailBinding =
        FragmentTransactionDetailBinding.bind(view)

    override fun init() {
        setupUI()
    }

    private fun setupUI() {

    }

}