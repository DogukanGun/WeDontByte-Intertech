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
    private val getCurrencyViewModel:GetCurrencyViewModel by viewModels()
    private lateinit var getAccountModel: GetAccountModel
    private var adapter=AllAccountsAdapter(arrayListOf())
    //private var checkBoxList: HashMap<String,CheckBox> = hashMapOf()
    private lateinit var adapter_:AllAccountsAdapter
    private var currencyStates: HashMap<String,Int> =hashMapOf(
        "TRY" to 1,
        "USD" to 1,
        "EUR" to 1,
        "GBP" to 1
    )

    private lateinit var pieChartEntries:ArrayList<PieEntry>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAllAccountsBinding.inflate(layoutInflater)

        Constant.currentBottomMenu=1
        Button.isUserInformationTopBarButtonClickFromAllAccounts.observe(viewLifecycleOwner,{
            if (it==1 && Constant.currentBottomMenu==1){
                Button.isUserInformationTopBarButtonClickFromAllAccounts.value=2
                val action = AllAccountsFragmentDirections.actionAllAccountsFragmentToUserInformationFragment()
                Constant.navHostFragment.findNavController().navigate(action)
            }
        })
        Button.isSettingTopBarButtonClickFromAllAccountsFragment.observe(viewLifecycleOwner,{
            if (it==1 && Constant.currentBottomMenu==1){
                Button.isSettingTopBarButtonClickFromAllAccountsFragment.value=2
                val action =  AllAccountsFragmentDirections.actionAllAccountsFragmentToSettingFragment()
                Constant.navHostFragment.findNavController().navigate(action)
            }
        })
        controlError()
        getData(savedInstanceState)
        createSwipe()

        return binding.root

    }

    // Hesap ayrılımları için checkboxlistin gelen para birimlerine göre oluşturulması
    //TODO buraya her currency için hashmapi default olarak 1 yapan fonksiyon yazılacak
    /*
    private fun checkboxCreator(currencyNames:List<String>,savedInstanceState: Bundle?,){

        super.onCreate(savedInstanceState)
        val linearLayout = binding.linearLayoutAccountSelection as LinearLayout
        linearLayout.removeAllViews()
        for (item in currencyNames) {
            val checkBox = CheckBox(activity)
            checkBox.text = item
            checkBox.isChecked = false
            currencyStates[item] = 0
            checkBoxList.put(item, checkBox)

            checkBox.buttonTintList = ColorStateList.valueOf(Color.WHITE);
            checkBox.setTextColor(Color.WHITE)
            checkBox.setTypeface(checkBox.typeface, Typeface.BOLD)
            checkBox.textSize = 18F

            linearLayout.addView(checkBox)
        }


    }*/


    //Checkboxların basılması kontrolü ve RecyclerViewin yeniden sıralanması
    /*
    private fun checkBoxController(currencyString: String) {
        checkBoxList[currencyString]?.setOnCheckedChangeListener{ compoundButton ,b ->
            if(compoundButton.isChecked){
                currencyStates[currencyString] = 1
                adapter_.modifyAccount(currencyStates)
                Toast.makeText(activity as MainActivity, "${currencyString} Getirildi", Toast.LENGTH_LONG).show()

            }
            if(!compoundButton.isChecked){
                currencyStates[currencyString] = 0
                adapter_.modifyAccount(currencyStates)
                Toast.makeText(activity as MainActivity, "${currencyString} Kaldırıldı", Toast.LENGTH_LONG).show()
            }


        }
    }*/


    //Error check

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
               // adapter_.addAccount(getAccountModel.getAccountData.getAccountList,pieChartEntries)



//                arrList.add(createDummyAccount("USD",1500.0))
//                var asd: Array<GetAccountList> = arrList.toTypedArray()
//                asd = balanceExchange(asd)
//                for(i in asd){
//                    Log.d("Info",i.accountName+" : "+i.balanceAsTRY.toString())
//                }
                pieChartEntries=getAccountDetailWithChartsViewModel.createPieChartEntries()
                adapter_= (binding.allAccounts.adapter as? AllAccountsAdapter)!!
                adapter_.addAccount(getAccountModel.getAccountData.getAccountList,pieChartEntries)
                /*val currencyNames :List<String> = adapter_.getCurrencyList()
                checkboxCreator(currencyNames,savedInstanceState)
                for(i in currencyStates){
                    checkBoxController(i.key)
                }*/

            }

        })
    }

    private fun createDummyAccount(typeOfAccount:String,balance:Double):GetAccountList{
        val x = GetAccountList(isBlocked = false,
            "maltepe",
            "birinci",
            false,
            typeOfAccount,
            0.15,
            00.10,
            balance,
            1800.5,
            1600.5,
            1850.0,
            "benimHesabım",
            "TR1159465168416516841634623",
            false,88.50,null)
        return x

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


                    //TODO Buraya istenidiği kadar buton eklenebilir
                    buffer.add(
                        SwipeButton(activity as MainActivity,
                            resources.getString(R.string.sub_accounts),
                            30,
                            0,
                            resources.getColor(R.color.intertech_textview_text_color),
                            object : SwipeButtonClickListener {
                                override fun onClick(pos: Int) {


                                    var action =
                                        AllAccountsFragmentDirections.actionAllAccountsFragmentToSimpleAccountFragment()
                                    Constant.navHostFragment.findNavController().navigate(action)
                                }

                            })
                    )
                    buffer.add(
                        SwipeButton(activity as MainActivity,
                            resources.getString(R.string.account_details),
                            30,
                            0,
                            resources.getColor(R.color.intertech_button_back_color),
                            object : SwipeButtonClickListener {
                                override fun onClick(pos: Int) {


                                    var action =
                                        AllAccountsFragmentDirections.actionAllAccountsFragmentToAccountDetailFragment()
                                    Constant.navHostFragment.findNavController().navigate(action)
                                }

                            })
                    )


                    /*
                buffer.add(
                    SwipeButton(activity as MainActivity,
                    "Update",
                    30,
                    0,
                    Color.parseColor("#FF9502"),
                    object: SwipeButtonClickListener {
                        override fun onClick(pos: Int) {
                            Toast.makeText(activity as MainActivity,"Delete ID"+pos, Toast.LENGTH_SHORT).show()
                        }

                    })
                )*/


                }

            }
        }
    }

}