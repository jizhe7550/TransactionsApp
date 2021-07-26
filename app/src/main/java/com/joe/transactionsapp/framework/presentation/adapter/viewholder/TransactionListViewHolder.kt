package com.joe.transactionsapp.framework.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.joe.transactionsapp.business.domain.model.TransactionModel
import com.joe.transactionsapp.databinding.ListItemTransactionBinding
import com.joe.transactionsapp.framework.presentation.adapter.TransactionListAdapter

class TransactionListViewHolder
constructor(
    private val mBinding: ListItemTransactionBinding,
    private val interaction: TransactionListAdapter.Interaction?
) : RecyclerView.ViewHolder(mBinding.root) {

    fun bind(transactionModel: TransactionModel) {
        mBinding.tvSummary.text = transactionModel.summary
        mBinding.tvCredit.text = transactionModel.credit.toString()
        mBinding.tvDebit.text = transactionModel.debit.toString()
        mBinding.tvDate.text = transactionModel.transactionDate

        mBinding.root.setOnClickListener {
            interaction?.onItemSelected(position = bindingAdapterPosition,transactionModel)
        }
    }
}