package com.example.intertech_account.view.main_page.fragment.user_information

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.view.main_page.activity.MainActivity
import com.example.intertech_account.view.main_page.fragment.user_information.adapter.UserInformationAdapter
import com.google.android.material.snackbar.Snackbar


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
        var adapter = UserInformationAdapter(processNamesList,balanceList)
        //var adapter = UserInformationAdapter(emptyList(), emptyList())
        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,1
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        getCustomerViewModel.apiRequest()
        showApiErrorMessage()

        getCustomerViewModel.getCustomerInfo.observe(viewLifecycleOwner,{
            getCustomerModel=it
            var adapter_=binding.recyclerView.adapter as? UserInformationAdapter
            var x = listOf("Name ","Surname ", "Citizienship Id ", "Birth Date", "Email ", "Telephone Number")

            var Info = listOf(getCustomerModel.getCustomerData.shortName ,
                getCustomerModel.getCustomerData.shortName,
                getCustomerModel.getCustomerData.citizenshipNumber,
                getCustomerModel.getCustomerData.birthDate,
                getCustomerModel.getCustomerData.email,
                getCustomerModel.getCustomerData.getMobilePhoneList.number
            )
            adapter_!!.addInfo(x,Info)

        })
        return binding.root
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
        Constant.isUserInformationTopBarButtonClick.value=0
    }
}