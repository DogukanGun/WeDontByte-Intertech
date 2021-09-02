package com.example.intertech_account.view.main_page.fragment.user_information

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.core.app.ApplicationProvider
import com.example.intertech_Customer.view_model.GetCustomerViewModel
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentUserInformationBinding
import com.example.intertech_account.model.api_model.get_customer.GetCustomerModel
import com.example.intertech_account.resources.common_variables.Button
import com.example.intertech_account.view.main_page.activity.MainActivity
import com.example.intertech_account.view.main_page.fragment.user_information.adapter.UserInformationAdapter
import com.google.android.material.internal.ViewUtils.getContentView
import com.google.android.material.snackbar.Snackbar
import java.io.FileOutputStream



class UserInformationFragment : Fragment() {
    private var processNamesList = mutableListOf<String>()
    private lateinit var getCustomerModel: GetCustomerModel
    private var balanceList = mutableListOf<String>()
    private lateinit var binding:FragmentUserInformationBinding
    private val getCustomerViewModel: GetCustomerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_information,container,false)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager =  LinearLayoutManager(activity)
        val adapter = UserInformationAdapter(processNamesList,balanceList)
        //var adapter = UserInformationAdapter(emptyList(), emptyList())
        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,1
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        getCustomerViewModel.apiRequest()
        showApiErrorMessage()
//        pdf()
        getCustomerViewModel.getCustomerInfo.observe(viewLifecycleOwner,{
            getCustomerModel=it
            val adapter_=binding.recyclerView.adapter as? UserInformationAdapter

            //TELEFONUN DILINE GORE USER INFO SAYFASINDA NAME SURNAME YERINE AD SOYAD SEKLINDE YAZACAK
            val x = listOf(getString(R.string.userName),getString(R.string.userSurname),
                getString(R.string.citizenshipID), getString(R.string.birthdate)
                , getString(R.string.email), getString(R.string.phoneNumber))

            val Info = listOf(getCustomerModel.getCustomerData.name ,
                getCustomerModel.getCustomerData.surname,
                getCustomerModel.getCustomerData.citizenshipNumber,
                getCustomerModel.getCustomerData.birthDate,
                getCustomerModel.getCustomerData.email,
                getCustomerModel.getCustomerData.getMobilePhoneList.number
            )
            adapter_!!.addInfo(x,Info)

        })

        return binding.root
    }
    @SuppressLint("RestrictedApi")
    private fun pdf(){
        // create a new document
        val document = PdfDocument()

        // create a page description
         val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(100, 100, 1).create()

        // start a page
        val page = document.startPage(pageInfo)

        // draw something on the page
        val content = getContentView(binding.userImageView)
        if(content != null) {
            content.draw(page.getCanvas())
        }
        // finish the page
        document.finishPage(page)
        //val out = OutputStream.

        // write the document content
        //document.writeTo()

        // close the document
        document.close();


    }


    private fun addToRecyclerView(processName: String, balance: String)
    {
        processNamesList.add(processName)
        balanceList.add(balance)
    }
    private fun showApiErrorMessage(){
        getCustomerViewModel.errorMessage.observe(viewLifecycleOwner,{
            if (it.equals("ApiError")){
                Snackbar.make(this.requireView(), R.string.relode_page, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.relode_page_button){
                        relodePage()
                    }
                    .setActionTextColor(Color.RED)
                    .setTextColor(Color.GRAY)
                    .setBackgroundTint(Color.WHITE)
                    .show()
            }

        })

    }
//    @SuppressLint("RestrictedApi")
//    private fun pdf(){
//        // create a new document
//        val document = PdfDocument()
//
//        // create a page description
//        val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(100, 100, 1).create()
//
//        // start a page
//        val page = document.startPage(pageInfo)
//
//        // draw something on the page
//        val content = getContentView(binding.userImageView)
//        if(content != null) {
//            content.draw(page.getCanvas())
//        }
//        // finish the page
//        document.finishPage(page)
//        //val out = OutputStream.
//        PdfWriter.getInstance(document, FileOutputStream(Environment.getStorageDirectory()))
//
//        // write the document content
//        //document.writeTo()
//
//        // close the document
//        document.close();
//
//
//    }
    private fun relodePage(){
        val intent = Intent(
            ApplicationProvider.getApplicationContext<Context>(),
            MainActivity::class.java
        )
        val mPendingIntentId: Int = 1600042000
        val mPendingIntent = PendingIntent.getActivity(
            ApplicationProvider.getApplicationContext<Context>(),
            mPendingIntentId,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        var mgr: AlarmManager = ApplicationProvider.getApplicationContext<Context>()
            .getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent)
        System.exit(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        Button.isUserInformationTopBarButtonClick.value=0
    }

}