package com.example.intertech_account.view.main_page.fragment.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentAccountDetailBinding

class AccountDetailFragment : Fragment() {


    private lateinit var binding:FragmentAccountDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentAccountDetailBinding.inflate(layoutInflater)
        return binding.root
    }

}