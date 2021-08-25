package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.AllAccountsRecyclerviewRowBinding

class AllAccountsAdapter(val allAccounts:ArrayList<String>): RecyclerView.Adapter<AllAccountsAdapter.AllAccountsHolder>()  {


    class AllAccountsHolder(val binding:AllAccountsRecyclerviewRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllAccountsHolder {
        val binding = AllAccountsRecyclerviewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AllAccountsHolder(binding)
    }

    override fun onBindViewHolder(holder: AllAccountsHolder, position: Int) {
//        holder.binding.text = "Definition {$position}"
        //burada bir label yarattigini ve isminin labelX oldugunu dusun
        //icine bir sey yazmak icin binding.labelX yapicaksin
        //burada position hangi hucrenin yuklendigini gosterir

    }


    override fun getItemCount(): Int {
        return allAccounts.size
    }
}