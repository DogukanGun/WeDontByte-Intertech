package com.example.intertech_account.view.main_page.fragment.user_information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intertech_Customer.view_model.GetCustomerViewModel
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentUserInformationBinding
import com.example.intertech_account.model.api_model.get_customer.GetCustomerModel
import com.example.intertech_account.view.main_page.fragment.user_information.adapter.UserInformationAdapter


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


        getCustomerViewModel.getCustomerInfo.observe(viewLifecycleOwner,{
            getCustomerModel=it
            var adapter_=binding.recyclerView.adapter as? UserInformationAdapter
            var x = listOf("Name ","Surname ", "Citizienship Id ", "Birth Date", "Email ", "Telephone Number")
            var Info = listOf("deneme" ,
                "deneme",
                "deneme",
                "deneme",
                "deneme",
                "deneme"
            )
//            var Info = listOf(getCustomerModel.getCustomerData.shortName ,
//                getCustomerModel.getCustomerData.shortName,
//                getCustomerModel.getCustomerData.citizenshipNumber,
//                getCustomerModel.getCustomerData.birthDate,
//                getCustomerModel.getCustomerData.email,
//                getCustomerModel.getCustomerData.getMobilePhoneList.number
//            )
            adapter_!!.addInfo(x,Info)

        })
        return binding.root
    }
    private fun addToRecyclerView(processName: String, balance: String)
    {
        processNamesList.add(processName)
        balanceList.add(balance)
    }

}