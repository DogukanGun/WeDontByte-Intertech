package com.example.intertech_account.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.github.mikephil.charting.data.PieEntry

class GetAccountDetailWithChartsViewModel:ViewModel() {

    lateinit var getAccountModel:GetAccountModel

    fun createPieChartEntries():ArrayList<PieEntry>{
        if(getAccountModel != null){
            val getAccountModelList=getAccountModel.getAccountData.getAccountList
            val getAccountModelListGroupBy=getAccountModelList.groupBy { it.currency }
            val entries: ArrayList<PieEntry> = ArrayList()
            for(indexOfGetAccountModelListGroupBy in getAccountModelListGroupBy ){
                var totalAmount=0.0
                for(indexOfGetAccountModelListGroupByValue in indexOfGetAccountModelListGroupBy.value){
                    totalAmount+=indexOfGetAccountModelListGroupByValue.balance
                }
                val entry=PieEntry(indexOfGetAccountModelListGroupBy.key.length.toFloat(),indexOfGetAccountModelListGroupBy.key,totalAmount)
                entries.add(entry)
            }
            return entries
        }else{
            Log.d("PieChartError","empty getAccountModel")
            return emptyList<PieEntry>() as ArrayList<PieEntry>
        }
    }

    fun createLineChartEntries(){

    }

}