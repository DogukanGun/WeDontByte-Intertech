package com.example.intertech_account.view.main_page.fragment.main_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.intertech_account.R
import com.example.intertech_account.databinding.FragmentMainPageBinding


class MainPageFragment : Fragment() {

    private lateinit var binding:FragmentMainPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=
            DataBindingUtil.inflate(inflater,R.layout.fragment_main_page,container,false)
        return binding.root
    }


}