package com.example.intertech_account.view.main_page.fragment.main_page.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.HomeScreenTransactionRowBinding
import com.example.intertech_account.model.api_model.get_account_transaction_list.GetAccountTransactionList

class MainPageAdapter ()
    :RecyclerView.Adapter<MainPageAdapter.MainPageHolder>()   {
    private  var transactions:Array<GetAccountTransactionList> = emptyArray()


    class MainPageHolder(val binding:HomeScreenTransactionRowBinding ): RecyclerView.ViewHolder(binding.root){

    }


    // Ana sayfadaki transactionların response geldikten sonra eklenmesi

    fun addList(transactions: Array<GetAccountTransactionList>){
        this.transactions=transactions
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPageHolder {
        val binding = HomeScreenTransactionRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainPageHolder(binding)
    }


    //Transactionların doldurulması

    override fun onBindViewHolder(holder: MainPageHolder, position: Int) {
        if (transactions.isNotEmpty()){

            holder.binding.explanation.text =transactions[0].transactions[position].explanation
            holder.binding.dateValue.text = transactions[0].transactions[position].date + transactions[0].transactions[position].time
            holder.binding.amountValue.text = transactions[0].transactions[position].amount.toString()
        }


    }


    override fun getItemCount(): Int {
        return transactions.size
    }
}

