package com.example.intertech_account.view.main_page.fragment.account.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intertech_account.databinding.AllAccountsRecyclerviewGraphRowBinding
import com.example.intertech_account.databinding.AllAccountsRecyclerviewRowBinding
import com.example.intertech_account.databinding.AllAccountsRecyclerviewTitleRowBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.lang.ref.WeakReference

class AllAccountsAdapter(var allAccounts: ArrayList<GetAccountList>): RecyclerView.Adapter<AllAccountsRecyclerViewHolder>()  {
    private val ITEM_GRAPH = 0
    private val ITEM_TITLE = 1
    private val ITEM_ACCOUNT = 2

    private lateinit var pieEntries:ArrayList<PieEntry>
    private var originalallAccounts:ArrayList<GetAccountList> = ArrayList()



    //TL Hesaplarının başa gelmesi için Hashmap
    private val roles: HashMap<String, Int> = hashMapOf(
        "TRY" to 0,
        "USD" to 1,
        "EUR" to 2,
        "GBP" to 3

    )


    //Boş gelen RecyclerView doldurmak için fonksiyon

    fun addAccount(item:Array<GetAccountList>,pieChartEntries:ArrayList<PieEntry>){
        val AllAccountsArrayList : ArrayList<GetAccountList> = rearrangeList(item.toCollection(ArrayList()))
        pieEntries=pieChartEntries
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
        names.remove("Graph")
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
                "Graph",
                0.15,
                00.10,
                1500.2,
                1800.5,
                1600.5,
                1850.0,
                "benimHesabım",
                "TR1159465168416516841634623",
                false,88.50))
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
                holder.getBind().bakiyeNoTv.text = allAccounts[position].balance.toString() + allAccounts[position].currency
                holder.getBind().ibanTv.text = allAccounts[position].iban
                holder.getBind().subeIsmiTv.text = allAccounts[position].branch
                holder.getBind().hesapIsmiTv.text = allAccounts[position].accountName
            }
            is AllAccountsRecyclerViewHolder.TitleViewHolder -> {
                holder.getBind().textViewTitleRow.text = "${allAccounts[position+1].currency} Hesaplarım"
            }
            is AllAccountsRecyclerViewHolder.GraphViewHolder -> {
                drawingPieChart(holder.getBind(),pieEntries)
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



    // TODO PirChart içini dinamik olarak seçilen şeye göre dolur veya PieChartta seçilen hesapları altta getir !!!!

    private fun drawingPieChart(binding: AllAccountsRecyclerviewGraphRowBinding,pieEntries:ArrayList<PieEntry>)
    {
        //SET PIE ENTRIES (ENTER THE AMOUNT OF MONEY IN HERE)


        //GET PIE CHART COMPONENT FROM XML
        var intertechPieChart : PieChart = binding.allAccountsPieChart

        //SETUP PIE ANIMATION
        intertechPieChart.animateXY(1000,1000)

        //SETUP PIE CHART COLORS
        val pieDataSet = PieDataSet(pieEntries, "BURAYA RENKLERİN ANLAMLARINI YAZ <3")
        pieDataSet.setColors(
            Color.CYAN,
            Color.YELLOW,
            Color.RED,
            Color.GREEN
        )
        intertechPieChart.setEntryLabelTextSize(18f)


        //SETTING UP PIE DATA INTO PieData
        val pieData = PieData(pieDataSet)

        //SET TEXT IN THE MIDDLE OF THE PIECHART
        intertechPieChart.centerText = "CENTER TEXT <3"
        intertechPieChart.setCenterTextColor(Color.BLACK)
        intertechPieChart.setCenterTextSize(18f)

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
                print(e!!.data)
            }

            override fun onNothingSelected() {
                TODO("Not yet implemented")
            }

        })
        //CENTER SPACE INCREMENT/DECREMENT OF THE PIECHART
        intertechPieChart.holeRadius = 20f


        pieData.setDrawValues(true)
        intertechPieChart.data = pieData
    }
}