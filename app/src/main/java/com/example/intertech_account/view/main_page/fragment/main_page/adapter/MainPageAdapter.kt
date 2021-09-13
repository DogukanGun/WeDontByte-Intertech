package com.example.intertech_account.view.main_page.fragment.main_page.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.HomeScreenTransactionRowBinding
import com.example.intertech_account.model.api_model.get_account_transaction_list.GetAccountTransactionList
import com.example.intertech_account.resources.common_variables.Constant

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
            var year = transactions[position].date.substringBefore("T").substring(0,4)
            var month = transactions[position].date.substringBefore("T").substring(5,7)
            var day = transactions[position].date.substringBefore("T").substring(8,10)
            holder.binding.aliciIsmi.text = transactions[position].userCode
            holder.binding.explanation.text =transactions[position].explanation
            //holder.binding.dateValue.text = "$day.$month.$year"
            //holder.binding.dateName.text = transactions[position].date.substringAfter("T")
            //TODO transaction modeli içine
            holder.binding.amountValue.text = Constant.amountFormatter.format(transactions[position].amount)+" "+transactions[position].currencyCode
            if(transactions[position].amount < 0){
                holder.binding.amountValue.setTextColor(Color.RED)
            }else{
                holder.binding.amountValue.setTextColor(Color.BLACK)
            }
        }


    }


    override fun getItemCount(): Int {
        return transactions.size
    }
}

