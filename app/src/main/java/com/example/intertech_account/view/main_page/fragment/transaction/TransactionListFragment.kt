package com.example.intertech_account.view.main_page.fragment.transaction

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.core.app.ApplicationProvider
import com.example.intertech_account.R
import com.example.intertech_account.resources.common_variables.Button
import com.example.intertech_account.view.main_page.activity.MainActivity
import com.example.intertech_account.view.main_page.fragment.transaction_list.adapter.TransactionListAdapter
import com.example.intertech_account.view.main_page.fragment.user_information.adapter.UserInformationAdapter
import com.example.intertech_account.view_model.GetAccountTransactionViewModel
import com.google.android.material.snackbar.Snackbar

class TransactionListFragment {
    /*private var allTransactions = mutableListOf<String>()
    private lateinit var getTransactionListModel: GetAccountTransactionViewModel
    private lateinit var binding: FragmentTransaction
    private val getAccountTransactionViewModel: GetAccountTransactionViewModel by viewModels()

     fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_information,container,false)

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager =  LinearLayoutManager(activity)
        val adapter = TransactionListAdapter(allTransactions)
        //var adapter = UserInformationAdapter(emptyList(), emptyList())
        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,1
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        getAccountTransactionViewModel.apiRequest()
        showApiErrorMessage()

         //        pdf()

        getAccountTransactionViewModel.getAccountTransactionResult.observe(viewLifecycleOwner,{
            getTransactionListModel=it
            val adapter_=binding.recyclerView.adapter as? UserInformationAdapter

            val x = listOf(getString(R.string.userName),getString(R.string.userSurname),
                getString(R.string.citizenshipID), getString(R.string.birthdate)
                , getString(R.string.email), getString(R.string.phoneNumber))

            val Info = listOf(getTransactionListModel.getAccountTransactionResult.name ,
                getTransactionListModel.getAccountTransactionResult.surname,
                getTransactionListModel.getAccountTransactionResult.citizenshipNumber,
                getTransactionListModel.getAccountTransactionResult.birthDate,
                getTransactionListModel.getAccountTransactionResult.email,
                getTransactionListModel.getAccountTransactionResult.getMobilePhoneList.number
            )
            adapter_!!.addInfo(x,Info)

        })

        return binding.root
    }


    private fun addToRecyclerView(processName: String)
    {
        allTransactions.add()
    }
    private fun showApiErrorMessage(){
        getAccountTransactionViewModel.errorMessage.observe(viewLifecycleOwner,{
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
        Button.isUserInformationTopBarButtonClick.value=0
    }

     */
}