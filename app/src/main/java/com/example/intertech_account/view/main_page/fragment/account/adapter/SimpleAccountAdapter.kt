package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.HomeScreenTransactionRowBinding
import com.example.intertech_account.databinding.SimpleAccountRecyclerviewRowBinding
import com.example.intertech_account.model.api_model.get_account_transaction_list.GetAccountTransactionList

class SimpleAccountAdapter :RecyclerView.Adapter<SimpleAccountAdapter.SimpleAccountHolder>()   {
        private  var transactions:Array<GetAccountTransactionList> = emptyArray()

        class SimpleAccountHolder(val binding: SimpleAccountRecyclerviewRowBinding): RecyclerView.ViewHolder(binding.root){

        }


        // Ana sayfadaki transactionların response geldikten sonra eklenmesi

        fun addList(transactions: Array<GetAccountTransactionList>){
            this.transactions=transactions
            notifyDataSetChanged()
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleAccountHolder {
            val binding = SimpleAccountRecyclerviewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return SimpleAccountHolder(binding)
        }


        //Transactionların doldurulması

        override fun onBindViewHolder(holder: SimpleAccountHolder, position: Int) {

            if (transactions.isNotEmpty()){

                holder.binding.explanation.text =transactions[position].explanation
                holder.binding.dateValue.text = transactions[position].date
                holder.binding.amountValue.text = transactions[position].amount.toString()
            }


        }


        override fun getItemCount(): Int {
            return transactions.size
        }
}