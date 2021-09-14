package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.R
import com.example.intertech_account.databinding.AllAccountsRecyclerviewGraphRowBinding
import com.example.intertech_account.databinding.AllAccountsRecyclerviewRowBinding
import com.example.intertech_account.databinding.AllAccountsRecyclerviewTitleRowBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList
import com.example.intertech_account.resources.common_variables.Constant.amountFormatter
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.text.DecimalFormat
import kotlin.reflect.full.memberProperties


class AllAccountsAdapter(var allAccounts: ArrayList<GetAccountList>): RecyclerView.Adapter<AllAccountsRecyclerViewHolder>()  {
    private val ITEM_GRAPH = 0
    private val ITEM_TITLE = 1
    private val ITEM_ACCOUNT = 2
    private val DEFAULT_POSITIONING = 0
    private val DESCENDING_POSITIONING = 1
    private val ASCENDING_POSITIONING = 2
    private var positioningCriteria = 0
    companion object{
        private var sortButtonClick=3
    }
    private var graphState = 0
    private var totalBalanceForPieChart = 0.0
    private var totalBalanceChecker = true
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
        "ALL" to "Lek",
        "AFN" to "؋",
        "ARS" to "$",
        "AWG" to "ƒ",
        "AUD" to "$",
        "AZN" to "₼",
        "BSD" to "$",
        "BBD" to "$",
        "BYN" to "Br",
        "BZD" to "BZ$",
        "BMD" to "$",
        "BOB" to "\$b",
        "BAM" to "KM",
        "BWP" to "P",
        "BGN" to "лв",
        "BRL" to "R$",
        "BND" to "$",
        "KHR" to "៛",
        "CAD" to "$",
        "KYD" to "$",
        "CLP" to "$",
        "CNY" to "¥",
        "COP" to "$",
        "CRC" to "₡",
        "HRK" to "kn",
        "CUP" to "₱",
        "CZK" to "Kč",
        "DKK" to "kr",
        "DOP" to "RD$",
        "XCD" to "$",
        "EGP" to "£",
        "SVC" to "$",
        "EUR" to "€",
        "FKP" to "£",
        "FJD" to "$",
        "GHS" to "¢",
        "GIP" to "£",
        "GTQ" to "Q",
        "GGP" to "£",
        "GYD" to "$",
        "HNL" to "L",
        "HKD" to "$",
        "HUF" to "Ft",
        "ISK" to "kr",
        "INR" to "",
        "IDR" to "Rp",
        "IRR" to "﷼",
        "IMP" to "£",
        "ILS" to "₪",
        "JMD" to "J$",
        "JPY" to "¥",
        "JEP" to "£",
        "KZT" to "лв",
        "KPW" to "₩",
        "KRW" to "₩",
        "KGS" to "лв",
        "LAK" to "₭",
        "LBP" to "£",
        "LRD" to "$",
        "MKD" to "ден",
        "MYR" to "RM",
        "MUR" to "₨",
        "MXN" to "$",
        "MNT" to "₮",
        "MZN" to "MT",
        "NAD" to "$",
        "NPR" to "₨",
        "ANG" to "ƒ",
        "NZD" to "$",
        "NIO" to "C$",
        "NGN" to "₦",
        "NOK" to "kr",
        "OMR" to "﷼",
        "PKR" to "₨",
        "PAB" to "B/.",
        "PYG" to "Gs",
        "PEN" to "S/.",
        "PHP" to "₱",
        "PLN" to "zł",
        "QAR" to "﷼",
        "RON" to "lei",
        "RUB" to "₽",
        "SHP" to "£",
        "SAR" to "﷼",
        "RSD" to "Дин.",
        "SCR" to "₨",
        "SGD" to "$",
        "SBD" to "$",
        "SOS" to "S",
        "ZAR" to "R",
        "LKR" to "₨",
        "SEK" to "kr",
        "CHF" to "CHF",
        "SRD" to "$",
        "SYP" to "£",
        "TWD" to "NT$",
        "THB" to "฿",
        "TTD" to "TT$",
        "TVD" to "$",
        "UAH" to "₴",
        "GBP" to "£",
        "USD" to "$",
        "UYU" to "\$U",
        "UZS" to "лв",
        "VEF" to "Bs",
        "VND" to "₫",
        "YER" to "﷼",
        "ZWD" to "Z$",
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
                if(roles.containsKey(o1.currency) && roles.containsKey(o2.currency)){
                    return@Comparator roles[o1.currency]!! - roles[o2.currency]!!
                }
                else if(roles.containsKey(o1.currency) && !roles.containsKey(o2.currency)){
                    return@Comparator roles[o1.currency]!! - 5
                }
                else if(!roles.containsKey(o1.currency) && roles.containsKey(o2.currency)){
                    return@Comparator 5 - roles[o2.currency]!!
                }
                else{
                    return@Comparator 0
                }
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
                holder.getBind().bakiyeNoTv.text =
                    amountFormatter.format(allAccounts[position].balance) + " " + currencySigns[allAccounts[position].currency]
                holder.getBind().ibanTv.text = allAccounts[position].iban
                holder.getBind().subeIsmiTv.text = allAccounts[position].branch
                holder.getBind().hesapIsmiTv.text = allAccounts[position].accountName

