package com.example.intertech_account.view.main_page.fragment.user_information.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.UserInformationRecyclerviewRowBinding


class UserInformationAdapter(val userInformation:ArrayList<String>):RecyclerView.Adapter<UserInformationAdapter.UserInformationHolder>()  {


    class UserInformationHolder(val binding:UserInformationRecyclerviewRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserInformationHolder {
        val binding = UserInformationRecyclerviewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserInformationHolder(binding)
    }

    override fun onBindViewHolder(holder: UserInformationHolder, position: Int) {
//        holder.binding.text = "Definition {$position}"
        //burada bir label yarattigini ve isminin labelX oldugunu dusun
        //icine bir sey yazmak icin binding.labelX yapicaksin
        //burada position hangi hucrenin yuklendigini gosterir

    }

    
    override fun getItemCount(): Int {
        return userInformation.size
    }
}

