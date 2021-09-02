package com.example.intertech_account.view.main_page.fragment.transaction_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.HomeScreenTransactionRowBinding

class TransactionListAdapter(var allTransactions: MutableList<String>): RecyclerView.Adapter<TransactionListAdapter.HomeScreenTransactionHolder>()  {
    private val EMPTY_ITEM = 0
    private val NORMAL_ITEM = 1

    open class HomeScreenTransactionHolder(val binding: HomeScreenTransactionRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenTransactionHolder {
        val binding = HomeScreenTransactionRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeScreenTransactionHolder(binding)

    }

    override fun onBindViewHolder(holder: HomeScreenTransactionHolder, position: Int) {

       /* holder.binding.explanation.text = allTransactions[0].transactions[position].explanation
        holder.binding.dateValue.text = allTransactions[0].transactions[position].date + allTransactions[0].transactions[position].time
        holder.binding.amountValue.text = allTransactions[0].transactions[position].amount.toString()


        */
    }



    override fun getItemCount(): Int {

        return allTransactions.size
    }
}