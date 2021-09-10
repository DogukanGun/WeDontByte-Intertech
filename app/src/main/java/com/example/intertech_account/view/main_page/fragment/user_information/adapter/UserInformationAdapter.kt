package com.example.intertech_account.view.main_page.fragment.user_information.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.AllAccountsRecyclerviewRowBinding
import com.example.intertech_account.databinding.UserInformationRecyclerviewRowBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList
import com.example.intertech_account.view.main_page.fragment.account.adapter.AllAccountsAdapter


class UserInformationAdapter(private var userInfoTypes: List<String>, private var userValues: List<String>):RecyclerView.Adapter<UserInformationAdapter.UserInformationHolder>()  {


    class UserInformationHolder(val binding:UserInformationRecyclerviewRowBinding): RecyclerView.ViewHolder(binding.root){

    }
    fun addInfo(userInfo:List<String>, userValue:List<String>){

        userInfoTypes=userInfo
        userValues=userValue
        notifyDataSetChanged()


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInformationHolder {
        val binding = UserInformationRecyclerviewRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserInformationHolder(binding)
    }
    override fun onBindViewHolder(holder: UserInformationHolder, position: Int) {

        holder.binding.cardViewProcessName.text = userInfoTypes[position]
        holder.binding.cardViewBalance.text = userValues[position]


    }


    override fun getItemCount(): Int {
        return userValues.size
    }



}


