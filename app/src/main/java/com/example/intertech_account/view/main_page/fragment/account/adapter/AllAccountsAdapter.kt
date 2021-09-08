package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.graphics.Color
import android.graphics.Paint
import android.location.Criteria
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.R
import com.example.intertech_account.databinding.AllAccountsRecyclerviewGraphRowBinding
import com.example.intertech_account.databinding.AllAccountsRecyclerviewRowBinding
import com.example.intertech_account.databinding.AllAccountsRecyclerviewTitleRowBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList
import com.example.intertech_account.view.main_page.activity.MainActivity
import com.example.intertech_account.view.main_page.fragment.account.AllAccountsFragmentDirections
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import javax.crypto.spec.DESedeKeySpec


class AllAccountsAdapter(var allAccounts: ArrayList<GetAccountList>): RecyclerView.Adapter<AllAccountsRecyclerViewHolder>()  {
    private val ITEM_GRAPH = 0
    private val ITEM_TITLE = 1
    private val ITEM_ACCOUNT = 2
    private val DEFAULT_POSITIONING = 0
    private val DESCENDING_POSITIONING = 1
    private val ASCENDING_POSITIONING = 2
    private var positioningCriteria = 0
    private var graphState = 0
    private lateinit var piechart :PieChart
    private lateinit var pieEntries:ArrayList<PieEntry>
    private var originalallAccounts:ArrayList<GetAccountList> = ArrayList()
    private var currencyStates: HashMap<String,Int> =hashMapOf()


    //TL Hesaplarının başa gelmesi için Hashmap
    private val roles: HashMap<String, Int> = hashMapOf(
        "TRY" to 0,
        "USD" to 1,
        "EUR" to 2,
        "GBP" to 3,
        "CAD" to 4

    )
    private val currencySigns:HashMap<String,String> = hashMapOf(
        "TRY" to "₺",
        "USD" to "$",
        "EUR" to "€",
        "GBP" to "£",
        "CAD" to "$"
    )


    //Boş gelen RecyclerView doldurmak için fonksiyon
    fun addAccount(item:Array<GetAccountList>, pieChartEntries:ArrayList<PieEntry>){
        val AllAccountsArrayList : ArrayList<GetAccountList> = rearrangeList(item.toCollection(ArrayList()))


        originalallAccounts.clear()
        allAccounts.clear()
        //pieChartEntries.add(PieEntry(1000f,"USD"))

        pieEntries=pieChartEntries
        originalallAccounts.addAll(AllAccountsArrayList)
        allAccounts.addAll(originalallAccounts)
        val currencyNames =getCurrencyList()
        for(i in currencyNames){
            currencyStates[i] = 1
        }
        for (pieChartSlice in pieChartEntries){
            for(account in allAccounts)
                if(account.currency.equals(pieChartSlice.label)){
                    pieChartSlice.y += account.balanceAsTRY!!.toFloat()
                }
        }
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
        names.remove("Graph")
        return names

    }


    // Başlıkların seçili olan hesaplara göre oluşturulup tekrar sıralanması

    fun rearrangeList(item:ArrayList<GetAccountList>):ArrayList<GetAccountList>{
        val AllAccountsArrayList: ArrayList<GetAccountList> = arrayListOf()

        if(positioningCriteria == DEFAULT_POSITIONING) {
            val comparator = Comparator { o1: GetAccountList, o2: GetAccountList ->
                return@Comparator roles[o1.currency]!! - roles[o2.currency]!!
            }

            item.sortWith(comparator)
            //item.sortBy { account -> account.currency }


            for (i in 0 until item.size) {
                //Add first Label

                if (i == 0) {
                    AllAccountsArrayList.add(createDummyAccount("Graph"))
                    AllAccountsArrayList.add(createDummyAccount("Title"))
                }

                AllAccountsArrayList.add(item[i])

                //Add labels
                if (i < item.size - 1 && item[i + 1].currency != item[i].currency) {
                    AllAccountsArrayList.add(createDummyAccount("Title"))
                }
            }
            return AllAccountsArrayList
        }
        else if(positioningCriteria == DESCENDING_POSITIONING){

            item.sortWith(compareByDescending { it.balanceAsTRY })
            AllAccountsArrayList.add(createDummyAccount("Graph"))
            for (i in 0 until item.size) {
                AllAccountsArrayList.add(item[i])
            }
            return AllAccountsArrayList
        }
        else{
            item.sortWith(compareBy { it.balance })
            AllAccountsArrayList.add(createDummyAccount("Graph"))
            for (i in 0 until item.size) {
                AllAccountsArrayList.add(item[i])
            }
            return AllAccountsArrayList
        }
    }



    //Seçienleri belirleme

