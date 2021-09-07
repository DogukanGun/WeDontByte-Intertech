package com.example.intertech_account.view.main_page.fragment.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentOpenAccountBinding

class OpenAccountFragment : Fragment() {

    lateinit var binding:FragmentOpenAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOpenAccountBinding.inflate(layoutInflater)
        return binding.root
    }




}