package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.AccountDetailRecyclerRowBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList
import com.example.intertech_account.model.api_model.main_page.landmark.Landmark
import com.github.mikephil.charting.data.PieEntry

class AccountDetailAdapter() : RecyclerView.Adapter<AccountDetailAdapter.AccountDetailHolder>() {
    private var titles: ArrayList<String> = arrayListOf()
    private var values: ArrayList<String> = arrayListOf()
    class AccountDetailHolder(val binding: AccountDetailRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountDetailHolder {
        val binding = AccountDetailRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AccountDetailHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountDetailHolder, position: Int) {
        holder.binding.recyclerviewtextview.text = titles[position]
        holder.binding.recyclerviewtextview1.text = values[position]
    }

    fun addAccount(titles: ArrayList<String>, values: ArrayList<String>){
        this.titles.addAll(titles)
        this.values.addAll(values)
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return values.size
    }
}
