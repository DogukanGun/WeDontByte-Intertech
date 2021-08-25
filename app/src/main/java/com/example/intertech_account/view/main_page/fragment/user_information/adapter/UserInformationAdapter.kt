package com.example.intertech_account.view.main_page.fragment.user_information.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.UserInformationRecyclerviewRowBinding


class UserInformationAdapter(private var processNames: List<String>, private var balances: List<String>):RecyclerView.Adapter<UserInformationAdapter.UserInformationHolder>()  {


    class UserInformationHolder(val binding:UserInformationRecyclerviewRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInformationHolder {
        val binding = UserInformationRecyclerviewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserInformationHolder(binding)
    }

    override fun onBindViewHolder(holder: UserInformationHolder, position: Int) {
        holder.binding.cardViewProcessName.text = processNames[position]
        holder.binding.cardViewBalance.text = balances[position]
    }

    
    override fun getItemCount(): Int {
        return balances.size
    }

}