                if (positioningCriteria != DEFAULT_POSITIONING) {
                    if (!allAccounts[position].currency.equals("TRY"))
                        holder.getBind().bakiyeCevirTl.text =
                            "(" + String.format("%.1f", allAccounts[position].balanceAsTRY) + " ₺)"
                    else
                        holder.getBind().bakiyeCevirTl.text = ""
                } else {
                    holder.getBind().bakiyeCevirTl.text = ""
                }

            }
            is AllAccountsRecyclerViewHolder.TitleViewHolder -> {
                holder.getBind().textViewTitleRow.text =
                    "${allAccounts[position + 1].currency} Hesaplarım"
            }
            is AllAccountsRecyclerViewHolder.GraphViewHolder -> {
                if (!totalBalanceChecker) {
                    for (pos in 0..pieEntries.size - 1) {
                        pieEntries.get(pos).y += (totalBalanceForPieChart / pieEntries.size).toFloat()
                    }
                }
                piechart = drawingPieChart(holder.getBind(), pieEntries)

                //SETUP PIE ANIMATION
                if (graphState == 0) {
                    piechart.animateXY(1000, 1000)
                    graphState = 1
                }
                holder.getBind().sortingRadioButton.setOnClickListener {

                    for (pos in 0..pieEntries.size - 1) {
                        pieEntries.get(pos).y -= (totalBalanceForPieChart / pieEntries.size).toFloat()
                    }


                    sortButtonClick += 1
                    if (sortButtonClick > 2) {
                        sortButtonClick = 0 //ARTAN
                        holder.getBind().sortingRadioButton.background = ContextCompat.getDrawable(
                            holder.getBind().sortingRadioButton.context,
                            R.drawable.qr_radio_button_not_selected
                        )
                        holder.getBind().sortingRadioButton.foreground = ContextCompat.getDrawable(
                            holder.getBind().sortingRadioButton.context,
                            R.drawable.filter
                        )
                        holder.getBind().sortingTextView.apply {
                            holder.getBind().sortingTextView.text =
                                context.getString(R.string.default_value)
                        }

                        setPositioningCriteria(0)
                        modifyAccount(currencyStates)
                    }
                    if (sortButtonClick < 2) { //AZALAN
                        holder.getBind().sortingRadioButton.background = ContextCompat.getDrawable(
                            holder.getBind().sortingRadioButton.context,
                            R.drawable.qr_radio_button_selected
                        )
                        holder.getBind().sortingRadioButton.foreground = ContextCompat.getDrawable(
                            holder.getBind().sortingRadioButton.context,
                            R.drawable.descending
                        )
                        holder.getBind().sortingTextView.apply {
                            holder.getBind().sortingTextView.text =
                                context.getString(R.string.descending)
                        }

                        if (sortButtonClick == 0) { //ARTAN
                            holder.getBind().sortingRadioButton.background =
                                ContextCompat.getDrawable(
                                    holder.getBind().sortingRadioButton.context,
                                    R.drawable.qr_radio_button_selected
                                )
                            holder.getBind().sortingRadioButton.foreground =
                                ContextCompat.getDrawable(
                                    holder.getBind().sortingRadioButton.context,
                                    R.drawable.ascending
                                )
                            holder.getBind().sortingTextView.apply {
                                holder.getBind().sortingTextView.text =
                                    context.getString(R.string.ascending)
                            }

                            setPositioningCriteria(2)
                            modifyAccount(currencyStates)
                        } else { //AZALAN
                            holder.getBind().sortingRadioButton.background =
                                ContextCompat.getDrawable(
                                    holder.getBind().sortingRadioButton.context,
                                    R.drawable.qr_radio_button_selected
                                )
                            holder.getBind().sortingRadioButton.foreground =
                                ContextCompat.getDrawable(
                                    holder.getBind().sortingRadioButton.context,
                                    R.drawable.descending
                                )
                            holder.getBind().sortingTextView.apply {
                                holder.getBind().sortingTextView.text =
                                    context.getString(R.string.descending)
                            }

                            setPositioningCriteria(1)
                            modifyAccount(currencyStates)
                        }
                    } else { //DEFAULT
                        holder.getBind().sortingRadioButton.background = ContextCompat.getDrawable(
                            holder.getBind().sortingRadioButton.context,
                            R.drawable.qr_radio_button_selected
                        )
                        holder.getBind().sortingRadioButton.foreground = ContextCompat.getDrawable(
                            holder.getBind().sortingRadioButton.context,
                            R.drawable.filter
                        )
                        holder.getBind().sortingTextView.apply {
                            holder.getBind().sortingTextView.text =
                                context.getString(R.string.default_value)
                        }

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
        var retString = ""
        for (prop in GetAccountList::class.memberProperties) {
            retString+="${prop.name}!${prop.get(allAccounts[position])}?"
        }

        return  retString


    }

    // TODO PirChart içini dinamik olarak seçilen şeye göre dolur veya PieChartta seçilen hesapları altta getir !!!!

    private fun drawingPieChart(binding: AllAccountsRecyclerviewGraphRowBinding,pieEntries:ArrayList<PieEntry>):PieChart
    {

        //GET PIE CHART COMPONENT FROM XML
        val intertechPieChart : PieChart = binding.allAccountsPieChart

        intertechPieChart.description.isEnabled = false
        intertechPieChart.legend.isEnabled = false


        //SETUP PIE CHART COLORS
        val pieDataSet = PieDataSet(pieEntries, "")
        pieDataSet.setColors(
            Color.rgb(51,86,127),
            Color.rgb(65, 108, 159),
            Color.rgb(76, 125, 183),
            Color.rgb(138, 163, 204),
            Color.rgb(185, 198, 221),
            Color.rgb(204, 213, 230),
            Color.rgb(57, 162, 219),
            Color.rgb(5,55,66),
            Color.rgb(39, 102, 120),
            Color.rgb(121, 163, 177),
            Color.rgb(147, 181, 198),
            Color.rgb(162, 219, 250),
            Color.rgb(73, 84, 100),
            Color.rgb(181, 191, 202),
            Color.rgb(147, 181, 198),
            Color.rgb(158, 158, 158),
            Color.rgb(129, 212, 250),

        )
        intertechPieChart.setEntryLabelTextSize(18f)


        //SETTING UP PIE DATA INTO PieData
        val pieData = PieData(pieDataSet)


        //IF YOU WANT TO HIDE THE ENTRIES, MAKE SET THIS AS ENABLE
        //intertechPieChart.legend.isEnabled = false


        intertechPieChart.setOnChartValueSelectedListener(object :OnChartValueSelectedListener{
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                for (pos in 0..pieEntries.size - 1) {
                    pieEntries.get(pos).y -= (totalBalanceForPieChart / pieEntries.size).toFloat()
                }
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
                for (pos in 0..pieEntries.size - 1) {
                    pieEntries.get(pos).y -= (totalBalanceForPieChart / pieEntries.size).toFloat()
                }
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

        if(totalBalanceChecker){
            for (pos in 0..pieEntries.size - 1) {
                totalBalanceForPieChart += pieEntries.get(pos).y
            }
            totalBalanceForPieChart /= 2
            for (pos in 0..pieEntries.size - 1) {
                Log.d("Info","Girdiii")
                pieEntries.get(pos).y += (totalBalanceForPieChart / pieEntries.size).toFloat()
            }
            totalBalanceChecker = false
        }


        //val totalBalanceString =totalBalanceForPieChart.toString()
        val totalBalanceString =amountFormatter.format(totalBalanceForPieChart)

        //intertechPieChart.apply { intertechPieChart.centerText = context.getString(R.string.total_balance) + "\n%.2f ".format(totalBalanceForPieChart)+"₺" }
        intertechPieChart.apply { intertechPieChart.centerText = context.getString(R.string.total_balance) + "\n" + totalBalanceString+"₺" }
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
            false,88.50,"","","","","",0.0)
        return x

    }
    fun setPositioningCriteria(criteria: Int){
        positioningCriteria = criteria
    }

}