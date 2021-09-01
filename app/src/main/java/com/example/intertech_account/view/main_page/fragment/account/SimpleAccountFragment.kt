package com.example.intertech_account.view.main_page.fragment.account

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat.getSystemService
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

        ExecutePopupMenu(inflater)

        return binding.root
    }

    private fun ExecutePopupMenu(inflater: LayoutInflater)
    {
        binding.myButton.setOnClickListener {

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.popup_screen, null)

            // Initialize a new instance of popup window
            val popupWindow = PopupWindow(
                view, // Custom view to show in popup window
                LinearLayout.LayoutParams.MATCH_PARENT, // Width of popup window
                LinearLayout.LayoutParams.MATCH_PARENT // Window height
            )

            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }

            // If API level 23 or higher then execute the code
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //WINDOW OPEN ANIMATION
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.LEFT
                popupWindow.enterTransition = slideIn

                //WINDOW EXIT ANIMATION
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.RIGHT
                popupWindow.exitTransition = slideOut
            }

            // Get the widgets reference from custom view
            val son1HaftaButton = view.findViewById<Button>(R.id.son1HaftaButton)
            val son1AyButton = view.findViewById<Button>(R.id.son1AyButton)
            val son3AyButton = view.findViewById<Button>(R.id.son3AyButton)
            val son6AyButton = view.findViewById<Button>(R.id.son6AyButton)
            val son1YilButton = view.findViewById<Button>(R.id.son1YilButton)
            val detayliFiltrelemeButton = view.findViewById<Button>(R.id.detayliFiltrelemeButton)

            //CORRESPONDING BUTTON ONCLICKED EVENT
            son1HaftaButton.setOnClickListener {
                //TODO SON 1 HAFTAYA GÖRE FİLTRELEME İŞLEMİ
                popupWindow.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            son1AyButton.setOnClickListener {
                //TODO SON 1 AYA GÖRE FİLTRELEME İŞLEMİ
                popupWindow.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            son3AyButton.setOnClickListener {
                //TODO SON 3 AYA GÖRE FİLTRELEME İŞLEMİ
                popupWindow.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            son6AyButton.setOnClickListener {
                //TODO SON 6 AYA GÖRE FİLTRELEME İŞLEMİ
                popupWindow.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            son1YilButton.setOnClickListener {
                //TODO SON 1 YILA GÖRE FİLTRELEME İŞLEMİ
                popupWindow.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            detayliFiltrelemeButton.setOnClickListener {
                //TODO DETAYLI FİLTELEME BUTONU YAPILMAYACAKSA BU SİLİNEBİLİR
                popupWindow.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }

            //WHEN THE POPUP SCREEN IS CLOSED, THIS LISTENER IS EXECUTED
            popupWindow.setOnDismissListener {
                Toast.makeText(context, "Popup closed", Toast.LENGTH_SHORT).show()
            }

            //IF OUTSIDE OF THE IS CLICKED, MAKE THE SCENE DISAPPEAR
            view?.findViewById<LinearLayout>(R.id.popupScreenName)?.setOnClickListener()
            {
                popupWindow.dismiss()
            }

            //SHOW THE POPUP WINDOW ON THE APP
            TransitionManager.beginDelayedTransition(binding.myScreen)
            popupWindow.showAtLocation(
                binding.myScreen,
                Gravity.CENTER,
                0,
                0
            )
        }
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