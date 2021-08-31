package com.example.intertech_account.view.main_page.fragment.account

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentAllAccountsBinding
import com.example.intertech_account.databinding.FragmentSimpleAccountBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.listener.OnChartGestureListener

class SimpleAccountFragment : Fragment() {

    private lateinit var binding: FragmentSimpleAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSimpleAccountBinding.inflate(layoutInflater)
        DrawingPieChart()
        return binding.root
    }

    private fun DrawingPieChart()
    {
        //SET PIE ENTRIES (ENTER THE AMOUNT OF MONEY IN HERE)
        val pieEntries = arrayListOf<PieEntry>()
        pieEntries.add(PieEntry(1000.0F))
        pieEntries.add(PieEntry(2000.0F))
        pieEntries.add(PieEntry(3000.0F))
        pieEntries.add(PieEntry(4000.0F))

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


        //CENTER SPACE INCREMENT/DECREMENT OF THE PIECHART
        intertechPieChart.holeRadius = 20f


        pieData.setDrawValues(true)
        intertechPieChart.data = pieData
    }

}