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
        return binding.root
    }

}
    // Buradaki DrawingPieChart AllAccountsAdapter içinde
