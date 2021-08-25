package com.example.intertech_account.view.main_page.fragment.main_page.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.HomeScreenTransactionRowBinding
import com.example.intertech_account.databinding.UserInformationRecyclerviewRowBinding
import com.example.intertech_account.view.main_page.fragment.user_information.adapter.UserInformationAdapter

class MainPageAdapter (private var destinationAccountTiles: List<String>,
                       private var transactionNames: List<String>,
                       private var amounts: List<String>,
                       private var time: List<String>,
                       private var date: List<String>)
    :RecyclerView.Adapter<MainPageAdapter.MainPageHolder>()   {


    class MainPageHolder(val binding:HomeScreenTransactionRowBinding ): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPageHolder {
        val binding = HomeScreenTransactionRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MainPageHolder(binding)
    }

    override fun onBindViewHolder(holder: MainPageHolder, position: Int) {
        holder.binding.destinationValue.text = destinationAccountTiles[position]
        holder.binding.transactionValue.text = transactionNames[position]
        holder.binding.amountValue.text = amounts[position]
        holder.binding.timeValue.text = time[position]
        holder.binding.dateValue.text = date[position]
    }


    override fun getItemCount(): Int {
        return destinationAccountTiles.size
    }
}

