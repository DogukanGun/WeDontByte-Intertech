package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.HomeScreenTransactionRowBinding
import com.example.intertech_account.databinding.SimpleAccountRecyclerviewRowBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList
import com.example.intertech_account.model.api_model.get_account_transaction_list.GetAccountTransactionList

class SimpleAccountAdapter() :RecyclerView.Adapter<SimpleAccountAdapter.SimpleAccountHolder>()  {
        private  var transactions:Array<GetAccountTransactionList> = emptyArray()
        private var transactionArrayList:ArrayList<GetAccountTransactionList> = arrayListOf()
        class SimpleAccountHolder(val binding: SimpleAccountRecyclerviewRowBinding): RecyclerView.ViewHolder(binding.root){

        }


        // Ana sayfadaki transactionların response geldikten sonra eklenmesi

        fun addList(transactions: Array<GetAccountTransactionList>){
            this.transactions=transactions
            transactionArrayList.addAll(transactions)
            notifyDataSetChanged()
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleAccountHolder {
            val binding = SimpleAccountRecyclerviewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return SimpleAccountHolder(binding)
        }


        //Transactionların doldurulması

        override fun onBindViewHolder(holder: SimpleAccountHolder, position: Int) {

            if (transactionArrayList.isNotEmpty()){

                holder.binding.explanation.text =transactionArrayList[position].explanation
                holder.binding.dateValue.text = transactionArrayList[position].date
                holder.binding.amountValue.text = transactionArrayList[position].amount.toString()
            }


        }


        override fun getItemCount(): Int {
            return transactionArrayList.size
        }

    private fun createDummyTransactionList(x: Double, d: String): GetAccountTransactionList {
        var x = GetAccountTransactionList("test",d,"test","test",x,122.2,
            "t","t","t","t","t","t","t",
            233.3,"t"
        )
        return x
    }

    private fun lastTenTransactions(item:ArrayList<GetAccountTransactionList>):ArrayList<GetAccountTransactionList>{
        val allTransactionsArrayList: ArrayList<GetAccountTransactionList> = arrayListOf()
        item.sortWith(compareByDescending { it.date})
        if(item.size >= 10) {
            for (i in 0 until 10) {
                allTransactionsArrayList.add(item[i])
            }
        }
        else{
            for(i in 0 until item.size){
                allTransactionsArrayList.add(item[i])
            }
        }

        return allTransactionsArrayList

    }

    fun sortTransactionsDefault(){
       transactionArrayList.clear()
        transactionArrayList.addAll(lastTenTransactions(transactions.toCollection(ArrayList<GetAccountTransactionList>())))
        notifyDataSetChanged()
    }



}

