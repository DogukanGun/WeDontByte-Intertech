package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.AllAccountsRecyclerviewRowBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList

class AllAccountsAdapter(var allAccounts: ArrayList<GetAccountList>): RecyclerView.Adapter<AllAccountsAdapter.AllAccountsHolder>()  {
    private val EMPTY_ITEM = 0
    private val NORMAL_ITEM = 1

    open class AllAccountsHolder(val binding:AllAccountsRecyclerviewRowBinding): RecyclerView.ViewHolder(binding.root){

    }
    inner class TitleViewHolder(binding:AllAccountsRecyclerviewRowBinding): AllAccountsHolder(binding){

    }

    fun deleteAccount(position: Int){
        allAccounts.removeAt(position)
        notifyItemRemoved(position)

    }
    fun addAccount(item:Array<GetAccountList>){
        item.sortBy { account -> account.currency }
        var AllAccountsArrayList : ArrayList<GetAccountList> = arrayListOf()

        for (i in 0 until item.size)
        {
            if(i == 0)AllAccountsArrayList.add(GetAccountList(isBlocked = false,"maltepe","birinci",false,"Title",0.15,00.10,1500.2,1800.5,1600.5,1850.0,"benimHesabım","TR1159465168416516841634623",false,88.50))

            AllAccountsArrayList.add(item[i])
            if(i < item.size-1 && item[i+1].currency != item[i].currency ){
                AllAccountsArrayList.add(GetAccountList(isBlocked = false,"maltepe","birinci",false,"Title",0.15,00.10,1500.2,1800.5,1600.5,1850.0,"benimHesabım","TR1159465168416516841634623",false,88.50))
            }
        }
        allAccounts=AllAccountsArrayList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllAccountsHolder {
        if(viewType == 1) {
            val binding = AllAccountsRecyclerviewRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return AllAccountsHolder(binding)
        }
        else{
            val binding = AllAccountsRecyclerviewRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return TitleViewHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: AllAccountsHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                holder.binding.ibanTv.text = ""
                holder.binding.bakiyeNoTv.text =""
                holder.binding.hesapIsmiTv.text =""
                holder.binding.subeIsmiTv.text =allAccounts[position+1].currency.toString()+" Hesaplarım"
                holder.binding.subeIsmiTv.textSize = 30.toFloat()
            }
            else -> {
                holder.binding.bakiyeNoTv.text = allAccounts[position].balance.toString() + allAccounts[position].currency
                holder.binding.ibanTv.text = allAccounts[position].iban
                holder.binding.subeIsmiTv.text = allAccounts[position].branch
                holder.binding.hesapIsmiTv.text = allAccounts[position].accountName
            }
        }


    }

    override fun getItemViewType(position: Int): Int {
        return if (allAccounts[position].currency == "Title"){
            EMPTY_ITEM
        }else{
            NORMAL_ITEM
        }
    }


    override fun getItemCount(): Int {

        return allAccounts.size
    }
}