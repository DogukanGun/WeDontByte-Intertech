package com.example.intertech_account.view.main_page.fragment.account

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentSimpleAccountBinding
import com.example.intertech_account.model.api_model.get_account_transaction_list.GetAccountTransactionList
import com.example.intertech_account.model.api_model.status.SimpleAccountListState
import com.example.intertech_account.resources.common_variables.Receipt
import com.example.intertech_account.view.main_page.fragment.account.adapter.SimpleAccountAdapter
import com.example.intertech_account.view_model.GetReceiptViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.net.ParseException
import android.util.Base64
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import android.net.Uri

import android.provider.MediaStore.Images
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.example.intertech_account.model.api_model.get_account_transaction_list.GetAccountTransactionListModel
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.view_model.GetAccountTransactionViewModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.concurrent.schedule


class SimpleAccountFragment : Fragment() {
    private var destinationAccountTitles = mutableListOf<String>()
    private var transactionNames = mutableListOf<String>()
    private var amounts = mutableListOf<String>()
    private var times = mutableListOf<String>()
    private var dates = mutableListOf<String>()
    private var transaction = arrayListOf<GetAccountTransactionList>()
    private lateinit var binding: FragmentSimpleAccountBinding
    private lateinit var adapter: SimpleAccountAdapter
    private val getReceiptViewModel: GetReceiptViewModel by viewModels()
    private val getAccountTransactionViewModel: GetAccountTransactionViewModel by viewModels()
    private lateinit var getAccountTransactionListModel: GetAccountTransactionListModel
    var toolbar: Toolbar? = null
    lateinit var popupWindowShare: PopupWindow
    lateinit var popupWindowFilter: PopupWindow


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSimpleAccountBinding.inflate(layoutInflater)
        createRecyclerView()
        executePopupMenu(inflater)
        Receipt.isReceiptButtonClicked.observe(viewLifecycleOwner, {
            if (it == true) {
                getReceiptViewModel.apiRequest()
                Receipt.isReceiptButtonClicked.value = false
            }
        })

