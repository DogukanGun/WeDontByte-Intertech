package com.example.intertech_account.view.main_page.fragment.account

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentAllAccountsBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountList
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.resources.common_variables.Button
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.view.main_page.activity.MainActivity
import com.example.intertech_account.view.main_page.fragment.account.adapter.AllAccountsAdapter
import com.example.intertech_account.view.main_page.fragment.account.adapter.Swipe
import com.example.intertech_account.view.main_page.fragment.account.adapter.SwipeButton
import com.example.intertech_account.view.main_page.fragment.account.adapter.SwipeButtonClickListener
import com.example.intertech_account.view.main_page.fragment.main_page.MainPageFragmentDirections
import com.example.intertech_account.view_model.GetAccountDetailWithChartsViewModel
import com.example.intertech_account.view_model.GetAccountViewModel
import com.example.intertech_account.view_model.GetCurrencyViewModel
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.snackbar.Snackbar


class AllAccountsFragment : Fragment() {

    private lateinit var binding:FragmentAllAccountsBinding
    private val getAccountViewModel: GetAccountViewModel by viewModels()
    private val getAccountDetailWithChartsViewModel: GetAccountDetailWithChartsViewModel by viewModels()
     private lateinit var getAccountModel: GetAccountModel
    private lateinit var adapter:AllAccountsAdapter
     private lateinit var adapter_:AllAccountsAdapter


    private lateinit var pieChartEntries:ArrayList<PieEntry>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentAllAccountsBinding.inflate(layoutInflater)
        adapter=AllAccountsAdapter(arrayListOf(),requireContext())
        (requireActivity() as MainActivity).binding.topAppBarToolbar.title=getString(R.string.app_title)

        controlError()
        getData(savedInstanceState)
        createSwipe()

        return binding.root

    }



    private fun controlError(){
        getAccountViewModel.errorMessage.observe(viewLifecycleOwner,{
            if (it=="ApiError"){
                showApiErrorMessage()
            }
        })
    }


    //İlk geldiğinde response a göre Recycler View doldurulması

    private fun getData(savedInstanceState:Bundle?){

        binding.allAccounts.adapter=adapter
        binding.allAccounts.layoutManager=LinearLayoutManager(activity)
        getAccountViewModel.apiRequest()
        getAccountViewModel.getAccountList.observe(viewLifecycleOwner,{
            if(it.getAccountData.getAccountList.isNotEmpty()){
                getAccountModel=it
                getAccountDetailWithChartsViewModel.getAccountModel=it
                getAccountModel.getAccountData.getAccountList=balanceExchange(it.getAccountData.getAccountList)


                var arrList = ArrayList<GetAccountList>()
                arrList.addAll(getAccountModel.getAccountData.getAccountList.toCollection(ArrayList()))

                pieChartEntries=getAccountDetailWithChartsViewModel.createPieChartEntries()
                adapter_= (binding.allAccounts.adapter as? AllAccountsAdapter)!!
                adapter_.addAccount(getAccountModel.getAccountData.getAccountList,pieChartEntries)


            }

        })
    }


    private fun findCurrency(destinationCurrency:String):Double{
        for(index in Constant.currencyList){
            if(index.currencyCode.equals(destinationCurrency)){
                return index.exchangeRate
            }

        }

        return 1.0
    }

    private fun balanceExchange(getAccountList:Array<GetAccountList>):Array<GetAccountList>{
        for(index in getAccountList){
            index.balanceAsTRY=index.balance*findCurrency(index.currency)
        }
        return getAccountList

    }


    //Error check

    private fun showApiErrorMessage(){
        Snackbar.make(binding.root, R.string.page_reloading,Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.page_reloading_button){
                relodePage()
            }
            .setActionTextColor(Color.RED)
            .setTextColor(Color.GRAY)
            .setBackgroundTint(Color.WHITE)
            .show()
    }


    //TODO ?

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
        var mgr:AlarmManager= ApplicationProvider.getApplicationContext<Context>()
            .getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent)
        System.exit(0)
    }

    //Swipe ve içindeki butonların oluşturulması

    private fun createSwipe(){
        object: Swipe(activity as MainActivity ,binding.allAccounts,200){
            override fun instantiateSwipeButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<SwipeButton>
            ) {
                if(viewHolder.itemViewType == 2) {
                    var detailsButton = SwipeButton(activity as MainActivity,
                        resources.getString(R.string.sub_accounts),
                        30,
                        0,
                        resources.getColor(R.color.intertech_swipebutton_backcolor2),
                        object : SwipeButtonClickListener {
                            override fun onClick(pos: Int) {


                                var action = AllAccountsFragmentDirections.actionAllAccountsFragmentToSimpleAccountFragment()
                                Constant.navHostFragment.findNavController().navigate(action)
                            }

                        })


                    var activitiesButton = SwipeButton(activity as MainActivity,
                        resources.getString(R.string.account_details),
                        30,
                        0,
                        resources.getColor(R.color.intertech_swipebutton_backcolor1),
                        object : SwipeButtonClickListener {
                            override fun onClick(pos: Int) {

                                var toSend = adapter_.getPositionData(2)
                                Log.d("Info",toSend.toString())
                                var action = AllAccountsFragmentDirections.actionAllAccountsFragmentToAccountDetailFragment("asd")
                                action.setAccountDetailFragmentListValues(toSend)
                                Log.d("Info","Burası da sonrası")
                                Constant.navHostFragment.findNavController().navigate(action)
                                Log.d("Info","OV yeaaa")
                            }

                        })

                    detailsButton.textColor = resources.getColor(R.color.intertech_swipe_textcolor1)
                    activitiesButton.textColor = resources.getColor(R.color.intertech_swipe_textcolor2)


                    //TODO Buraya istenidiği kadar buton eklenebilir
                    buffer.add(detailsButton)
                    buffer.add(activitiesButton)



                }

            }
        }
    }

}