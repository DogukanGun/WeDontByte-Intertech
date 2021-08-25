package com.example.intertech_account.view.main_page.fragment.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentAllAccountsBinding

class AllAccountsFragment : Fragment() {

    private lateinit var binding:FragmentAllAccountsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_all_accounts,container,false)

        //kendi adapterunden obje olustur
        //var adapter = Adapter(animalNames)

        //onu senin recyclerView'a bagla
        //binding.recyclerView.adapter=adapter


        return binding.root

    }

}