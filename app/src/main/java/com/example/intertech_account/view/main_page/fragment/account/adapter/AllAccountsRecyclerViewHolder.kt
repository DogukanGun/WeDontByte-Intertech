package com.example.intertech_account.view.main_page.fragment.account.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.intertech_account.databinding.AllAccountsRecyclerviewGraphRowBinding
import com.example.intertech_account.databinding.AllAccountsRecyclerviewRowBinding
import com.example.intertech_account.databinding.AllAccountsRecyclerviewTitleRowBinding

sealed class AllAccountsRecyclerViewHolder (binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {


    // TODO başlıklar için değişecek

    class TitleViewHolder(private val binding: AllAccountsRecyclerviewTitleRowBinding) :
        AllAccountsRecyclerViewHolder(binding) {

        fun getBind():AllAccountsRecyclerviewTitleRowBinding{
            return binding
        }
    }



    class AccountViewHolder(private val binding: AllAccountsRecyclerviewRowBinding) :
        AllAccountsRecyclerViewHolder(binding) {
        fun bind(account: AllAccountsRecyclerItem.Title) {
            binding.hesapIsmiTv.text = account.title
        }
        fun getBind():AllAccountsRecyclerviewRowBinding{
            return binding
        }
    }


    //TODO en baştaki grafik için değişecek

    class GraphViewHolder(private val binding: AllAccountsRecyclerviewGraphRowBinding) :
        AllAccountsRecyclerViewHolder(binding) {

        fun getBind():AllAccountsRecyclerviewGraphRowBinding{
            return binding
        }

    }
}