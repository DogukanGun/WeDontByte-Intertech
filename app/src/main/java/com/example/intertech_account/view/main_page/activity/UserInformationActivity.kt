package com.example.intertech_account.view.main_page.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.core.app.ApplicationProvider
import com.example.intertech_Customer.view_model.GetCustomerViewModel
import com.example.intertech_account.R
import com.example.intertech_account.databinding.ActivityUserInformationBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList
import com.example.intertech_account.model.api_model.get_customer.GetCustomerModel
import com.example.intertech_account.view.main_page.fragment.user_information.adapter.UserInformationAdapter
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class UserInformationActivity : AppCompatActivity() {
    lateinit var binding:ActivityUserInformationBinding
    private var processNamesList = mutableListOf<String>()
    private lateinit var getCustomerModel: GetCustomerModel
    private var balanceList = mutableListOf<String>()
    private val getCustomerViewModel: GetCustomerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar =  binding.appToolbarUserActivity
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager =  LinearLayoutManager(this)
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
        getCustomerViewModel.getCustomerInfo.observe(this,{
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
    }
    private fun showApiErrorMessage(){
        getCustomerViewModel.errorMessage.observe(this,{
            if (it.equals("ApiError")){
                Snackbar.make(binding.root, R.string.page_reloading, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.page_reloading_button){
                        reloadedPage()
                    }
                    .setActionTextColor(Color.RED)
                    .setTextColor(Color.GRAY)
                    .setBackgroundTint(Color.WHITE)
                    .show()
            }

        })

    }
    private fun reloadedPage(){
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
        exitProcess(0)
    }

}