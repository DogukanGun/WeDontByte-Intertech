package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.AccountDetailRecyclerRowBinding
import com.example.intertech_account.model.api_model.main_page.landmark.Landmark

class AccountDetailAdapter(val landmarkList: ArrayList<Landmark>) : RecyclerView.Adapter<AccountDetailAdapter.AccountDetailHolder>() {

    class AccountDetailHolder(val binding: AccountDetailRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountDetailHolder {
        val binding = AccountDetailRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AccountDetailHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountDetailHolder, position: Int) {
        holder.binding.recyclerviewtextview.text = landmarkList.get(position).name
        holder.binding.recyclerviewtextview1.text = landmarkList.get(position).kome




    }

    override fun getItemCount(): Int {
        return landmarkList.size
    }
}
