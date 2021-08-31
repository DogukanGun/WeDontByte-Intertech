package com.example.intertech_account.view.main_page.fragment.account

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.iterator
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentAllAccountsBinding
import com.example.intertech_account.model.api_model.get_account.GetAccountModel
import com.example.intertech_account.resources.common_variables.Constant
import com.example.intertech_account.view.main_page.activity.MainActivity
import com.example.intertech_account.view.main_page.fragment.account.adapter.AllAccountsAdapter
import com.example.intertech_account.view.main_page.fragment.account.adapter.Swipe
import com.example.intertech_account.view.main_page.fragment.account.adapter.SwipeButton
import com.example.intertech_account.view.main_page.fragment.account.adapter.SwipeButtonClickListener
import com.example.intertech_account.view_model.GetAccountViewModel
import com.google.android.material.snackbar.Snackbar


class AllAccountsFragment : Fragment() {

    private lateinit var binding:FragmentAllAccountsBinding
    private val getAccountViewModel: GetAccountViewModel by viewModels()
    private lateinit var getAccountModel: GetAccountModel
    private var adapter=AllAccountsAdapter(arrayListOf())
    private var checkBoxList: HashMap<String,CheckBox> = hashMapOf()
    private lateinit var adapter_:AllAccountsAdapter
    private var currencyStates: HashMap<String,Int> =hashMapOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllAccountsBinding.inflate(layoutInflater)

        Constant.currentBottomMenu=1
        controlError()
        getData(savedInstanceState)
        createSwipe()

        Constant.isUserInformationTopBarButtonClick.observe(viewLifecycleOwner) {
            if (it == 1 && Constant.currentBottomMenu == 1) {
                Constant.isUserInformationTopBarButtonClick.value = 2
                val action =
                    AllAccountsFragmentDirections.actionAllAccountsFragmentToUserInformationFragment()
                Constant.navHostFragment.findNavController().navigate(action)
            }
        }





        return binding.root

    }
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


    }
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
    }

    private fun controlError(){
        getAccountViewModel.errorMessage.observe(viewLifecycleOwner,{
            if (it=="ApiError"){
                showApiErrorMessage()
            }
        })
    }
    private fun getData(savedInstanceState:Bundle?){

        binding.allAccounts.adapter=adapter
        binding.allAccounts.layoutManager=LinearLayoutManager(activity)
        getAccountViewModel.apiRequest()
        getAccountViewModel.getAccountList.observe(viewLifecycleOwner,{
            getAccountModel=it
            adapter_= (binding.allAccounts.adapter as? AllAccountsAdapter)!!
            adapter_.addAccount(getAccountModel.getAccountData.getAccountList)
            val currencyNames :List<String> = adapter_.getCurrencyList()

            checkboxCreator(currencyNames,savedInstanceState)

            for(i in currencyStates){
                checkBoxController(i.key)
            }



        })
    }
    private fun showApiErrorMessage(){
        Snackbar.make(binding.root, R.string.relode_page,Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.relode_page_button){
                relodePage()
            }
            .setActionTextColor(Color.RED)
            .setTextColor(Color.GRAY)
            .setBackgroundTint(Color.WHITE)
            .show()
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
        var mgr:AlarmManager= ApplicationProvider.getApplicationContext<Context>()
            .getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent)
        System.exit(0)
    }
    private fun createSwipe(){
        val swipe=object: Swipe(activity as MainActivity ,binding.allAccounts,200){
            override fun instantiateSwipeButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<SwipeButton>
            ) {




                buffer.add(
                    SwipeButton(activity as MainActivity,
                        "Deneme",
                        30,
                        0,
                        Color.parseColor("#2b075b"),
                        object: SwipeButtonClickListener {
                            override fun onClick(pos: Int) {




                                var action = AllAccountsFragmentDirections.actionAllAccountsFragmentToSimpleAccountFragment()
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