    fun modifyAccount(statesOfCurriencies:HashMap<String,Int>){
        if(positioningCriteria == DEFAULT_POSITIONING) {
            val AllAccountsArrayList: ArrayList<GetAccountList> = arrayListOf()
            allAccounts = ArrayList()
            for (item in originalallAccounts) {
                if (statesOfCurriencies[item.currency] == 1)
                    AllAccountsArrayList.add(item)
            }
            allAccounts = rearrangeList(AllAccountsArrayList)
            notifyDataSetChanged()
        }
        else{
            val AllAccountsArrayList: ArrayList<GetAccountList> = arrayListOf()
            allAccounts = ArrayList()
            for (item in originalallAccounts) {
                if(!item.currency.equals("Title")&& !item.currency.equals("Graph"))
                    AllAccountsArrayList.add(item)
            }
            allAccounts = rearrangeList(AllAccountsArrayList)
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllAccountsRecyclerViewHolder {
        return when(viewType){
            ITEM_TITLE -> AllAccountsRecyclerViewHolder.TitleViewHolder(
                AllAccountsRecyclerviewTitleRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ITEM_ACCOUNT -> AllAccountsRecyclerViewHolder.AccountViewHolder(
                AllAccountsRecyclerviewRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ITEM_GRAPH -> AllAccountsRecyclerViewHolder.GraphViewHolder(
                AllAccountsRecyclerviewGraphRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }

    }


    //Row bilgilerinin düzenlenmesi

    override fun onBindViewHolder(holder: AllAccountsRecyclerViewHolder, position: Int) {
        when (holder) {
            is AllAccountsRecyclerViewHolder.AccountViewHolder -> {
                    holder.getBind().bakiyeNoTv.text = allAccounts[position].balance.toString() +" "+ currencySigns[allAccounts[position].currency]
                    holder.getBind().ibanTv.text = allAccounts[position].iban
                    holder.getBind().subeIsmiTv.text = allAccounts[position].branch
                    holder.getBind().hesapIsmiTv.text = allAccounts[position].accountName

                    if(positioningCriteria != DEFAULT_POSITIONING)
                    {
                        if(!allAccounts[position].currency.equals("TRY"))
                            holder.getBind().bakiyeCevirTl.text = "(" + String.format("%.1f", allAccounts[position].balanceAsTRY) + " ₺)"
                        else
                            holder.getBind().bakiyeCevirTl.text = ""
                    }
                    else
                    {
                        holder.getBind().bakiyeCevirTl.text = ""
                    }

            }
            is AllAccountsRecyclerViewHolder.TitleViewHolder -> {
                holder.getBind().textViewTitleRow.text = "${allAccounts[position+1].currency} Hesaplarım"
            }
            is AllAccountsRecyclerViewHolder.GraphViewHolder -> {
                piechart= drawingPieChart(holder.getBind(),pieEntries)

                //SETUP PIE ANIMATION
                if(graphState == 0) {
                    piechart.animateXY(1000, 1000)
                    graphState=1
                }
                holder.getBind().allAccountsRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                    if (checkedId == R.id.ascending)
                    {
                        holder.getBind().ascending.background = ContextCompat.getDrawable(holder.getBind().ascending.context, R.drawable.qr_radio_button_selected)
                        holder.getBind().descending.background = ContextCompat.getDrawable(holder.getBind().descending.context, R.drawable.qr_radio_button_not_selected)
                        holder.getBind().defaultSort.background = ContextCompat.getDrawable(holder.getBind().defaultSort.context, R.drawable.qr_radio_button_not_selected)

                        setPositioningCriteria(2)
                        modifyAccount(currencyStates)
                    }
                    else if (checkedId == R.id.descending) {
                        holder.getBind().descending.background = ContextCompat.getDrawable(holder.getBind().descending.context, R.drawable.qr_radio_button_selected)
                        holder.getBind().ascending.background = ContextCompat.getDrawable(holder.getBind().ascending.context, R.drawable.qr_radio_button_not_selected)
                        holder.getBind().defaultSort.background = ContextCompat.getDrawable(holder.getBind().defaultSort.context, R.drawable.qr_radio_button_not_selected)

                        setPositioningCriteria(1)
                        modifyAccount(currencyStates)
                    }
                    else if (checkedId == R.id.defaultSort) {
                        holder.getBind().defaultSort.background = ContextCompat.getDrawable(holder.getBind().defaultSort.context, R.drawable.qr_radio_button_selected)
                        holder.getBind().ascending.background = ContextCompat.getDrawable(holder.getBind().ascending.context, R.drawable.qr_radio_button_not_selected)
                        holder.getBind().descending.background = ContextCompat.getDrawable(holder.getBind().descending.context, R.drawable.qr_radio_button_not_selected)

                        setPositioningCriteria(0)
                        modifyAccount(currencyStates)
                    }
                }
            }

        }


    }
    override fun getItemViewType(position: Int): Int {
        return if (allAccounts[position].currency == "Title"){
            ITEM_TITLE
        }
        else if(allAccounts[position].currency == "Graph"){
            ITEM_GRAPH
        }
        else{
            ITEM_ACCOUNT
        }
    }



    override fun getItemCount(): Int {

        return allAccounts.size
    }

    fun getPositionData(position: Int): String{
        return  allAccounts[position].accountName  + "?" +
                allAccounts[position].availableBalance + "?" +
                allAccounts[position].balance + "?" +
                allAccounts[position].balanceAsTRY + "?" +
                allAccounts[position].branch + "?" +
                allAccounts[position].currency + "?" +
                allAccounts[position].iban + "?" +
                allAccounts[position].interestRate + "?" +
                allAccounts[position].isBlocked + "?" +
                allAccounts[position].isClosed + "?" +
                allAccounts[position].name

    }

    // TODO PirChart içini dinamik olarak seçilen şeye göre dolur veya PieChartta seçilen hesapları altta getir !!!!

    private fun drawingPieChart(binding: AllAccountsRecyclerviewGraphRowBinding,pieEntries:ArrayList<PieEntry>):PieChart
    {
        //SET PIE ENTRIES (ENTER THE AMOUNT OF MONEY IN HERE)


        //GET PIE CHART COMPONENT FROM XML
        val intertechPieChart : PieChart = binding.allAccountsPieChart

        intertechPieChart.description.isEnabled = false
        intertechPieChart.legend.isEnabled = false


        //SETUP PIE CHART COLORS
        val pieDataSet = PieDataSet(pieEntries, "BURAYA RENKLERİN ANLAMLARINI YAZ <3")
        pieDataSet.setColors(
            Color.rgb(9, 83, 153),
            Color.rgb(200, 29, 71),
            Color.rgb(124, 124, 124),
            Color.rgb(229, 153, 173)
        )
        intertechPieChart.setEntryLabelTextSize(18f)


        //SETTING UP PIE DATA INTO PieData
        val pieData = PieData(pieDataSet)



        //IF YOU WANT TO HIDE THE ENTRIES, MAKE SET THIS AS ENABLE
        //intertechPieChart.legend.isEnabled = false

        //SET/HIDE DESCRIPTION
        intertechPieChart.description.isEnabled = true
        intertechPieChart.description.text = "THIS IS MY DESCRIPTION!"
        intertechPieChart.description.textAlign = Paint.Align.CENTER
        intertechPieChart.description.textSize = 18f
        intertechPieChart.description.textColor = Color.WHITE


        intertechPieChart.setOnChartValueSelectedListener(object :OnChartValueSelectedListener{
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                if(e!=null){
                    val entry=e as PieEntry
                    //TODO butonlarin tiklanmasi burada olacak
                    //TODO entry.label sana degeri verecek
                    for(item in currencyStates)
                        if (item.key.equals(entry.label)){
                            currencyStates[entry.label] = 1
                        }
                        else{
                            currencyStates[item.key] = 0
                        }
                    modifyAccount(currencyStates)

                }
            }

            override fun onNothingSelected() {
                //TODO("Not yet implemented")
                for(item in currencyStates){
                    currencyStates[item.key] = 1
                }
                modifyAccount(currencyStates)
            }

        })
        //CENTER SPACE INCREMENT/DECREMENT OF THE PIECHART
        intertechPieChart.holeRadius = 60f

        intertechPieChart.data = pieData

        pieData.setDrawValues(false)
        intertechPieChart.transparentCircleRadius=0f

        //SET TEXT IN THE MIDDLE OF THE PIECHART
        var totalBalance = 0.0
        for(pos in 0..pieEntries.size-1){
            totalBalance += pieEntries.get(pos).y
        }
        val totalBalanceString =totalBalance.toString()

        intertechPieChart.apply { intertechPieChart.centerText = context.getString(R.string.total_balance) + "\n%.2f ".format(totalBalance)+"₺" }
        intertechPieChart.setCenterTextColor(Color.BLACK)
        intertechPieChart.setCenterTextSize(18f)

        return intertechPieChart
    }

    private fun createDummyAccount(typeOfAccount:String):GetAccountList{
        val x = GetAccountList(isBlocked = false,
            "maltepe",
            "birinci",
            false,
            typeOfAccount,
            0.15,
            00.10,
            15000.2,
            1800.5,
            1600.5,
            1850.0,
            "benimHesabım",
            "TR1159465168416516841634623",
            false,88.50,null)
        return x

    }
    fun setPositioningCriteria(criteria: Int){
        positioningCriteria = criteria
    }
}