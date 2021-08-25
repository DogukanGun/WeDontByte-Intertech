package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.AllAccountsRecyclerviewRowBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList

class AllAccountsAdapter(var allAccounts: Array<GetAccountList>): RecyclerView.Adapter<AllAccountsAdapter.AllAccountsHolder>()  {


    class AllAccountsHolder(val binding:AllAccountsRecyclerviewRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    fun addAccount(item:Array<GetAccountList>){
        allAccounts=item
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllAccountsHolder {
        val binding = AllAccountsRecyclerviewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AllAccountsHolder(binding)
    }

    override fun onBindViewHolder(holder: AllAccountsHolder, position: Int) {

        holder.binding.bakiyeNoTv.text = allAccounts[position].balance.toString()
        holder.binding.ibanTv.text = allAccounts[position].iban
        holder.binding.subeIsmiTv.text = allAccounts[position].branch
        holder.binding.hesapIsmiTv.text = allAccounts[position].accountName

    }


    override fun getItemCount(): Int {
        if (allAccounts.size>0){
            print("fuck")
        }
        return allAccounts.size
    }
}