package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.AllAccountsRecyclerviewRowBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList

class AllAccountsAdapter(var allAccounts: ArrayList<GetAccountList>): RecyclerView.Adapter<AllAccountsAdapter.AllAccountsHolder>()  {
    private val EMPTY_ITEM = 0
    private val NORMAL_ITEM = 1
    private var originalallAccounts:ArrayList<GetAccountList> = ArrayList()


    //TL Hesaplarının başa gelmesi için Hashmap
    private val roles: HashMap<String, Int> = hashMapOf(
        "TRY" to 0,
        "USD" to 1,
        "EUR" to 2,
        "GBP" to 3

    )

    open class AllAccountsHolder(val binding:AllAccountsRecyclerviewRowBinding): RecyclerView.ViewHolder(binding.root){

    }
    inner class TitleViewHolder(binding:AllAccountsRecyclerviewRowBinding): AllAccountsHolder(binding){

    }

    //Boş gelen RecyclerView doldurmak için fonksiyon

    fun addAccount(item:Array<GetAccountList>){
        val AllAccountsArrayList : ArrayList<GetAccountList> = rearrangeList(item.toCollection(ArrayList()))

        originalallAccounts.addAll(AllAccountsArrayList)
        allAccounts.addAll(originalallAccounts)
        notifyDataSetChanged()

    }

    // Kullanıcının sahip olduğu hesap türler(TL, Dolar, Euro,vs.)
    fun getCurrencyList():List<String>{
        val names=ArrayList<String>()
        for(item in originalallAccounts){
            if(item.currency !in names)
                names.add(item.currency)
        }
        names.remove("Title")
        return names
    }


    // Başlıkların seçili olan hesaplara göre oluşturulup tekrar sıralanması

    fun rearrangeList(item:ArrayList<GetAccountList>):ArrayList<GetAccountList>{
        val comparator = Comparator { o1: GetAccountList, o2: GetAccountList ->
            return@Comparator roles[o1.currency]!! - roles[o2.currency]!!
        }
        item.sortWith(comparator)
        //item.sortBy { account -> account.currency }
        val AllAccountsArrayList : ArrayList<GetAccountList> = arrayListOf()

        for (i in 0 until item.size)
        {
            //Add first Label
            if(i == 0){AllAccountsArrayList.add(GetAccountList(isBlocked = false,
                "maltepe",
                "birinci",
                false,
                "Title",
                0.15,
                00.10,
                1500.2,
                1800.5,
                1600.5,
                1850.0,
                "benimHesabım",
                "TR1159465168416516841634623",
                false,88.50))}

            AllAccountsArrayList.add(item[i])

            //Add labels
            if(i < item.size-1 && item[i+1].currency != item[i].currency ){

                AllAccountsArrayList.add(GetAccountList(isBlocked = false,
                    "maltepe",
                    "birinci",
                    false,
                    "Title",
                    0.15,
                    00.10,
                    1500.2,
                    1800.5,
                    1600.5,
                    1850.0,
                    "benimHesabım",
                    "TR1159465168416516841634623",
                    false,88.50))
            }
        }
        return AllAccountsArrayList
    }


    //Seçienleri belirleme

    fun modifyAccount(statesOfCurriencies:HashMap<String,Int>){
        val AllAccountsArrayList : ArrayList<GetAccountList> = arrayListOf()
        allAccounts=ArrayList()
        for(item in originalallAccounts){
            if(statesOfCurriencies[item.currency] == 1)
                AllAccountsArrayList.add(item)
        }
        allAccounts= rearrangeList(AllAccountsArrayList)
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


    //Row bilgilerinin düzenlenmesi

    override fun onBindViewHolder(holder: AllAccountsHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                holder.binding.ibanTv.text = ""
                holder.binding.bakiyeNoTv.text =""
                holder.binding.hesapIsmiTv.text =""
                holder.binding.subeIsmiTv.text = allAccounts[position+1].currency+" Hesaplarım"
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


    // Title veya element ayrışımı için gerekli
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