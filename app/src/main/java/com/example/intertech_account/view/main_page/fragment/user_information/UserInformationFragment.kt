package com.example.intertech_account.view.main_page.fragment.user_information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentUserInformationBinding
import com.example.intertech_account.view.main_page.fragment.user_information.adapter.UserInformationAdapter


class UserInformationFragment : Fragment() {
    private var processNamesList = mutableListOf<String>()
    private var balanceList = mutableListOf<String>()
    private lateinit var binding:FragmentUserInformationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_user_information,container,false)


        val recyclerView = binding.recyclerView
        recyclerView.layoutManager =  LinearLayoutManager(activity)
        recyclerView.adapter = UserInformationAdapter(processNamesList,balanceList)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,1
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        createDummyData()

        return binding.root
    }
    private fun addToRecyclerView(processName: String, balance: String)
    {
        processNamesList.add(processName)
        balanceList.add(balance)
    }
    private fun createDummyData(){
        processNamesList.clear()
        balanceList.clear()

        addToRecyclerView("İsim:", "Hürkan")
        addToRecyclerView("Soyad:", "Uğur")
        addToRecyclerView("TC:", "19191919191")
        addToRecyclerView("Doğum Tarihi:", "10.02.1998")
        addToRecyclerView("Yaş:", "23")
        addToRecyclerView("E-posta:", "example@hotmail.com")
        addToRecyclerView("Tel:", "+9010101010101")
        addToRecyclerView("Ev Adresi:", "Patlıcan Mah. Ayva Sok. İskender Apt. Kat 4 Daire 8 Kadıköy/İstanbul")
    }
}