        getReceiptViewModel.getContentOfReceipt.observe(viewLifecycleOwner, {
            if (!it.value.isEmpty()) {
                val intent = Intent(Intent.ACTION_SEND)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val decodedString: ByteArray = Base64.decode(it.value, Base64.DEFAULT)
                val decodedByte =
                    BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                val pictureFile = getOutputMediaFile()
                if (pictureFile == null) {
                    Log.d(
                        "TAG",
                        "Error creating media file, check storage permissions: "
                    ) // e.getMessage());

                }
                try {
                    val fos = FileOutputStream(pictureFile)
                    decodedByte.compress(Bitmap.CompressFormat.PNG, 90, fos)
                    fos.close()

                } catch (e: FileNotFoundException) {
                    Log.d("TAG", "File not found: " + e.message)
                } catch (e: IOException) {
                    Log.d("TAG", "Error accessing file: " + e.message)
                }
            }
        })
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.R)
    private fun getOutputMediaFile(): File? {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        val mediaStorageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath
        )

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        // Create a media file name
        val timeStamp: String = SimpleDateFormat("ddMMyyyy_HHmm").format(Date())
        val mediaFile: File
        val mImageName = "MI_$timeStamp.jpg"
        mediaFile = File(mediaStorageDir.path + File.separator + mImageName)
        return mediaFile
    }

    private fun executePopupMenu(inflater: LayoutInflater) {
        binding.myButton.setOnClickListener {

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.popup_screen, null)

            // Initialize a new instance of popup window
            popupWindowFilter = PopupWindow(
                view, // Custom view to show in popup window
                LinearLayout.LayoutParams.MATCH_PARENT, // Width of popup window
                LinearLayout.LayoutParams.MATCH_PARENT // Window height
            )

            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindowFilter.elevation = 10.0F
            }

            // If API level 23 or higher then execute the code
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //WINDOW OPEN ANIMATION
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.LEFT
                popupWindowFilter.enterTransition = slideIn

                //WINDOW EXIT ANIMATION
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.RIGHT
                popupWindowFilter.exitTransition = slideOut
            }

            // Get the widgets reference from custom view
            //TODO turkce den ingilizceye degisstirilecek
            val son1HaftaButton = view.findViewById<Button>(R.id.son1HaftaButton)
            val son1AyButton = view.findViewById<Button>(R.id.son1AyButton)
            val son3AyButton = view.findViewById<Button>(R.id.son3AyButton)
            val son6AyButton = view.findViewById<Button>(R.id.son6AyButton)
            val son1YilButton = view.findViewById<Button>(R.id.son1YilButton)
            val detayliFiltrelemeButton = view.findViewById<Button>(R.id.detayliFiltrelemeButton)


            //CORRESPONDING BUTTON ONCLICKED EVENT
            son1HaftaButton.setOnClickListener {
                adapter.status = SimpleAccountListState.LAST_ONE_WEEK
                adapter.changeStatusOfArray()
                popupWindowFilter.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            son1AyButton.setOnClickListener {
                adapter.status = SimpleAccountListState.LAST_ONE_MONTH
                adapter.changeStatusOfArray()
                popupWindowFilter.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            son3AyButton.setOnClickListener {
                adapter.status = SimpleAccountListState.LAST_THREE_MONTHS
                adapter.changeStatusOfArray()
                popupWindowFilter.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            son6AyButton.setOnClickListener {
                adapter.status = SimpleAccountListState.LAST_SIX_MONTHS
                adapter.changeStatusOfArray()
                popupWindowFilter.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            son1YilButton.setOnClickListener {
                adapter.status = SimpleAccountListState.LAST_ONE_YEAR
                adapter.changeStatusOfArray()
                popupWindowFilter.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            detayliFiltrelemeButton.setOnClickListener {
                adapter.status = SimpleAccountListState.DETAIL
                adapter.changeStatusOfArray()
                popupWindowFilter.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }

            //WHEN THE POPUP SCREEN IS CLOSED, THIS LISTENER IS EXECUTED
            popupWindowFilter.setOnDismissListener {
                Toast.makeText(context, "Popup closed", Toast.LENGTH_SHORT).show()

            }

            //IF OUTSIDE OF THE IS CLICKED, MAKE THE SCENE DISAPPEAR
            view?.findViewById<LinearLayout>(R.id.popupScreenName)?.setOnClickListener()
            {
                popupWindowFilter.dismiss()
            }

            //SHOW THE POPUP WINDOW ON THE APP
            TransitionManager.beginDelayedTransition(binding.myScreen)
            popupWindowFilter.showAtLocation(
                binding.myScreen,
                Gravity.CENTER,
                0,
                0
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getDateInMilliSeconds(givenDateString: String?, format: String): Long {
        val sdf = SimpleDateFormat(format, Locale.US)
        var timeInMilliseconds: Long = 1
        try {
            val mDate = sdf.parse(givenDateString)
            timeInMilliseconds = mDate.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeInMilliseconds
    }

    private fun drawingLineChart(entries: ArrayList<Entry>, xLabels: ArrayList<String>) {

        //SET LINE ENTRIES (YEAR, MONEY)
        val myArray = ArrayList<Entry>()
        /*myArray.add(Entry(2010F, 100F))
        myArray.add(Entry(2011F, 500F))
        myArray.add(Entry(2013F, 800F))
        myArray.add(Entry(2014F, 200F))*/
        //GET LINE CHART COMPONENT FROM XML
        var intertechLineChart: LineChart = binding.simpleAccountLineChart

        //SETUP LINE ANIMATION
        intertechLineChart.animateXY(3000, 3000)

        intertechLineChart.description.isEnabled = false
        intertechLineChart.legend.isEnabled = false
        intertechLineChart.xAxis.setLabelCount(5, true)
        intertechLineChart.setScaleEnabled(false)
        intertechLineChart.xAxis.setValueFormatter(object : ValueFormatter() {
            @RequiresApi(Build.VERSION_CODES.N)
            private val mFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)

            @RequiresApi(Build.VERSION_CODES.N)
            override fun getFormattedValue(value: Float): String {
                if(value==Math.ceil(value.toDouble()).toFloat()){
                    val millis: Long = getDateInMilliSeconds(xLabels[value.toInt()], "yyyy-MM-dd")
                    return mFormat.format(Date(millis))
                }
                return ""
            }
        })


        //SETUP LINE CHART COLORS
        //var lineDataSet = LineDataSet(myArray, "MONEY/YEAR GRAPH")
        var lineDataSet = LineDataSet(entries, "MONEY/YEAR GRAPH")
        var colors = ArrayList<Int>()
        //SETUP BAR CHART COLORS
        /*lineDataSet.setColors(
            Color.GREEN,
            Color.RED
        )*/
        for (pos in 0..entries.size - 2) {
            colors.add(
                if (entries.get(pos + 1).y - entries.get(pos).y > 0) Color.GREEN
                else if (entries.get(pos + 1).y - entries.get(pos).y < 0) Color.RED
                else Color.BLACK
            )
        }

        lineDataSet.setColors(colors.toIntArray(), 255)
        lineDataSet.valueTextColor = resources.getColor(R.color.intertech_linechart_value_color)
        lineDataSet.valueTextSize = 12F
        intertechLineChart.setBackgroundColor(resources.getColor(R.color.intertech_linechart_background_color))
        intertechLineChart.xAxis.textColor =
            resources.getColor(R.color.intertech_linechart_label_color)
        intertechLineChart.axisLeft.textColor =
            resources.getColor(R.color.intertech_linechart_label_color)
        intertechLineChart.axisRight.isEnabled = false
        intertechLineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        var lineData = LineData(lineDataSet)

        lineData.setDrawValues(true)
        intertechLineChart.data = lineData
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun createRecyclerView() {
        val recyclerView = binding.simpleAccountTransactions
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = SimpleAccountAdapter()
        recyclerView.adapter = adapter

        getAccountTransactionViewModel.apiRequest()
        getAccountTransactionViewModel.getAccountTransactionResult.observe(viewLifecycleOwner, {
            getAccountTransactionListModel = it
            if (getAccountTransactionListModel.data.activityCollection.isNotEmpty()) {
                val recyclerView = binding.simpleAccountTransactions
                recyclerView.layoutManager = LinearLayoutManager(activity)
                (recyclerView.adapter as SimpleAccountAdapter).addList(
                    getAccountTransactionListModel.data.activityCollection,
                    this
                )
                val dividerItemDecoration = DividerItemDecoration(
                    recyclerView.context, 1
                )

                recyclerView.addItemDecoration(dividerItemDecoration)
                var xLabels = ArrayList<String>()
                var lineChartEntries = ArrayList<Entry>()
                var lineChartArray = ArrayList<GetAccountTransactionList>()
                lineChartArray.addAll((recyclerView.adapter as SimpleAccountAdapter).getSorted())
                lineChartArray.reverse()
                if (lineChartArray.size >= 5) {
                    for (i in (lineChartArray.size - 5)..(lineChartArray.size - 1)) {
                        lineChartEntries.add(Entry((i-5).toFloat(),
                                lineChartArray[i].remainingBalance.toFloat()))
                        xLabels.add(lineChartArray[i].date)
                    }
                } else {
                    for (i in 0..(lineChartArray.size - 1)) {
                        lineChartEntries.add(Entry((i).toFloat(),
                                lineChartArray[i].remainingBalance.toFloat()))
                        xLabels.add(lineChartArray[i].date)
                    }
                }
                drawingLineChart(lineChartEntries, xLabels)
            }
        })
//
        adapter = SimpleAccountAdapter()
//
        recyclerView.adapter = adapter
//        var arrayList = arrayListOf<GetAccountTransactionList>()
//
//
        //val myarray2: Array<GetAccountTransactionList> = arrayList.toTypedArray()
        //val myarray2: Array<GetAccountTransactionList> = createDummyList(15)
        //var myarray = arrayOf(GetAccountTransactionList())
//        adapter.addList(myarray2)
        var lineChartEntries = ArrayList<Entry>()
        lineChartEntries.add(Entry(1000F, 200F))
        lineChartEntries.add(Entry(1001F, 201F))
        /*      for(i in myarray2){
                  lineChartEntries.add(Entry(i.date.toFloat(),i.remainingBalance.toFloat()))

              }*/

/*
        lineChartEntries.add(Entry(2010F, 100F))
        lineChartEntries.add(Entry(2011F, 500F))
        lineChartEntries.add(Entry(2013F, 800F))
        lineChartEntries.add(Entry(2014F, 200F))*/


        //drawingLineChart(lineChartEntries)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context, 1
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    /*private fun createDummyTransactionList(x: Double, d: String): GetAccountTransactionList {
        var x = GetAccountTransactionList("test",d,"test","test",x,122.2,
            "t","t","t","t","t","t","t",
            233.3,"t"
        )
        return x
    }

     */
    private fun addToRecyclerView(
        destinationAccountTitle: String,
        transactionName: String,
        amount: String,
        time: String,
        date: String
    ) {
        destinationAccountTitles.add(destinationAccountTitle)
        transactionNames.add(transactionName)
        amounts.add(amount)
        times.add(time)
        dates.add(date)
    }

    private fun createDummyList(size: Int): Array<GetAccountTransactionList> {
        var dummyOutgoing =
            arrayListOf("Amazon", "Hepsiburada", "Spotify", "Gittigidiyor", "GooglePlay")
        var dummyIncoming = arrayListOf(
            "ALEYNA USTA",
            "HALİLİBRAHİM KAPAR",
            "ITIR KURTULUŞ",
            "FERHAT ATAÇ",
            "MERJEN SULUOVA",
            "BARTU GÜNGÜNEŞ",
            "BERK EGEHAN",
            "CAHİT MEMİŞ",
            "BAŞAR SAĞÇOLAK",
            "YAPRAK KAL"
        )
        var dummyIncomingDetail = arrayListOf("Sipariş", "Üyelik", "Hizmet")
        var dummyOutgoingDetail = arrayListOf("Kira", "Borç", "Gelen Havale")
        var x = java.util.ArrayList<GetAccountTransactionList>()
        var date = 0
        var defaultAmount = 1000.0
        var dateStr = ""
        for (i in 0..size) {
            date += 1
            var transactionAmout: Double
            if (i == 0) transactionAmout = 0.0
            else transactionAmout = (-150..150).random().toDouble()
            var name: String = ""
            var detail: String = ""
            if (transactionAmout > 0) {
                name = dummyOutgoing[(0..dummyOutgoing.size - 1).random()]
                detail = dummyOutgoingDetail[(0..dummyOutgoingDetail.size - 1).random()]
            } else {
                name = dummyIncoming[(0..dummyIncoming.size - 1).random()]
                detail = dummyIncomingDetail[(0..dummyIncomingDetail.size - 1).random()]
            }

            defaultAmount += transactionAmout
            x.add(
                GetAccountTransactionList(
                    name,
                    date.toString(),
                    "213",
                    "1312",
                    "test",
                    detail,
                    transactionAmout,
                    defaultAmount,
                    "t",
                    "t",
                    "t",
                    "t",
                    "9:00",
                    "t",
                    "t",
                    233.3,
                    "t",
                    "TRY"
                )
            )

        }
        return x.toTypedArray()
    }

    fun executeSharePopupMenu(
        inflater: LayoutInflater,
        holder: SimpleAccountAdapter.SimpleAccountHolder
    ) {
        holder.binding.showDekont.setOnClickListener {
            Receipt.referenceNo = 3411
            Receipt.isReceiptButtonClicked.value = true
            Receipt.branchCode = 9142
            Receipt.customerNo = 123
            Receipt.transactionDate = "2021-01-28"

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.receipt_popup_screen, null)

            // Initialize a new instance of popup window
            popupWindowShare = PopupWindow(
                view, // Custom view to show in popup window
                LinearLayout.LayoutParams.MATCH_PARENT, // Width of popup window
                LinearLayout.LayoutParams.MATCH_PARENT // Window height
            )

            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindowShare.elevation = 10.0F
            }

            // If API level 23 or higher then execute the code
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //WINDOW OPEN ANIMATION
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.BOTTOM
                popupWindowShare.enterTransition = slideIn

                //WINDOW EXIT ANIMATION
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.BOTTOM
                popupWindowShare.exitTransition = slideOut
            }

            // Get the widgets reference from custom view
            val downloadButton =
                view.findViewById<android.widget.Button>(R.id.ReceiptDownloadButton)
            val whatsappButton =
                view.findViewById<android.widget.Button>(R.id.ReceiptWhatsappButton)
            val wechatButton = view.findViewById<android.widget.Button>(R.id.ReceiptWechatButton)
            val gmailButton = view.findViewById<android.widget.Button>(R.id.ReceiptGmailButton)
            val instagramButton =
                view.findViewById<android.widget.Button>(R.id.ReceiptInstagramButton)
            val twitterButton = view.findViewById<android.widget.Button>(R.id.ReceiptTwitterButton)
            val facebookButton =
                view.findViewById<android.widget.Button>(R.id.ReceiptFacebookButton)
            val smsButton = view.findViewById<android.widget.Button>(R.id.ReceiptSmsButton)


            //CORRESPONDING BUTTON ONCLICKED EVENT
            downloadButton.setOnClickListener {


                popupWindowShare.dismiss()
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            whatsappButton.setOnClickListener {
                activity?.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.whatsapp.com/")
                    )
                )
                popupWindowShare.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            wechatButton.setOnClickListener {
                activity?.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.wechat.com/")
                    )
                )
                popupWindowShare.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            gmailButton.setOnClickListener {
                activity?.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/intl/tr/gmail/about/#")
                    )
                )
                popupWindowShare.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            instagramButton.setOnClickListener {
                activity?.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.instagram.com/")
                    )
                )
                popupWindowShare.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            twitterButton.setOnClickListener {
                activity?.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://twitter.com/")
                    )
                )
                popupWindowShare.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            facebookButton.setOnClickListener {
                activity?.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.facebook.com/")
                    )
                )
                popupWindowShare.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }
            //CORRESPONDING BUTTON ONCLICKED EVENT
            smsButton.setOnClickListener {
                activity?.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.messaging&hl=en&gl=US")
                    )
                )
                popupWindowShare.dismiss() //HAVING CLICKED ON THE BUTTON, POPUP MENU IS CLOSED
            }

            //WHEN THE POPUP SCREEN IS CLOSED, THIS LISTENER IS EXECUTED
            popupWindowShare.setOnDismissListener {
                popupWindowShare.dismiss()
                //DO ANYTHING YOU WANT WHEN THE POPUP MENU IS CLOSED

            }


            //IF OUTSIDE OF THE IS CLICKED, MAKE THE SCENE DISAPPEAR
            view?.findViewById<LinearLayout>(R.id.sharePopupScreenName)?.setOnClickListener()
            {
                popupWindowShare.dismiss()
            }

            //SHOW THE POPUP WINDOW ON THE APP
            TransitionManager.beginDelayedTransition(binding.root)
            popupWindowShare.showAtLocation(
                binding.root,
                Gravity.CENTER,
                0,
                0
            )
        }
    }

    override fun onDestroy() {
        if (this::popupWindowFilter.isInitialized) {
            popupWindowFilter.dismiss()
        }
        if (this::popupWindowShare.isInitialized) {
            popupWindowShare.dismiss()
        }
        super.onDestroy()
    }

    public fun updateLineChart(lineChartEntries: ArrayList<Entry>) {


        Timer("SettingUp", false).schedule(2000) {
            var lineDataSet = LineDataSet(lineChartEntries, "MONEY/YEAR GRAPH")
            var colors = ArrayList<Int>()
            for (pos in 0..lineChartEntries.size - 2) {
                colors.add(
                    if (lineChartEntries.get(pos + 1).y - lineChartEntries.get(pos).y > 0) Color.GREEN
                    else if (lineChartEntries.get(pos + 1).y - lineChartEntries.get(pos).y < 0) Color.RED
                    else Color.BLACK
                )
            }

            lineDataSet.setColors(colors.toIntArray(), 255)
            lineDataSet.valueTextColor = R.color.intertech_actionbar_bottomnav_back_color
            lineDataSet.valueTextSize = 15F


            var lineData = LineData(lineDataSet)

            lineData.setDrawValues(true)
            binding.simpleAccountLineChart.data = lineData
        }
    }
}